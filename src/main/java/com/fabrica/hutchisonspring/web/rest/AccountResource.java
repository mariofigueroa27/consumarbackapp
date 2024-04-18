package com.fabrica.hutchisonspring.web.rest;

import com.fabrica.hutchisonspring.service.UserService;
import com.fabrica.hutchisonspring.service.dto.UserDTO;
import com.fabrica.hutchisonspring.web.rest.vm.ManagedUserVM;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private final UserService userService;

    public AccountResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithRoles()
                .map(UserDTO::new)
                .orElseThrow(() -> new RuntimeException("user could not be found"));
    }

    @PreAuthorize("hasAuthority(\"ROLE_ADMIN\")")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerAccount(@Valid @RequestBody ManagedUserVM vm) throws URISyntaxException {
        if (!checkPasswordLength(vm.getPassword())) {
            throw new RuntimeException("invalid password");
        }
        UserDTO userDTO = userService.register(vm, vm.getPassword());
        return ResponseEntity.created(new URI("/api/users/" + userDTO.getId().toString())).body(userDTO);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}

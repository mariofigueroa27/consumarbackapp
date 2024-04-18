package com.fabrica.hutchisonspring.service.mapper;

import com.fabrica.hutchisonspring.domain.Permission;
import com.fabrica.hutchisonspring.domain.Role;
import com.fabrica.hutchisonspring.domain.User;
import com.fabrica.hutchisonspring.service.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .filter(Objects::nonNull)
                .map(this::userDTOToUser)
                .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if(userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setEmail(userDTO.getEmail());
            Set<Permission> permissions = this.rolesFromStrings(userDTO.getRoles());
            user.setPermissions(permissions);
            return user;
        }
    }

    public Set<Permission> rolesFromStrings(Set<String> rolesAsStrings) {
        Set<Permission> roles = new HashSet<>();

        if(rolesAsStrings != null) {
            roles = rolesAsStrings.stream().map(roleName -> {
                Permission role = new Permission();
                Role auth = new Role();
                auth.setRoleName(roleName);
                role.setRole(auth);
                return role;
            }).collect(Collectors.toSet());
        }

        return roles;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}

package com.fabrica.hutchisonspring.service;

import com.fabrica.hutchisonspring.domain.Permission;
import com.fabrica.hutchisonspring.domain.Role;
import com.fabrica.hutchisonspring.domain.User;
import com.fabrica.hutchisonspring.repository.PermissionRepository;
import com.fabrica.hutchisonspring.repository.UserRepository;
import com.fabrica.hutchisonspring.security.SecurityUtils;
import com.fabrica.hutchisonspring.service.dto.UserDTO;
import com.fabrica.hutchisonspring.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PermissionRepository permissionRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
    }

    public UserDTO register(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent((existingUser) -> {
            throw new RuntimeException("login already used");
        });
        if (userDTO.getEmail() != null) {
            userRepository.findOneByEmail(userDTO.getEmail().toLowerCase()).ifPresent((existingUser) -> {
                throw new RuntimeException("email already used");
            });
        }
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setLogin(userDTO.getLogin());
        user.setPassword(encryptedPassword);
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user = userRepository.save(user);
        Permission permission = new Permission();
        permission.setUser(user);
        Role role = new Role();
        role.setId(2L);
        permission.setRole(role);
        permissionRepository.save(permission);
        return userMapper.userToUserDTO(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithRoles() {
        return SecurityUtils.getCurrentLogin()
                .flatMap(userRepository::findOneByLogin);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstname(userDTO.getFirstname());
                    user.setLastname(userDTO.getLastname());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail());
                    }
                    return user;
                })
                .map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }
}

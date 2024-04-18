package com.fabrica.hutchisonspring.repository;

import com.fabrica.hutchisonspring.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByLogin(String login);
    Optional<User> findOneByEmail(String email);

    @EntityGraph(attributePaths = "permissions")
    Optional<User> findOneWithPermissionsByEmail(String email);

    @EntityGraph(attributePaths = "permissions")
    Optional<User> findOneWithPermissionsByLogin(String login);
}

package com.fabrica.hutchisonspring.domain;

import com.fabrica.hutchisonspring.domain.composite.PermissionComposite;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(PermissionComposite.class)
@Table(name = "hu_permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("permissions")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("permissions")
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}

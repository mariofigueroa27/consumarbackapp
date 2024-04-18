package com.fabrica.hutchisonspring.domain.composite;

import java.io.Serializable;
import java.util.Objects;

public class PermissionComposite implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long user;

    private Long role;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionComposite that = (PermissionComposite) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public String toString() {
        return "PermissionComposite{" +
                "user=" + user +
                ", role=" + role +
                '}';
    }
}

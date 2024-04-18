package com.fabrica.hutchisonspring.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionDTO that = (PermissionDTO) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}

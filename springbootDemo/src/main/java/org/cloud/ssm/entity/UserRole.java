package org.cloud.ssm.entity;

import java.io.Serializable;

public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String userId;
    private String roleId;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" + "id=" + id + ", userId='" + userId + '\'' + ", roleId='" + roleId + '\'' + '}';
    }
}

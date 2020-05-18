package org.cloud.ssm.entity;

import java.io.Serializable;

public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1951888956560507904L;
    private Role role;
    private Permission permission;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}

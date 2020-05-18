package org.cloud.ssm.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String roleName;
    private String roleCard;

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRoleCard() {
        return roleCard;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleCard(String roleCard) {
        this.roleCard = roleCard;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", roleName='" + roleName + '\'' + ", roleCard='" + roleCard + '\'' + '}';
    }

}

package org.cloud.ssm.entity;

import java.io.Serializable;

public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String memberId;
    private String roleId;

    public Long getId() {
        return id;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" + "id=" + id + ", memberId='" + memberId + '\'' + ", roleId='" + roleId + '\'' + '}';
    }
}

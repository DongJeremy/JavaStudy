package org.cloud.ssm.entity;

import java.io.Serializable;

public class UserRole implements Serializable {

    private static final long serialVersionUID = 7738316240092530856L;
    private User user;
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
}

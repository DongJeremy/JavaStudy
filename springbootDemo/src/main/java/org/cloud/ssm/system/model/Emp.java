package org.cloud.ssm.system.model;

import java.io.Serializable;
import java.util.UUID;

public class Emp implements Serializable {

    private static final long serialVersionUID = -8637238762518016290L;
    private Long id;
    private String userId;
    private String username;
    private String password;
    private Long deptId;
    private String deptName;
    private String tel;
    private String email;
    private Integer status;

    public Emp() {
        super();
    }

    public Emp(Long id, String username, String password, String tel, String email, Integer status) {
        super();
        this.id = id;
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
        this.status = status;
    }

    public Emp(Long id, String userId, String username, String password, String tel, String email, Integer status) {
        super();
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Emp [id=" + id + ", userId=" + userId + ", username=" + username + ", password=" + password + ", tel="
                + tel + ", email=" + email + ", status=" + status + "]";
    }
}

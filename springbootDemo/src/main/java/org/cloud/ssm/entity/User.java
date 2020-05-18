package org.cloud.ssm.entity;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String phone;
    private String username;
    private String password;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", phone='" + phone + '\'' + ", username='" + username
                + '\'' + ", password='" + password + '\'' + '}';
    }

    @Override
    public int compareTo(User o) {
        if (this.age < o.age) {
            return -1;
        } else if (this.age > o.age) {
            return 1;
        }
        return 0;
    }
}

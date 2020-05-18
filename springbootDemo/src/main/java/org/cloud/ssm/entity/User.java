package org.cloud.ssm.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable, Comparable<User> {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String nickName;
    private Date birthday;
    private String openId;
    private Integer age;
    private String notes;
    private Integer sex;
    private String adress;
    private Integer status;
    private String phone;
    private String username;
    private String password;

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

    public Integer getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getNickName() {
        return nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getOpenId() {
        return openId;
    }

    public Integer getAge() {
        return age;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getSex() {
        return sex;
    }

    public String getAdress() {
        return adress;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", name='" + name + '\'' + ", nickName='" + nickName + '\'' + ", birthday="
                + birthday + ", openId='" + openId + '\'' + ", age=" + age + ", notes='" + notes + '\'' + ", sex=" + sex
                + ", adress='" + adress + '\'' + ", status=" + status + ", phone='" + phone + '\'' + ", username='"
                + username + '\'' + ", password='" + password + '\'' + '}';
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

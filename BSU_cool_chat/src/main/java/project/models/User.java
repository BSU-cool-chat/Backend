package project.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class User {
    private int id;
    @NotEmpty(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Login should be between 2 and 30 characters")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 2, max = 30, message = "Password should be between 2 and 30 characters")
    private String password;
    private boolean isRoot;
    private String name;
    private String sex;
    private Integer age;
    private String additionalInfo;

    public User() {

    }

    public String toString() {
        return "id : " + id + "\nlogin: " + login + "\n";
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    public User(int id, String login, String password, boolean isRoot,
                String name, String sex, Integer age, String additionalInfo) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isRoot = isRoot;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.additionalInfo = additionalInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(id, login, isRoot, name, sex, age, additionalInfo);
    }
}

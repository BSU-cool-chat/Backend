package project.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class User {
    private int id;
    @NotEmpty(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Login should be between 2 and 30 characters")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 2, max = 30, message = "Password should be between 2 and 30 characters")
    private String password;
    private String name;
    private String sex;
    private Integer age;
    private String additionalInfo;

    public User() {

    }

    public User(int id, String login, String password,
                String name, String sex, Integer age, String additionalInfo) {
        this.id = id;
        this.login = login;
        this.password = password;
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
        return new UserInfo(id, login, name, sex, age, additionalInfo);
    }
}

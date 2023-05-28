package project.models;

import java.util.Objects;

public class UserInfo {
    private int id;
    private String login;
    private String name;
    private String sex;
    private int age;
    private String additionalInfo;

    public String toString() {
        return "id : " + id + "\nlogin: " + login + "\nsex: " + sex + "\nage: " + age + "\nadditional_info: " + additionalInfo;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        UserInfo user = (UserInfo) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(name, user.name) &&
                Objects.equals(sex, user.sex) && Objects.equals(age, user.age) && Objects.equals(additionalInfo, user.additionalInfo);
    }

    public UserInfo(int id, String login, String name, String sex, int age, String additionalInfo) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.additionalInfo = additionalInfo;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAllInfo() {
        String result = "";
        if (name != null) {
            result += "Name\t" + name + "\n";
        }
        if (sex != null) {
            result += "Sex\t" + sex + "\n";
        }
        if (age != 0) {
            result += "Age\t" + age + "\n";
        }
        if (additionalInfo != null) {
            result += "AAdditionalInfo\t" + additionalInfo + "\n";
        }
        if (result.isEmpty()) {
            return "Default user";
        }
        return result;
    }

    public String getChatLink(int user_id) {
        return "/chats/" + user_id + "/new_standard_chat/" + id;
    }
}

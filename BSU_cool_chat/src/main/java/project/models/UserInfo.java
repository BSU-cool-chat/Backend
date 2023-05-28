package project.models;

import java.util.Objects;

public class UserInfo {
    private int id;
    private String login;
    private boolean isRoot;
    private String name;
    private String sex;
    private int age;
    private String additionalInfo;

    public UserInfo(int id, String login, boolean isRoot, String name, String sex, int age, String additionalInfo) {
        this.id = id;
        this.login = login;
        this.isRoot = isRoot;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.additionalInfo = additionalInfo;
    }

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

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public String getAllInfo() {
        String result = "";
        if (name != null && !name.isEmpty()) {
            result += "Name:\t" + name + "\n";
        }
        if (sex != null && !sex.isEmpty()) {
            result += "Sex:\t" + sex + "\n";
        }
        if (age != 0) {
            result += "Age:\t" + age + "\n";
        }
        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            result += "AdditionalInfo:\t" + additionalInfo + "\n";
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

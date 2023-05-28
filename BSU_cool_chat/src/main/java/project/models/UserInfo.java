package project.models;

public class UserInfo {
    private int id;
    private String login;
    private String name;
    private String sex;
    private int age;
    private String additionalInfo;

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

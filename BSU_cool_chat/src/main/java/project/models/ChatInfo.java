package project.models;

public class ChatInfo {
    private Message lastMessage;
    private String name;
    private String link;

    public ChatInfo(Message lastMessage, String name, String link) {
        this.lastMessage = lastMessage;
        this.name = name;
        this.link = link;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}

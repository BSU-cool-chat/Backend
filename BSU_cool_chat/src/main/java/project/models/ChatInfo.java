package project.models;

public class ChatInfo {
    private Message lastMessage;
    private String name;

    public ChatInfo(Message lastMessage, String name) {
        this.lastMessage = lastMessage;
        this.name = name;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public String getName() {
        return name;
    }
}

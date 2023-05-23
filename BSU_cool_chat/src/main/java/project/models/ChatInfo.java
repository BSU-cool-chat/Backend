package project.models;

public class ChatInfo {
    private int chat_id;
    private String name;
    private Message lastMessage;

    public ChatInfo(int chat_id, String name, Message lastMessage) {
        this.chat_id = chat_id;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public String getName() {
        return name;
    }

    public String getLink(int user_id) {
        return "/chats/" + user_id + "/chat/" + chat_id;
    }
}

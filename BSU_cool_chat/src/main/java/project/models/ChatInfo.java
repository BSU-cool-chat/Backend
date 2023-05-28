package project.models;

import java.util.Objects;

public class ChatInfo {
    private int chat_id;
    private String name;
    private Message lastMessage;

    public ChatInfo(int chat_id, String name, Message lastMessage) {
        this.chat_id = chat_id;
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public String toString() {
        return "chat_id: " + chat_id + "\n" + "chat_name: " + name + "\n";
    }

    public int hashCode() {
        return chat_id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        ChatInfo info = (ChatInfo) o;
        return chat_id == info.chat_id && Objects.equals(name, info.name) && Objects.equals(lastMessage, info.lastMessage);
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

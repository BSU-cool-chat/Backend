package project.models;

import java.util.List;
import java.util.Objects;

public class Chat {
    private int id;
    private String name;
    private boolean isGroupChat;
    private List<User> members;
    private List<Message> messages;

    public String toString() {
        StringBuilder answer = new StringBuilder("id: " + id + "\n" + "name: " + name + "\n" + "isGroupChat: " + isGroupChat + "\n" + "members: " + "\n");
        for (int i = 0; i < members.size(); ++i) {
            answer.append(i).append(": ").append(members.get(i).toString()).append("\n");
        }
        answer.append("\n");
        answer.append("messages: \n");

        for (int i = 0; i < messages.size(); ++i) {
            answer.append(i).append(": ").append(messages.get(i).toString()).append("\n");
        }

        return answer.toString();
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Chat info = (Chat) o;
        return id == info.id && Objects.equals(name, info.name);
    }

    public Chat(int id, String name, boolean isGroupChat) {
        this.id = id;
        this.name = name;
        this.isGroupChat = isGroupChat;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    public User getInterlocutor(int user_id) {
        if (isGroupChat) {
            throw new RuntimeException("no interlocutor");
        }
        if (members.size() != 2) {
            throw new RuntimeException("too many members");
        }

        return members.stream()
                .filter(user -> user.getId() != user_id)
                .findAny()
                .get();
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        return messages.stream()
                .max((a, b) -> {
                    if (a.getDispatchDate().compareTo(b.getDispatchDate()) == 0) {
                        return a.getDispatchTime().compareTo(b.getDispatchTime());
                    }
                    return a.getDispatchDate().compareTo(b.getDispatchDate());
                })
                .orElse(null);
    }

    public ChatInfo getChatInfo() {
        return new ChatInfo(id, name, getLastMessage());
    }
}

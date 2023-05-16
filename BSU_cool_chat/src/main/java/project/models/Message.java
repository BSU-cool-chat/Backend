package project.models;

import java.sql.Time;
import java.util.Date;

public class Message {
    private int id;
    private User sender;
    private int chatId;
    private String text;
    private Date dispatchDate;
    private Time dispatchTime;

    public Message(int id, User sender, int chatId, String text, Date dispatchDate, Time dispatchTime) {
        this.id = id;
        this.sender = sender;
        this.chatId = chatId;
        this.text = text;
        this.dispatchDate = dispatchDate;
        this.dispatchTime = dispatchTime;
    }

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public int getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDispatchDate() {
        return dispatchDate;
    }

    public Time getDispatchTime() {
        return dispatchTime;
    }
}

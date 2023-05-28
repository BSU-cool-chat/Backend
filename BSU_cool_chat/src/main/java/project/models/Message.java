package project.models;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Message {
    private int id;
    private User sender;
    private int chatId;
    private String text;
    private Date dispatchDate;
    private Time dispatchTime;

    public String toString() {
        return "id: " + id + "\n" + "user: " + sender.toString() + "\n" +
                "chat_id: " + chatId + "\n" + "text: " + text + "\n" + "date: " + dispatchDate.toString() +
                "\n" + "time: " + dispatchTime.toString();
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && chatId == message.chatId && Objects.equals(sender, message.sender) && Objects.equals(text, message.text)
                && Objects.equals(dispatchDate, message.dispatchDate) && Objects.equals(dispatchTime, message.dispatchTime);
    }

    public Message(int id, User sender, int chatId, String text, Date dispatchDate, Time dispatchTime) {
        this.id = id;
        this.sender = sender;
        this.chatId = chatId;
        this.text = text;
        this.dispatchDate = dispatchDate;
        this.dispatchTime = dispatchTime;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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

    public void setDispatchDate(Date dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public Time getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Time dispatchTime) {
        this.dispatchTime = dispatchTime;
    }
}

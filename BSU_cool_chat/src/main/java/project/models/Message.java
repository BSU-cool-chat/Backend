package project.models;

import java.util.Date;

public class Message {
    private int id;
    private int senderId;
    private int receiverId;
    private String text;
    Date dispatchTime;

    public Message() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
//        String date = dateFormat.format(new Date());
        dispatchTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }
}

package project.models;

import java.sql.Time;
import java.util.Date;

public class ChatInfo {
    private int senderId;
    private String senderLogin;
    private int receiverId;
    private String receiverLogin;
    private String text;
    private Date date;
    private Time time;
    private int interlocutor;
    private String interlocutorLogin;

    public ChatInfo() {
    }

    public ChatInfo(int senderId, String senderLogin,
                    int receiverId, String receiverLogin,
                    String text, Date date, Time time, int interlocutor) {
        this.senderId = senderId;
        this.senderLogin = senderLogin;
        this.receiverId = receiverId;
        this.receiverLogin = receiverLogin;
        this.text = text;
        this.date = date;
        this.time = time;
        this.interlocutor = interlocutor;
        if (interlocutor == senderId) {
            interlocutorLogin = senderLogin;
        } else if (interlocutor == receiverId) {
            interlocutorLogin = receiverLogin;
        } else {
            throw new RuntimeException("invalid parameters in ChatInfo constructor\n");
        }
    }

    public int getSenderId() {
        return senderId;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getInterlocutor() {
        return interlocutor;
    }

    public String getInterlocutorLogin() {
        return interlocutorLogin;
    }
}

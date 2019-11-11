package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {
    private String type;
    private String username;
    private String msg;
    private int onlineCount;
    static String SPEAK = "SPEAK";
    static String NEW_USER = "NEW_USER";
    static String CLOSE = "CLOSE";

    public Message() {
    }

    public Message(String type, String username, String msg, int onlineCount) {
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }

    public Message(String username, String msg){
        this.username = username;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }
    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

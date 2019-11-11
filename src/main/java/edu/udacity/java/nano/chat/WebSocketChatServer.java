package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        onlineSessions.values().stream().filter(session -> session.isOpen()).forEach(session -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //TODO: add send message method.
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        //TODO: add on open connection. //Done
        onlineSessions.put(session.getId(), session);
        Message message = new Message();
        int sessionCount = onlineSessions.size();
        message.setOnlineCount(sessionCount);
        this.setMessageAttributesAndSend(message, Message.NEW_USER);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO: add send message. //Done
        Message message = JSON.parseObject(jsonStr, Message.class);
        int sessionCount = onlineSessions.size();
        message.setOnlineCount(sessionCount);
        this.setMessageAttributesAndSend(message, Message.SPEAK);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //TODO: add close connection. //Done
        this.onlineSessions.remove(session.getId());
        int sessionCount = onlineSessions.size();
        Message message = new Message();
        message.setOnlineCount(sessionCount);
        this.setMessageAttributesAndSend(message, Message.CLOSE);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void setMessageAttributesAndSend(Message message, String type){
        message.setType(type);
        String msgString = JSON.toJSONString(message);
        sendMessageToAll(msgString);
    }

}

package com.yyoung.bookstore.controller;

import com.yyoung.bookstore.dto.chat.*;
import com.yyoung.bookstore.utils.MessageDecoder;
import com.yyoung.bookstore.utils.MessageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@ServerEndpoint(
        value = "/chat",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
@Component
public class ChatEndpoint {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(Session session) throws EncodeException, IOException {
        sessions.add(session);
        session.getBasicRemote().sendObject(new UserListMessage(this.getUserList()));
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        Map<String, Object> properties = session.getUserProperties();
        String username = (String) properties.get("username");

        if (message instanceof JoinMessage) {
            JoinMessage joinMessage = (JoinMessage) message;
            if (joinMessage.getUsername() == null) {
                return;
            }
            properties.put("username", joinMessage.getUsername());
            properties.put("active", "true");
            this.broadcast(joinMessage);
            this.broadcastUserList();
        } else if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            if (username == null || textMessage.getContent() == null) {
                return;
            }
            textMessage.setSender(username);
            this.broadcast(textMessage);
        } else if (message instanceof LeaveMessage) {
            if (username == null) {
                return;
            }
            LeaveMessage leaveMessage = (LeaveMessage) message;
            leaveMessage.setUsername(username);
            properties.put("active", "false");
            this.broadcast(leaveMessage);
            this.broadcastUserList();
        }
    }

    @OnClose
    public void onClose(Session session) {
        Map<String, Object> properties = session.getUserProperties();
        properties.put("active", false);
        if (properties.containsKey("username")) {
            String name = properties.get("username").toString();
            broadcast(new LeaveMessage(name));
            broadcast(new UserListMessage(this.getUserList()));
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.warn("WebSocket error: {}", throwable.getMessage());
        throwable.printStackTrace();
    }

    private void broadcastUserList() {
        this.broadcast(new UserListMessage(this.getUserList()));
    }

    private synchronized void broadcast(Message message) {
        try {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendObject(message);
                }
            }
        } catch (IOException | EncodeException exception) {
            log.warn("Error when broadcasting message: {}", exception.getMessage());
            exception.printStackTrace();
        }
    }

    private List<String> getUserList() {
        List<String> users = new ArrayList<>();
        for (Session session : sessions) {
            if (!session.isOpen()) {
                continue;
            }
            Map<String, Object> properties = session.getUserProperties();
            Object active = properties.get("active");
            String username = (String) properties.get("username");
            boolean isActive = active != null && Boolean.parseBoolean((String) active);
            if (isActive && username != null) {
                users.add(username);
            }
        }
        return users;
    }
}

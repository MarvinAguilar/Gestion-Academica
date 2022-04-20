package com.backend.endpoint;

import com.backend.controller.ProfesorController;
import com.backend.controller.SessionController;
import com.backend.endpoint.decode.JsonObjectDecoder;
import com.backend.endpoint.encode.JsonObjectEncoder;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@ServerEndpoint(value = "/sesion", decoders = {JsonObjectDecoder.class}, encoders = {JsonObjectEncoder.class})
public class SessionEndpoint {

    private static final SessionController controller = SessionController.getInstance();
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    public void broadcast(JSONObject message) throws EncodeException, IOException {
        if (message == null) return;
        for (Session session : sessions) {
            session.getBasicRemote().sendObject(controller.processRequest(message));
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(JSONObject message, Session session) throws EncodeException, IOException {
        JSONObject response = controller.processRequest(message);
        if (response != null) {
            session.getBasicRemote().sendObject(response);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.format("Session: %s Error: %s%n", session.getId(), throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        controller.logout();
    }

}

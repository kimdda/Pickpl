package com.pickpl.action;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
@ServerEndpoint("/BroadSocket")    // 클라이언트에서 서버로 접속할 주소를 지정함.
public class BroadSocket {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    
    @OnMessage				// 클라이언트로부터 메시지가 도착했을 때.
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("from client : " + message);
        
        
        synchronized(clients) {
            for(Session client : clients) {
                if(!client.equals(session)) {
                    client.getBasicRemote().sendText(message);
                }
            }
        }
        
    }
    
    @OnOpen					// 클라이언트가 서버로 접속했을 때.
    public void onOpen(Session session) {
        System.out.println(session);
        clients.add(session);
    }
    
    @OnClose				// 클라이언트의 접속이 끊겼을 때.
    public void onClose(Session session) {
        clients.remove(session);
    }
}

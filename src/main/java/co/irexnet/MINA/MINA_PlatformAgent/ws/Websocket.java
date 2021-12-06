package co.irexnet.MINA.MINA_PlatformAgent.ws;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/websocket")
@Slf4j
public class Websocket {
	
	/**
     * �쎒�냼耳� �꽭�뀡�쓣 �떞�뒗 ArrayList
     */
    private static ArrayList<Session> sessionList = new ArrayList<Session>();

    
    /**
     * �쎒�냼耳� �궗�슜�옄 �뿰寃� �꽦由쏀븯�뒗 寃쎌슦 �샇異�
     */
    @OnOpen
    public void handleOpen(Session session) {
        if (session != null) {
            String sessionId = session.getId();
            
            log.info("client is connected. sessionId == [" + sessionId + "]");
            sessionList.add(session);
          
        }
    }
    

    /**
     * �쎒�냼耳� 硫붿떆吏�(From Client) �닔�떊�븯�뒗 寃쎌슦 �샇異�
     */
    @OnMessage
    public String handleMessage(String message, Session session) {
        if (session != null) {
            String sessionId = session.getId();
            System.out.println("message is arrived. sessionId == [" + sessionId + "] / message == " + message + "]");

  
            //sendMessageToAll("[USER- "+ sessionId + "] " + message);
        }

        return null;
    }
    

    /**
     * �쎒�냼耳� �궗�슜�옄 �뿰寃� �빐�젣�븯�뒗 寃쎌슦 �샇異�
     */
    @OnClose
    public void handleClose(Session session) {
        if (session != null) {
            String sessionId = session.getId();
            System.out.println("client is disconnected. sessionId == [" + sessionId + "]");
            
            // �쎒�냼耳� �뿰寃� �꽦由쎈릺�뼱 �엳�뒗 紐⑤뱺 �궗�슜�옄�뿉寃� 硫붿떆吏� �쟾�넚
            sendMessageToAll("***** [USER-" + sessionId + "] is disconnected. *****");
        }
    }

    
    /**
     * �쎒�냼耳� �뿉�윭 諛쒖깮�븯�뒗 寃쎌슦 �샇異�
     */
    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }
    
    
    /**
     * �쎒�냼耳� �뿰寃� �꽦由쎈릺�뼱 �엳�뒗 紐⑤뱺 �궗�슜�옄�뿉寃� 硫붿떆吏� �쟾�넚
     */
    public static boolean sendMessageToAll(String message) {
        if (sessionList == null) {
            return false;
        }

        int sessionCount = sessionList.size();
        if (sessionCount < 1) {
            return false;
        }

        Session singleSession = null;

        for (int i = 0; i < sessionCount; i++) {
            singleSession = sessionList.get(i);
            if (singleSession == null) {
                continue;
            }

            if (!singleSession.isOpen()) {
                continue;
            }

            sessionList.get(i).getAsyncRemote().sendText(message);
        }

        return true;
    }
}

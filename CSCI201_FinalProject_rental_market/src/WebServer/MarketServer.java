package WebServer;

import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/mks")
public class MarketServer {
	private static Vector<Session> sessionVector = new Vector<Session>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connecting!");
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		if(message.equals("newPost")) {
			notifyNewPost(message, session);
		}
	}
	public void notifyNewPost(String message, Session session) {
		try {
			for(Session s : sessionVector) {
				s.getBasicRemote().sendText(message);
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	@OnClose
	public void close(Session session) {
		System.out.println("Disconnecting!");
		sessionVector.remove(session);
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
}

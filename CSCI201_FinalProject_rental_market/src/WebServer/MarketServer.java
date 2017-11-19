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
	private static Vector<String> userEmails = new Vector<String>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connecting!");
		sessionVector.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		String actionType = message.substring(0, 7);
		if(actionType.equals("ActionA")) {
			userEmails.add(message.substring(7));
			System.out.println(message.substring(7));
		}
		else if(actionType.equals("ActionB")) {
			System.out.println(userEmails.remove(message.substring(7)));
		}
		else if(actionType.equals("ActionC")) {
			int index = userEmails.indexOf(message.substring(7));
			if(index != -1) {
				Session s = sessionVector.get(index);
				try {
					s.getBasicRemote().sendText("New Request");
				} catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
					close(session);
				}
			}
		}
		else if(actionType.equals("ActionD")) {
			notifyNewPost("New Post", session);
		}
		else if(actionType.equals("ActionE")) {
			int index = userEmails.indexOf(message.substring(7));
			if(index != -1) {
				Session s = sessionVector.get(index);
				try {
					s.getBasicRemote().sendText("New Post Comment");
				} catch (IOException ioe) {
					System.out.println("ioe: " + ioe.getMessage());
					close(session);
				}
			}
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

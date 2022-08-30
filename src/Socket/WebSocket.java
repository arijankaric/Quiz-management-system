package Socket;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;


@ServerEndpoint(
		value = "/quizGameplay",
		decoders = MessageDecoder.class, 
		encoders = MessageEncoder.class )
public class WebSocket {

//	private static final List<Session> clientSession = new ArrayList<>();
//	private static final List<Integer> activeQuizzes = new ArrayList<>();
//	private static final Set<Session> sessions =  new CopyOnWriteArraySet<Session>();
	private static final Map<String, LiveQuiz> quizzes = new HashMap<String, LiveQuiz>();
	private static final int maxCode = 9999;
	
	
	
	private void sendMessage(Message message, Session session)
	{
		try {
			session.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String generateCode()
	{
		String retStr = new String();
		int randNum;
		int i = 0;
		while (i < 4)
		{
			randNum = ThreadLocalRandom.current().nextInt(1, maxCode + 1);
			retStr = Integer.toString(randNum);
			++i;
		}
		return retStr;
	}
	
	private String newQuizCode(int quizId)
	{
		String codeStr;
		while (true)
		{
			codeStr = generateCode();
			if(!quizzes.containsKey(codeStr))
			{
				quizzes.put(codeStr, new LiveQuiz(quizId));
				break;
			}
		}
		return codeStr;
	}
	
	private void sendMessageToAll(CopyOnWriteArraySet<Session> sessions) throws IOException {
		sessions.forEach(s -> {
			try
			{
				s.getBasicRemote().sendText(Integer.toString(sessions.size()));
			}
			catch (IOException e)
			{
				System.out.println("Error in communication!");
			}
		});
		
//		for (int i = 0; i < sessions.size(); ++i) {
//			try {
//				sessions.get(i).getBasicRemote().sendText(Integer.toString(sessions.size()));
//			} catch (IOException e) { sessions.
//				System.out.println("Error in communication!");
//			}
//		}
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
//		Set<Session> sessions = quizzes.get(session.getBasicRemote().)
//		sessions.add(session);
//		sendMessageToAll();
//		session.getUserProperties().get("quizCode");
		
		System.out.println("New client connected!");
	}

	@OnClose
	public void onClose(Session session) throws IOException {
//		sessions.remove(session);
//		sendMessageToAll();
		
		String qC = (String) session.getUserProperties().get("quizCode");
		LiveQuiz lQ = quizzes.get(qC);
		
		lQ.getSessions().removeIf(s -> (s.equals(session))); // safe guard if connection closes without message
		
//		for (Map.Entry mapElement : quizzes.entrySet()) {
//			
//			Map<String, LiveQuiz> sessions = (Map<String, LiveQuiz>) mapElement.getValue();
//			sessions.removeIf(s -> (s.equals(session)));
//			
////            int key = (int)mapElement.getKey();
////            String value = (String)mapElement.getValue();
////  
////            System.out.println(key + " : " + value);
//        }
		System.out.println("Client disconnected!");
	}

	@OnMessage
	public Message handleTextMessage(Message message, Session session) {
		System.out.println("New Text Message Received");
		System.out.println(message);
		int flag = message.getFlag();
		
		if(flag == 1) // admin tells server to create new quiz code
		{
			String qC = newQuizCode(message.getQuizId());
			quizzes.get(qC).getSessions().add(session);
			message.setQuizCode(qC);
			sendMessage(message, session);
		}
		else if(flag == 0) // client asks server to connect to quiz
		{
			String qCMessage = message.getQuizCode();
			boolean found = false; // found (active) Quiz Code in quizzes
			
			for (Map.Entry mapElement : quizzes.entrySet()) 
			{	
				String qCQuizzes = (String) mapElement.getKey();
				if(qCQuizzes.equals(qCMessage))
				{
					found = true;
					break;
				}
	        }
			
			if(found)
			{
				session.getUserProperties().put("quizCode", message.getQuizCode());
				quizzes.get(message.getQuizCode()).getSessions().add(session);
			}
			else
			{
				message.setFlag(-1);
				sendMessage(message, session);
			}
		}
		else if(flag == 2) // also have to handle this case in onClose; case being moderator hangs up on quiz without opening all the questions
		{
			
		}
		
//		sessions.forEach(s -> {
//			try {
//				System.out.println("sending message to:" + s.getId());
//				s.getBasicRemote().sendObject(message);
//			} catch (IOException | EncodeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		return message;
	}
}

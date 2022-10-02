package Socket;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


@ServerEndpoint(
		value = "/quizGameplay",
		decoders = MessageDecoder.class, 
		encoders = MessageEncoder.class,
		configurator = CustomConfigurator.class)
public class WebSocket {
	
	private HttpSession httpSession;
	private Boolean loggedIn;
	private Boolean moderatingQuiz = false;
	private Boolean spectatingQuiz = false;
	private String quizCode;
	private String clientNickname;
	private int clientId;
	Session session;
	List<Session> sessions;
	LiveQuiz liveQuiz;
	
	public void setHttpSession(HttpSession httpSession) {
        if (this.httpSession != null) {
            throw new IllegalStateException("HttpSession has already been set!");
        }

        this.httpSession = httpSession;
    }
	
	private static final Map<String, LiveQuiz> quizzes = new HashMap<String, LiveQuiz>();
	private static final int maxCode = 9;
	private static final List<Session> adminSession = new ArrayList<>();
	
	private void sendMessage(Message message)
	{
		if(this.session != null) {
			System.out.println("Sending msg: " + message);
			try {
				this.session.getBasicRemote().sendObject(message);
			} catch (IOException | EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isQuizCodeValid(String quizCode) {
		if(quizCode == null) return false;
		
		return quizzes.containsKey(quizCode);
	}
	
	private String generateCode()
	{
		String retStr = new String();
		int randNum;
		int i = 0;
		while (i < 4)
		{
			randNum = ThreadLocalRandom.current().nextInt(0, maxCode + 1);
			retStr += Integer.toString(randNum);
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
	
	private void broadcast(Message message) {
		if(this.sessions != null) {
			this.sessions.forEach(s -> {
				if(s == this.session) return;
				try
				{
					s.getBasicRemote().sendObject(message);
				}
				catch (IOException | EncodeException e)
				{
					System.out.println("Error in communication!");
				}
			});
		}
	}
	
	private void sendMessageToAll(Message message) throws IOException {
		System.out.println("sendingMessageToAll:" + message);
		if(this.sessions != null) {
			this.sessions.forEach(s -> {
				try
				{
					s.getBasicRemote().sendObject(message);
				}
				catch (IOException | EncodeException e)
				{
					System.out.println("Error in communication!");
				}
			});
		}		
	}
	
	private boolean checkLoggedIn()
	{
		return (loggedIn != null) && (loggedIn.equals(true));
	}
	
	private boolean checkModeratingQuiz()
	{
		return checkLoggedIn() && (moderatingQuiz != null) && (moderatingQuiz.equals(true));
	}
	private boolean checkSpectatingQuiz()
	{
		return (spectatingQuiz != null) && (spectatingQuiz.equals(true));
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		if(this.sessions != null)
			this.sessions.remove(this.session);
		System.out.println("throwable: " + t.toString());
        t.printStackTrace();
		if(checkModeratingQuiz())
		{
			// do something, try reconnecting, anything
		}
		else if(checkSpectatingQuiz())
		{
			adminSession.remove(session);
		}
		System.out.println("WS onError");
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws IOException {
//		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		System.out.println("New client connected!");
		this.session = session;
//		this.loggedIn = (Boolean) httpSession.getAttribute("loggedIn");
//		this.moderatingQuiz = (Boolean) httpSession.getAttribute("moderatingQuiz");
//		this.spectatingQuiz = (Boolean) httpSession.getAttribute("spectatingQuiz");
//		Enumeration<String> parNames = httpSession.getAttributeNames();
//		while(parNames.hasMoreElements()) {
//			System.out.println(parNames.nextElement());
//		}
		
		if(checkModeratingQuiz())
		{
			System.out.println("onOpen moderator");
						
		}
//		else if(checkLoggedIn())
//		{
//			adminSession.add(session);
//			final int[] activeSessions = {0};
//			quizzes.forEach((k, v) -> {
//				activeSessions[0] += v.getSessions().size() - 1;
//			});
//			Message msg = new Message();
//			msg.setQuizId(activeSessions[0]);
//			msg.setQuizCode("activeClients");
//			sendMessage(msg, session);
////			msg.setQuizCode(null);
//		}
		else // maybe check if playingQuiz or not, process of elimination for now I guess
		{
			System.out.println("onOpen client");
			
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		if(this.sessions != null)
			this.sessions.remove(this.session);
		
		if(checkModeratingQuiz())
		{
			// do something, try reconnecting, anything
		}
		else if(checkSpectatingQuiz())
		{
			adminSession.remove(session);
		} else {
			Message msg = new Message();
			msg.setTypeOfMessage(3);
			msg.setText(String.valueOf(clientId));
			msg.setSenderId(-2);
			msg.setSenderNickname(null);
			broadcast(msg);
		}
		
		if(this.liveQuiz != null)
		{
			this.liveQuiz.getSessions().removeIf(s -> (s.equals(session))); // safe guard if connection closes without message
			if(this.liveQuiz.getSessions().size() == 0) {
				quizzes.remove(this.quizCode);
			}
		}
		
		
		System.out.println("Client disconnected!");
	}
	
	private boolean handleCheckQuizCode(Message message) {
		
		boolean contains = quizzes.containsKey(message.getText());
		
		message.setSenderId(-2);
		this.clientNickname = message.getSenderNickname();
		message.setSenderNickname(null);		
		message.setText(String.valueOf(contains));
		message.setTypeOfMessage(-1);		
		sendMessage(message);
		
		return contains;
	}
	
	private void sendInvalidQuizCode(Message message) {
		message.setSenderId(-2);
		message.setSenderNickname(null);
		message.setText(String.valueOf(false));
		message.setTypeOfMessage(-1);		
		sendMessage(message);
	}
	
	private void sendValidQuizCode(Message message) {
		message.setSenderId(-2);
		message.setSenderNickname(null);
		message.setText(String.valueOf(true));
		message.setTypeOfMessage(-1);		
		sendMessage(message);
	}
	
	private void handleClientConnect(Message message) throws IOException {
//		assert message.getSenderId() == null : "Incorrect senderId. Must be null if client is just now connecting";
		
		System.out.println("handleClientConnect start");
		
		String quizCode = message.getText();
		this.quizCode = quizCode;
		if(quizCode == null) {
			System.out.println("Received no quizCode from client. quizCode == null");
			return;
		}
		
		boolean validQuizCode = handleCheckQuizCode(message);
		if(!validQuizCode) {
			System.out.println("Received invalid quizCode from client");
			sendInvalidQuizCode(message);
			return;
		}

		System.out.println("Client quizCode: " + quizCode);
		this.liveQuiz = quizzes.get(quizCode);
		this.sessions = liveQuiz.getSessions();
		int quizId = quizzes.get(quizCode).getQuizId();
		
		this.clientId = sessions.size() - 1;
		sessions.add(session);
		
		Message msg = new Message();
		msg.setText(String.valueOf(quizId));
		msg.setSenderId(-2);
		msg.setSenderNickname(null);
		msg.setTypeOfMessage(9);
		sendMessage(msg);
		
		message.setSenderId(this.clientId);
		message.setSenderNickname(this.clientNickname);
		message.setText("0");
		message.setTypeOfMessage(0);
		sendMessageToAll(message);
		
		message.setSenderId(-2);
		message.setSenderNickname(null);
		message.setTypeOfMessage(11);
		message.setText(String.valueOf(clientId));
		sendMessage(message);
		
		System.out.println("handleClientConnect end");
	}
	
	private void handleAdminStartQuiz(Message message) {
		System.out.println("handleAdminStartQuiz start");
		assert message.getSenderId() == -1 : "Incorrect senderId. Must be -1 if admin is the one starting quiz";
		assert message.getSenderNickname() == null : "Incorrect senderNickname. Must be null if admin is the one starting quiz";
		
		broadcast(message);
		System.out.println("handleAdminStartQuiz end");
	}
	
	private void handleAdminDisconnect(Message message) {
		System.out.println("handleAdminDisconnect start");
		assert message.getSenderId() == -1 : "Incorrect senderId. Must be -1 if admin is the one disconnecting";
		assert message.getSenderNickname() == null : "Incorrect senderNickname. Must be null if admin is the one disconnecting";
		
		broadcast(message);
		System.out.println("handleAdminDisconnect end");
	}
	
	private void handleClientDisconnect(Message message) {
		assert message.getSenderId() >= 0 : "Incorrect senderId. Must be >= 0 if client is the one disconnecting";
		assert message.getSenderId() == this.clientId : "Incorrect senderId. Must be == this.clientId";
		assert message.getSenderNickname() == this.clientNickname : "Incorrect senderNickname. Must be == this.clientNickname";

		
		broadcast(message);
	}
	
	private void handleAdminDeployQuiz(Message message) {
		System.out.println("handleAdminDeployQuiz start");
		assert message.getSenderId() == -1 : "Incorrect senderId. Must be -1 if admin is the one deploying quiz";
		assert message.getSenderNickname() == null : "Incorrect senderNickname. Must be null if admin is the one deploying quiz";
		
		int quizId = Integer.parseInt(message.getText());
		String quizCode = newQuizCode(quizId);
		this.quizCode = quizCode;
		System.out.println("this.quizCode: " + this.quizCode);
		System.out.println("quizCode: " + quizCode);
		LiveQuiz liveQuiz = new LiveQuiz(quizId);
		quizzes.put(quizCode, liveQuiz);
		this.liveQuiz = liveQuiz;
		this.sessions = quizzes.get(quizCode).getSessions();
		this.sessions.add(session);
		System.out.println("Moderator sessions: " + sessions);
		Message msg = new Message();
		msg.setSenderId(-2);
		msg.setSenderNickname(null);
		msg.setTypeOfMessage(8);
		msg.setText(quizCode);
		sendMessage(msg);
		this.moderatingQuiz = true;
		System.out.println("handleAdminDeployQuiz end");
	}
	
	private void handleUpdateClientScore(Message message) throws IOException {
		System.out.println("handleUpdateClientScore start");
		assert message.getSenderId() >= 0 : "Incorrect senderId. Must be >= 0 if client is the one updating his score. senderId: " + message.getSenderId();
		assert message.getSenderId() == this.clientId : "Incorrect senderId. Must be == this.clientId";
		assert message.getSenderNickname() == this.clientNickname : "Incorrect senderNickname. Must be == this.clientNickname";
		assert Integer.valueOf(message.getText()) >= 0 : "Incorrect score. Must be >= 0";

		sendMessageToAll(message);
		System.out.println("handleUpdateClientScore end");

	}
	
	private void handleNextQuestion(Message message) {
		System.out.println("handleNextQuestion start");
		
		broadcast(message);
		System.out.println("handleNextQuestion end");
	}
	
	private void handleEveryoneFinished(Message message) {
		System.out.println("handleEveryoneFinished start");
		
		broadcast(message);
		System.out.println("handleEveryoneFinished end");
	}
	
//	private void handleGetQuizId(Message message) {
//		this.liveQuiz = quizzes.get(this.quizCode);
//		
//		if(this.liveQuiz != null) {
//			this.quizCode = message.getText();
//			message.setTypeOfMessage(9);
//			message.setSenderId(-2);
//			message.setSenderNickname(null);
//			message.setText(String.valueOf(this.liveQuiz.getQuizId()));
//		} else {
//			message.setTypeOfMessage(-1);
//			message.setSenderId(-2);
//			message.setSenderNickname(null);
//			message.setText("Invalid quiz code!!");
//		}		
//	}

	@OnMessage
	public void handleTextMessage(Message message, Session session) throws IOException {
		System.out.println("New Text Message Received");
		System.out.println(message);
		int typeOfMessage = message.getTypeOfMessage();
		
		// -2 => checkIfQuizCodeExists (Clients asks Server if quiz code exists)
		// -1 => respondIfValidQuizCode(Client entered invalid/valid quiz code)
		// 0 => clientConnect, 
		// 1 => adminStartQuiz, 
		// 2 => adminDisconnect,
		// 3 => clientDisconnect(broadcasting clientId/clientNickname)
		// 4 => adminDeployQuiz
		// 5 => updateClientScore
		// 6 => nextQuestion
		// 7 => everyoneFinished(everyoneSubmittedTheirAnswer)
		// 8 => quizCode(sent from server to moderator)
		// 9 => quizId(sent from server to client)
		// 10 => getQuizId(sent from client to server; client is asking for quiz id of sent quiz code) NOT NECESSARY
		
		switch(typeOfMessage) {
		case -2:
			handleCheckQuizCode(message);
			break;
		case 0:
			handleClientConnect(message);
			break;
		case 1:
			handleAdminStartQuiz(message);
			break;
		case 2:
			handleAdminDisconnect(message);
			break;
		case 3:
			handleClientDisconnect(message);
			break;
		case 4:
			handleAdminDeployQuiz(message);
			break;
		case 5:
			handleUpdateClientScore(message);
			break;
		case 6:
			handleNextQuestion(message);
			break;
		case 7:
			handleEveryoneFinished(message);
			break;
//		case 10:
//			handleGetQuizId(message);
//			break;
		default:
			System.out.println("Undefined typeOfMessage:" + typeOfMessage);
		}
		
//		return message;
	}
}

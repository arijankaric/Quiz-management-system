package Socket;

public class Message {
	private int typeOfMessage;
	private String text;
	private int senderId; 
	// -2 => server, 
	// -1 => moderator, 
	// -3 => clientWithoutAssignedId (client that is requesting/waiting for an id to be assigned to it by the server)
	private String senderNickname; // null for server & moderator
	
	public int getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(int typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderNickname() {
		return senderNickname;
	}

	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	// -1 => inalidQuizCode(Client entered invalid quiz code)
	// 0 => clientConnect, 
	// 1 => adminStartQuiz, 
	// 2 => adminDisconnect,
	// 3 => clientDisconnect(broadcasting clientId)
	// 4 => adminDeployQuiz
	// 5 => updateClientScore
	// 6 => nextQuestion
	// 7 => everyoneFinished(everyoneSubmittedTheirAnswer)
	// 8 => quizCode(sent from server to moderator)
	// 9 => quizId(sent from server to client)
	// 4 => clientDisconnect(broadcasting clientNickname)


	@Override
	public String toString() {
		return "Message [typeOfMessage=" + this.typeOfMessage + ", text=" + this.text + ", senderId=" + this.senderId + ", senderNickname=" + this.senderNickname + "]";

	}
}

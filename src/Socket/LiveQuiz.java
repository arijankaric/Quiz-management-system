package Socket;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

public class LiveQuiz {
	public CopyOnWriteArraySet<Session> getSessions() {
		return sessions;
	}
	public void setSessions(CopyOnWriteArraySet<Session> sessions) {
		this.sessions = sessions;
	}
//	public String getQuizCode() {
//		return quizCode;
//	}
	public LiveQuiz() {
		super();
		sessions = new CopyOnWriteArraySet<Session>();
		// TODO Auto-generated constructor stub
	}
//	public LiveQuiz(String quizCode) {
//		super();
//		sessions = new CopyOnWriteArraySet<Session>();
//		this.quizCode = quizCode;
//		// TODO Auto-generated constructor stub
//	}
	public LiveQuiz(int quizId) {
		super();
		sessions = new CopyOnWriteArraySet<Session>();
		this.quizId = quizId;
		// TODO Auto-generated constructor stub
	}
	private CopyOnWriteArraySet<Session> sessions;
//	private String quizCode;
	private int quizId;
	public int getQuizId() {
		return quizId;
	}
//	public void setQuizCode(String quizCode) {
//		this.quizCode = quizCode;
//	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

}

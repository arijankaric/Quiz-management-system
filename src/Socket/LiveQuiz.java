package Socket;

import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

public class LiveQuiz {
	private CopyOnWriteArraySet<Session> sessions;
	private int quizId;
	
	public CopyOnWriteArraySet<Session> getSessions() {
		return sessions;
	}
	
	public void setSessions(CopyOnWriteArraySet<Session> sessions) {
		this.sessions = sessions;
	}
	
	public LiveQuiz() {
		super();
		sessions = new CopyOnWriteArraySet<Session>();
	}
	
	public LiveQuiz(int quizId) {
		super();
		sessions = new CopyOnWriteArraySet<Session>();
		this.quizId = quizId;
	}

	public int getQuizId() {
		return quizId;
	}
	
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

}

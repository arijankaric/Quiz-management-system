package Socket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class LiveQuiz {
	private List<Session> sessions;
	private int quizId;
	
	public List<Session> getSessions() {
		return sessions;
	}
	
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	
	public LiveQuiz() {
		super();
		sessions = new ArrayList<Session>();
	}
	
	public LiveQuiz(int quizId) {
		super();
		sessions = new ArrayList<Session>();
		this.quizId = quizId;
	}

	public int getQuizId() {
		return quizId;
	}
	
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

}

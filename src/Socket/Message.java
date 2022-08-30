package Socket;

public class Message {
//	private String userId;
//	private String username;
//	private String text;
	private String quizCode;
	private int quizId;
	
	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	private Integer flag; 
	// -1 => Client entered invalid quizCode
	// 0 => newClient, 
	// 1 => Admin Start Quiz, 
	// 2 => Admin Disconnect in the middle of quiz (who's going to open questions?),

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getQuizCode() {
		return quizCode;
	}

	public void setQuizCode(String quizCode) {
		this.quizCode = quizCode;
	}

//	public String getUserId() {
//		return userId;
//	}
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getText() {
//		return text;
//	}
//	public void setText(String text) {
//		this.text = text;
//	}
	@Override
	public String toString() {
//		return "Message [userId=" + userId + ", username=" + username + ", text=" + text + "]";
		return "Message [quizCode=" + quizCode + "]";

	}
}

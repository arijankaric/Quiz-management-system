package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "question", schema = "rwaquiz")
public class Question {
    private int id;
    private int ordinalNumber;
    private String title;
    private int timeToAnswer;
    private int score;
    private List<Answer> answers = new ArrayList<Answer>();
    private transient Quiz quiz;
    
    public Question() {
    	super();
    }
    
    public Question(String title, int score, int timeToAnswer, Quiz quiz) {
    	this.title = title;
    	this.score = score;
    	this.timeToAnswer = timeToAnswer;
    	this.quiz = quiz;
    	this.ordinalNumber = quiz.getQuestions().size() + 1;
    }
    
    public Question(String title, int score, int timeToAnswer, Quiz quiz, int ordinalNumber) {
    	this.title = title;
    	this.score = score;
    	this.timeToAnswer = timeToAnswer;
    	this.quiz = quiz;
    	this.ordinalNumber = ordinalNumber;
    }
    
    @Column(name = "ordinal_number", table = "question", nullable = false)
    public int getOrdinalNumber() {
		return ordinalNumber;
	}

	public void setOrdinalNumber(int ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}

	@Id
    @Column(name = "id", table = "question", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title", table = "question", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "time_to_answer", table = "question", nullable = false)
    public int getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(int timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    @Column(name = "score", table = "question", nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != question.id) return false;
        if (timeToAnswer != question.timeToAnswer) return false;
        if (score != question.score) return false;
        return Objects.equals(title, question.title);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + timeToAnswer;
        result = 31 * result + score;
        return result;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Answer> getAnswers() {
        answers.sort(Comparator.comparingInt(Answer::getId));
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false, table = "question")
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    
    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
          .filter(str -> str.length() != 0)
          .map(str -> str.substring(0, str.length() - 1))
          .orElse(s);
        }

    @Override
    public String toString() {
        answers.sort(Comparator.comparingInt(Answer::getId));
        String answersStr = new String("[");
        for (int i = 0; i < answers.size(); i++) {
            answersStr += answers.get(i) + ","; 
        }
        answersStr = removeLastCharOptional(answersStr);
        answersStr += "]";
        return "{\"id\":" + id + "," +
                "\"title\":\"" + title + "\"," +
                "\"timeToAnswer\":" + timeToAnswer + "," +
                "\"score\":" + score + "," +
                "\"answers\":" + answersStr + "," +
                "\"ordinalNumber\":" + this.ordinalNumber +"}";
    }
}
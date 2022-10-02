package Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "answer", schema = "rwaquiz")
public class Answer {
    private int id;
    private String title;
    private boolean isCorrect;
    private transient Question question;
    
    public Answer() {
    	super();
    }
    public Answer(String title, boolean correct, Question question) {
    	this.title = title;
    	this.isCorrect = correct;
    	this.question = question;
    }

    @Id
    @Column(name = "id", table = "answer", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title", table = "answer", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "is_correct", table = "answer", nullable = false)
    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (isCorrect != answer.isCorrect) return false;
        return Objects.equals(title, answer.title);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, table = "answer")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + "," +
                "\"title\":\"" + title + "\"," +
                "\"correct\":" + isCorrect + '}';
    }
}
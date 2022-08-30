package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question")
public class Question {
    private int id;
    private String title;
    private int timeToAnswer;
    private int score;
    private List<Answer> answers = new ArrayList<>();
    private Quiz quiz;

    @Id
    @Column(name = "id", table = "question", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", table = "question", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "time_to_answer", table = "question", nullable = false)
    public int getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(int timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    @Basic
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        answers.sort(Comparator.comparingInt(Answer::getId));
        return "{\"id\":" + id + "," +
                "\"title\":\"" + title + "\"," +
                "\"timeToAnswer\":" + timeToAnswer + "," +
                "\"score\":" + score + "," +
                "\"answers\":" + answers + "}";
    }
}
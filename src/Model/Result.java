package Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "result", schema = "rwaquiz")
public class Result {
    private int id;
    private String userName;
    private String userSurname;
    private int score;
    private String userEmail;
    private Quiz quiz;

    @Id
    @Column(name = "id", table = "result", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", table = "result", nullable = false, length = -1)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_surname", table = "result", nullable = false, length = -1)
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Basic
    @Column(name = "score", table = "result", nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "user_email", table = "result", nullable = false, length = -1)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (id != result.id) return false;
        if (score != result.score) return false;
        if (!Objects.equals(userName, result.userName)) return false;
        if (!Objects.equals(userSurname, result.userSurname)) return false;
        return Objects.equals(userEmail, result.userEmail);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false, table = "result")
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + "," +
                "\"userName\":\"" + userName + "\"," +
                "\"userSurname\":\"" + userSurname + "\"," +
                "\"score\":" + score + "," +
                "\"userEmail\":\"" + userEmail + "\"}";
    }
}
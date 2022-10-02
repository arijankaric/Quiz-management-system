package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "quiz", schema = "rwaquiz")
public class Quiz {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private transient User owner;
    private List<Question> questions = new ArrayList<Question>();
    
    public Quiz(String title, String description, String imageUrl, User user) {
    	this.title = title;
    	this.description = description;
    	this.imageUrl = imageUrl;
    	this.owner = user;
    }
    
    public Quiz() {
    	super();
    }

    @Id
    @Column(name = "id", table = "quiz", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", table = "quiz", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", table = "quiz", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "image_url", table = "quiz", nullable = false, length = -1)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (id != quiz.id) return false;
        if (!Objects.equals(title, quiz.title)) return false;
        if (!Objects.equals(description, quiz.description)) return false;
        return Objects.equals(imageUrl, quiz.imageUrl);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Question> getQuestions() {
        questions.sort(Comparator.comparingInt(Question::getOrdinalNumber));
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false, table = "quiz")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
          .filter(str -> str.length() != 0)
          .map(str -> str.substring(0, str.length() - 1))
          .orElse(s);
        }

    @Override
    public String toString() {
        questions.sort(Comparator.comparingInt(Question::getOrdinalNumber));
        String questionsStr = new String("[");
        for (int i = 0; i < questions.size(); i++) {
            questionsStr += questions.get(i) + ","; 
        }
        if(questions.size() != 0)
        	questionsStr = removeLastCharOptional(questionsStr);
        questionsStr += "]";
        return "{\"id\":" + id + "," +
                "\"title\":\"" + title + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"imageUrl\":\"" + imageUrl + "\"," +
                "\"questions\":" + questionsStr + "}";
    }
}

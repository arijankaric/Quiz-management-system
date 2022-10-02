package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "users", schema = "rwaquiz")
public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private List<Quiz> quizzes = new ArrayList<Quiz>();
    
    public User() {
    	super();
    }
    
    public User(String username, String password, int role) {
    	super();
    	this.username = username;
    	this.password = password;
    	this.role = role;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role")
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Quiz> getQuizzes() {
    	if(quizzes != null) {
    		quizzes.sort(Comparator.comparingInt(Quiz::getId));
    	}
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
    
    public String getRoleString() {
        String retStr;
        switch(this.role){
            case 1:
                retStr = "Super-admin";
                break;
            case 2:
                retStr = "Admin";
                break;
            case 3:
                retStr = "Pending";
                break;
            default:
                retStr = "Undefined";
                System.out.println("Undefined role?!");
                break;
        }
        return retStr;
    }
}

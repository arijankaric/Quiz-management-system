package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-26T14:54:37.941+0200")
@StaticMetamodel(Quiz.class)
public class Quiz_ {
	public static volatile SingularAttribute<Quiz, Integer> id;
	public static volatile SingularAttribute<Quiz, String> title;
	public static volatile SingularAttribute<Quiz, String> description;
	public static volatile SingularAttribute<Quiz, Boolean> active;
	public static volatile SingularAttribute<Quiz, String> imageUrl;
	public static volatile ListAttribute<Quiz, Question> questions;
	public static volatile SingularAttribute<Quiz, User> owner;
	public static volatile ListAttribute<Quiz, Result> results;
}

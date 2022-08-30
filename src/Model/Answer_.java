package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-26T14:54:37.931+0200")
@StaticMetamodel(Answer.class)
public class Answer_ {
	public static volatile SingularAttribute<Answer, Integer> id;
	public static volatile SingularAttribute<Answer, String> title;
	public static volatile SingularAttribute<Answer, Boolean> correct;
	public static volatile SingularAttribute<Answer, Question> question;
}

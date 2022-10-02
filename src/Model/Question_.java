package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-09-30T06:04:25.539+0200")
@StaticMetamodel(Question.class)
public class Question_ {
	public static volatile SingularAttribute<Question, Integer> ordinalNumber;
	public static volatile SingularAttribute<Question, Integer> id;
	public static volatile SingularAttribute<Question, String> title;
	public static volatile SingularAttribute<Question, Integer> timeToAnswer;
	public static volatile SingularAttribute<Question, Integer> score;
	public static volatile ListAttribute<Question, Answer> answers;
	public static volatile SingularAttribute<Question, Quiz> quiz;
}

package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-08-26T14:54:37.947+0200")
@StaticMetamodel(Result.class)
public class Result_ {
	public static volatile SingularAttribute<Result, Integer> id;
	public static volatile SingularAttribute<Result, String> userName;
	public static volatile SingularAttribute<Result, String> userSurname;
	public static volatile SingularAttribute<Result, Integer> score;
	public static volatile SingularAttribute<Result, String> userEmail;
	public static volatile SingularAttribute<Result, Quiz> quiz;
}

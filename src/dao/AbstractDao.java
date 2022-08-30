package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractDao {
    private static final String PERSISTENCE_UNIT = "projekat";
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEMF() {
        if (emf == null)
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

        return emf;
    }

}

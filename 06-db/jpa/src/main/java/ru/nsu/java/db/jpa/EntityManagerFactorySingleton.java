package ru.nsu.java.db.jpa;

import javax.persistence.*;

public class EntityManagerFactorySingleton {
    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("ru.nsu.java.db.jpa");
        }
        return factory;
    }

    private static EntityManagerFactory factory = null;
}

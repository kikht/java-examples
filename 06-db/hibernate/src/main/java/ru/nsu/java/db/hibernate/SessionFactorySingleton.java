package ru.nsu.java.db.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            factory = new Configuration().configure().buildSessionFactory();
        }
        return factory;
    }

    private static SessionFactory factory = null;
}

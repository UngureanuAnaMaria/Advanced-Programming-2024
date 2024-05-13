package com.nictas.eclipselink.postgresql.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManagerFactorySingleton entityManagerFactorySingleton = null;
    private EntityManagerFactorySingleton() { }

    public static EntityManagerFactorySingleton getInstance() {
        if(entityManagerFactorySingleton == null) {
            entityManagerFactorySingleton = new EntityManagerFactorySingleton();
            entityManagerFactory = Persistence.createEntityManagerFactory("test");
        }
        return entityManagerFactorySingleton;
    }

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public static void closeEntity() {
        entityManagerFactory.close();
    }
}

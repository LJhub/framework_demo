package com.study.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory factory ;
    private static EntityManager entityManager ;
    static {
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    public static EntityManager getEntityManager(){
        entityManager = factory.createEntityManager();
        return entityManager;
    }

    public static EntityTransaction getEntityTransaction(){
        return entityManager.getTransaction();
    }

    public static void closeFactory() {
        factory.close();
    }
}

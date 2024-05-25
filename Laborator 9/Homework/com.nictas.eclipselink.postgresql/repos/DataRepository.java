package com.nictas.eclipselink.postgresql.repos;

import com.nictas.eclipselink.postgresql.LoggerConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DataRepository<T, ID extends Serializable> {
    private EntityManager em;
    private static final Logger logger = LoggerConfiguration.getLogger();

    public DataRepository() {
        this.em = Persistence.createEntityManagerFactory("test").createEntityManager();
    }

    public T findById(ID id, Class<T> clazz) {
        long startTime = System.currentTimeMillis();
        T entity = em.find(clazz, id);
        long endTime = System.currentTimeMillis();
        logExecutionTime("findById", endTime - startTime);
        return entity;
    }

    public boolean persist(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try {
            long startTime = System.currentTimeMillis();
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            long endTime = System.currentTimeMillis();
            logExecutionTime("persist", endTime - startTime);
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handleException(e);
            return false;
        }
    }

    public boolean merge(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try {
            long startTime = System.currentTimeMillis();
            transaction.begin();
            em.merge(entity);
            transaction.commit();
            long endTime = System.currentTimeMillis();
            logExecutionTime("merge", endTime - startTime);
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handleException(e);
            return false;
        }
    }

    public boolean remove(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try {
            long startTime = System.currentTimeMillis();
            transaction.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            transaction.commit();
            long endTime = System.currentTimeMillis();
            logExecutionTime("remove", endTime - startTime);
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            handleException(e);
            return false;
        }
    }

    protected void handleException(Exception e) {
        logger.log(Level.SEVERE, "Exception occurred", e);
    }

    private void logExecutionTime(String methodName, long executionTime) {
        logger.log(Level.INFO, "Method {0} executed in {1} ms", new Object[]{methodName, executionTime});
    }

    public EntityManager getEntityManager() {
        return em;
    }
}

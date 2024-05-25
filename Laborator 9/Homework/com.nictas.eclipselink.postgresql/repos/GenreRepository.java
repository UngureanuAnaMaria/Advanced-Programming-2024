package com.nictas.eclipselink.postgresql.repos;
import com.nictas.eclipselink.postgresql.domain.Author;
import com.nictas.eclipselink.postgresql.domain.Genre;
import com.nictas.eclipselink.postgresql.utils.EntityManagerFactorySingleton;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class GenreRepository {
    private EntityManager entityManager;

    private static final GenreRepository instance = new GenreRepository();

    private GenreRepository() {
    }

    public static GenreRepository getInstance() {
        return instance;
    }

    public void create(Genre genre) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(genre);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Genre findById(Integer id) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        Genre genre = entityManager.find(Genre.class, id);
        entityManager.close();
        return genre;
    }

    public Genre findByNameGenre(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Genre> query = entityManager.createNamedQuery("Genre.findByName", Genre.class);
        query.setParameter("name", name);
        Genre genre = query.getSingleResult();
        entityManager.close();
        return genre;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Genre> findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Genre> query = entityManager.createNamedQuery("Genre.findByName", Genre.class);
        query.setParameter("name",  name);
        List<Genre> genres = query.getResultList();
        entityManager.close();
        return genres;
    }
}

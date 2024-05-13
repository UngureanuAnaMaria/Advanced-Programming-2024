package com.nictas.eclipselink.postgresql;

import com.nictas.eclipselink.postgresql.domain.Author;
import com.nictas.eclipselink.postgresql.repos.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        /*entityManager.persist(new Author("Mark Twain"));
        transaction.commit();*/

        /*Author newAuthor = new Author("Mihail Sadoveanu");
        entityManager.persist(newAuthor);
        transaction.commit();*/

        /*Author a = (Author)entityManager.createQuery(
                        "select e from Author e where e.name='Mark Twain'")
                .getSingleResult();
        a.setName("Samuel Langhorne Clemens");
        transaction.commit();*/

        Author author = entityManager.find(Author.class, 16);
        System.out.println(author.getName());

        //AuthorRepository.getInstance().create(author);

        Author foundAuthor = AuthorRepository.getInstance().findById(author.getId());
        System.out.println("Found Author: " + foundAuthor);

        List<Author> authorsByNamePattern = AuthorRepository.getInstance().findByName("Mihail Sadoveanu");
        System.out.println("Authors with name pattern 'Mihail Sadoveanu': " + authorsByNamePattern);

        //System.out.println(newAuthor.getName());
    }

}

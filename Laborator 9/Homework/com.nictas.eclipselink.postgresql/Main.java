package com.nictas.eclipselink.postgresql;

import com.nictas.eclipselink.postgresql.domain.*;
import com.nictas.eclipselink.postgresql.repos.*;

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

        /*Author author = entityManager.find(Author.class, 16);
        System.out.println(author.getName());

        //AuthorRepository.getInstance().create(author);

        Author foundAuthor = AuthorRepository.getInstance().findById(author.getId());
        System.out.println("Found Author: " + foundAuthor);

        List<Author> authorsByNamePattern = AuthorRepository.getInstance().findByName("Mihail Sadoveanu");
        System.out.println("Authors with name pattern 'Mihail Sadoveanu': " + authorsByNamePattern);*/

        //System.out.println(newAuthor.getName());

        /*entityManager.persist(new Genre("Fantasy"));
        transaction.commit();*/

        /*entityManager.persist(new Author("J. K. Rowling"));
        transaction.commit();*/

        /*entityManager.persist(new Book("Harry Potter", "J. K. Rowling", "Fantasy", 1997, 380, "Bloomsbury"));
        transaction.commit();*/

        /*entityManager.persist(new Book("Harry Potter 2", "J. K. Rowling", "Fantasy", 1998, 380, "Bloomsbury"));
        transaction.commit();*/

        Author author = AuthorRepository.getInstance().findByNameAuthor("J. K. Rowling");
        Genre genre = GenreRepository.getInstance().findByNameGenre("Fantasy");
        /*entityManager.persist(new Book("Harry Potter 3", author, genre, 1999, 380, "Bloomsbury"));
        transaction.commit();*/

        /*entityManager.persist(new Book("Harry Potter 4", author, genre, 2000, 380, "Bloomsbury"));
        transaction.commit();*/

        //without dataRep
        /*List<Book> booksByAuthor = BookRepository.getInstance().findByAuthor(AuthorRepository.getInstance().findByNameAuthor("J. K. Rowling"));
        System.out.println("Books with author pattern 'J. K. Rowling': " + booksByAuthor);*/

        BookRepository bookRepository = new BookRepository();
        Book newBook = new Book("Harry Potter 5", author, genre, 2000, 380, "Bloomsbury");
        //bookRepository.saveBook(newBook);

        //Book book = bookRepository.findBookById(newBook.getId());
        Book book = bookRepository.findBookById(31);
        System.out.println("Found book: " + book.getName() + " by " + book.getAuthor());

        /*entityManager.persist(new Author("J. Rowling"));
        transaction.commit();*/

        /*book.setAuthor(AuthorRepository.getInstance().findByNameAuthor("J. Rowling"));
        bookRepository.updateBook(book);*/

        //bookRepository.deleteBook(book);
    }

}

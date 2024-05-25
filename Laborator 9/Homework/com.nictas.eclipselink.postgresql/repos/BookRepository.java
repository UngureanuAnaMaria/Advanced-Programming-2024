package com.nictas.eclipselink.postgresql.repos;
import com.nictas.eclipselink.postgresql.domain.Author;
import com.nictas.eclipselink.postgresql.domain.Book;
import com.nictas.eclipselink.postgresql.domain.Genre;
import com.nictas.eclipselink.postgresql.utils.EntityManagerFactorySingleton;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
/*public class BookRepository {
    private EntityManager entityManager;

    private static final BookRepository instance = new BookRepository();

    private BookRepository() {
    }

    public static BookRepository getInstance() {
        return instance;
    }

    public void create(Book book) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Book findById(Integer id) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.close();
        return book;
    }

    public Book findByNameBook(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByName", Book.class);
        query.setParameter("name", name);
        Book book = query.getSingleResult();
        entityManager.close();
        return book;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Book> findByName(String name) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByName", Book.class);
        query.setParameter("name",  name);
        List<Book> books = query.getResultList();
        entityManager.close();
        return books;
    }

    public List<Book> findByAuthor(Author author) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByAuthor", Book.class);
        query.setParameter("author", author);
        List<Book> books = query.getResultList();
        entityManager.close();
        return books;
    }

    public List<Book> findByGenre(Genre genre) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByGenre", Book.class);
        query.setParameter("genre", genre);
        List<Book> books = query.getResultList();
        entityManager.close();
        return books;
    }

    public List<Book> findByAuthor(int year) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().getEntityManager();
        TypedQuery<Book> query = entityManager.createNamedQuery("Book.findByYear", Book.class);
        query.setParameter("year", year);
        List<Book> books = query.getResultList();
        entityManager.close();
        return books;
    }
}*/

//using AbstractRepository
public class BookRepository extends DataRepository<Book, Integer> {
    public BookRepository() {
        super();
    }

    public Book findBookById(Integer id) {
        return findById(id, Book.class);
    }

    public boolean saveBook(Book book) {
        return persist(book);
    }

    public boolean updateBook(Book book) {
        return merge(book);
    }

    public boolean deleteBook(Book book) {
        return remove(book);
    }
}

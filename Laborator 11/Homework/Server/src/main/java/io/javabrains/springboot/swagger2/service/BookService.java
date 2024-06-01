package io.javabrains.springboot.swagger2.service;

import io.javabrains.springboot.swagger2.api.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    private static List<Book> bookList;

    public BookService() {
        bookList = new ArrayList<>();
        Book book1 = new Book(1, "B1", "A1, A2", "G1, G2", 2000, 200);
        Book book2 = new Book(2, "B2", "A3, A2", "G1, G2", 2003, 500);
        Book book3 = new Book(3, "B3", "A7, A4", "G3, G2", 2005, 476);
        Book book4 = new Book(4, "B4", "A4, A6", "G3, G4", 2011, 320);
        bookList.addAll(Arrays.asList(book1, book2, book3, book4));
    }

    public Book getBook(Integer id) {
        for(Book book : bookList) {
            if(id.equals(book.getId())) {
                return book;
            }
        }
        return null;
    }

    public static List<Book> getBooks() {
        return bookList;
    }

    public void save(Book book) {
        if(getBook(book.getId()) == null) {
            bookList.add(book);
        }
    }

    public Book update(Integer id, String newName) {
        Book book = getBook(id);
        book.setName(newName);
        return book;
    }

    public void deleteById(Integer id) {
        bookList.remove(getBook(id));
    }
}

package org.example;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ReadingList {
    private String name;
    private LocalDateTime timestamp;
    private Set<Book> books;

    public ReadingList(String name) {
        this.name = name;
        this.timestamp = LocalDateTime.now();
        this.books = new HashSet<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void getReadingList() {
        for(Book book : books) {
            System.out.println(book);
        }
    }

    public Set<Book> getBooks() {
        return books;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}


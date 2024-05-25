package org.example;
import org.apache.log4j.BasicConfigurator;
import org.example.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Main {
    private static final String CSV_FILE_PATH = "C:\\Users\\anaun\\Downloads\\goodreads_library_export (1).csv";

    public static void main(String args[]) {
        try {
            BasicConfigurator.configure();//log4j:WARN No appenders could be found for logger (com.zaxxer.hikari.HikariConfig).
            var authors = new AuthorDAO();
            //authors.create("William Shakespeare");
            //authors.create("Cassandra Clare");
            //authors.create("Collen Hoover");
            //authors.create("Douglas Adams");
            //System.out.println(authors.findByName("Collen Hoover"));
            //System.out.println(authors.findById(1));
            var genres = new GenreDAO();
            //genres.create("Tragedy");
            //genres.create("Comedy");
            //genres.create("Science fiction");
            //genres.create("Adventure");
            //System.out.println(genres.findByName("Comedy"));
            //Database.getConnection().commit();
            var books = new BookDAO(); //findByName
            //books.create(1597,"Romeo and Juliet","William Shakespeare","Tragedy");
            Book book1 = new Book(1597, "Romeo and Juliet", "William Shakespeare", "Tragedy");
            //books.create(1979,"The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science fiction");
            Book book2 = new Book(1979, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science fiction");

            //Database.getConnection().commit();
            //books.getAllBooks();

            ReadingList readingList = new ReadingList("Tragedy Books");
            readingList.addBook(book1);
            readingList.addBook(book2);
            readingList.removeBook(book2);
            System.out.println(book1.getGenre());

            List<Book> booksList = new ArrayList<>();
            booksList.add(book1);
            booksList.add(book2);

            processBooksFromCSV(books, authors, genres, readingList, booksList);

            readingList.getReadingList();

            for (Book book : booksList) {
                System.out.println(book);
            }

            List<List<Book>> readingLists = partitionIntoReadingLists(booksList);

            for (int i = 0; i < readingLists.size(); i++) {
                System.out.println("Reading List " + (i + 1) + ":");
                for (Book book : readingLists.get(i)) {
                    System.out.println(book.getTitle());
                }
                System.out.println();
            }

            Database.getConnection().close();
        } catch (IOException | SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
    }

    private static void processBooksFromCSV(BookDAO books, AuthorDAO authors, GenreDAO genres, ReadingList readingList, List<Book> booksList) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String title = data[1];
                String author = data[2];
                String yearPublished = data[3];

                if (authors.findByName(author) == null) {
                    authors.create(author);
                    Database.getConnection().commit();
                    System.out.println("New author created: " + author);
                }

                //genres.create("Unknown");

                if (books.findByName(title) != null) {
                    //books.create(Integer.parseInt(yearPublished), title, author, "Tragedy");
                    Book book = new Book(Integer.parseInt(yearPublished), title, author, "Tragedy");
                    Database.getConnection().commit();
                    System.out.println("New book created: " + title);
                    booksList.add(book);

                    readingList.addBook(book);
                    System.out.println("New book created: " + title);

                }
            }
            System.out.println("Data imported successfully!");
            Database.getConnection().commit();
        }
    }

    public static List<List<Book>> partitionIntoReadingLists(List<Book> books) {
        Collections.sort(books, Comparator.comparing(Book::getAuthor).thenComparing(Book::getGenre));

        List<List<Book>> readingLists = new ArrayList<>();
        for (Book book : books) {
            boolean added = false;
            for (List<Book> readingList : readingLists) {
                boolean related = false;
                for (Book existingBook : readingList) {
                    if (existingBook.getAuthor().equals(book.getAuthor()) || existingBook.getGenre().equals(book.getGenre())) {
                        related = true;
                        break;
                    }
                }

                if (!related) {
                    readingList.add(book);
                    added = true;
                    break;
                }
            }

            //not added to an existing list => create a new list
            if (!added) {
                List<Book> newReadingList = new ArrayList<>();
                newReadingList.add(book);
                readingLists.add(newReadingList);
            }
        }
        return readingLists;
    }
}

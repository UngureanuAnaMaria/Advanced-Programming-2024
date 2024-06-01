package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Collections;
import com.example.demo.model.Book;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
        app.run(args);

        // Create a new book
        Book newBook = new Book(7, "The Cruel Prince", "Holly Black", "Fantasy", 2010, 400);
        ResponseEntity<Book> createdBookResponse = new RestTemplate()
                .postForEntity("http://localhost:8080/books/add-book", newBook, Book.class);
        Book createdBook = createdBookResponse.getBody();
        System.out.println("Created Book: " + createdBook);

        Book newBook1 = new Book(8, "The Wicked King", "Holly Black", "Fantasy", 2012, 400);
        ResponseEntity<Book> createdBookResponse1 = new RestTemplate().postForEntity("http://localhost:8080/books/add-book", newBook1, Book.class);
        Book newBook2 = new Book(9, "The Queen of Nothing", "Holly Black", "Fantasy", 2013, 400);
        ResponseEntity<Book> createdBookResponse2 = new RestTemplate().postForEntity("http://localhost:8080/books/add-book", newBook2, Book.class);

        // Update the name of the book
        String newName = "B2 Updated";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("" + newName + "", headers);
        ResponseEntity<Book> updatedBookResponse = new RestTemplate()
                .exchange("http://localhost:8080/books/{id}", HttpMethod.PUT, requestEntity, Book.class,
                        2);
        Book updatedBook = updatedBookResponse.getBody();
        System.out.println("Updated Book: " + updatedBook);

        // Delete the book
        new RestTemplate()
                .delete("http://localhost:8080/books/delete/{id}", 1);

        // Get all books
        ResponseEntity<Book[]> booksResponse = new RestTemplate()
                .getForEntity("http://localhost:8080/books/all", Book[].class);
        Book[] books = booksResponse.getBody();
        System.out.println("Books: " + Arrays.toString(books));
    }
}
package io.javabrains.springboot.swagger2.api.controller;

import io.javabrains.springboot.swagger2.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.javabrains.springboot.swagger2.api.model.Book;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @ApiOperation("Get all books")
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookService.getBooks(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/add-book", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Add a new book")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.save(book);
        return new ResponseEntity<>(book, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete book by ID")
    public ResponseEntity<Integer> deleteBook(@PathVariable Integer id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update book's name")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody String newName) {
        return new ResponseEntity<>(bookService.update(id, newName), new HttpHeaders(), HttpStatus.OK);
    }
}

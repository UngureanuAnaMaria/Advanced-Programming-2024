package com.example.demo.api.controller;

import com.example.demo.api.model.Author;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/author")
    public Author getAuthor(@RequestParam Integer id) {
        Optional author = authorService.getAuthor(id);
        if(author.isPresent()) {
            return (Author)author.get();
        }
        return null;
    }

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return (List<Author>) authorService.getAuthors();
    }
}

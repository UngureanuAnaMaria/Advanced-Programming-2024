package com.example.demo.service;

import com.example.demo.api.model.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private List<Author> authorList;

    public AuthorService() {
        authorList = new ArrayList<>();
        Author author1 = new Author(1, "Author 1");
        Author author2 = new Author(2, "Author 2");
        Author author3 = new Author(3, "Author 3");
        Author author4 = new Author(4, "Author 4");
        authorList.addAll(Arrays.asList(author1, author2, author3, author4));
    }

    public Optional<Author> getAuthor(Integer id) {
        Optional optional = Optional.empty();
        for(Author author : authorList) {
            if(id.equals(author.getId())) {
                optional = Optional.of(author);
                return optional;
            }
        }
        return optional;
    }

    public List<Author> getAuthors() {
        return authorList;
    }
}

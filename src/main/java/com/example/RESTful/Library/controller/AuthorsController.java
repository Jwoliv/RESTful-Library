package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.service.user.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorsController {
    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> pageOfAllAuthors() {
        return authorService.findAll();
    }
}

package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.controller.AbstractController;
import com.example.RESTful.Library.dao.impl.user.AuthorDaoImpl;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.service.user.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorsController extends AbstractController<Author, AuthorDaoImpl, AuthorService> {
    protected AuthorsController(AuthorService service) {
        super(service);
    }
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> booksOfAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getBooks());
    }
    @GetMapping("/search-by-name")
    public ResponseEntity<List<Author>> searchUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
}

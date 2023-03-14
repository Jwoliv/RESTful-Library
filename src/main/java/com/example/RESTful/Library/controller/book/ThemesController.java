package com.example.RESTful.Library.controller.book;

import com.example.RESTful.Library.controller.AbstractController;
import com.example.RESTful.Library.dao.dao.book.ThemeDaoImpl;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.book.Theme;
import com.example.RESTful.Library.service.book.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemesController extends AbstractController<Theme, ThemeDaoImpl, ThemeService> {
    protected ThemesController(ThemeService service) {
        super(service);
    }
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> booksOfTheme(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getBooks());
    }
}

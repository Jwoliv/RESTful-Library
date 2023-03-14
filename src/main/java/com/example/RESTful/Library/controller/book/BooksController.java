package com.example.RESTful.Library.controller.book;

import com.example.RESTful.Library.controller.AbstractController;
import com.example.RESTful.Library.dao.dao.book.BookDaoImpl;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.service.book.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BooksController extends AbstractController<Book, BookDaoImpl, BookService> {
    protected BooksController(BookService service) {
        super(service);
    }
}

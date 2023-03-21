package com.example.RESTful.Library.controller.book;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BooksControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksController booksController;

    @Test
    public void testDeleteElement() {
        Long id = 123L;

        when(bookService.findById(id)).thenReturn(new Book());

        ResponseEntity<List<Book>> responseEntity = booksController.deleteElement(id);

        verify(bookService, times(1)).delete(any(Book.class));
        verify(bookService, times(1)).findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    public void testBooksTaken() {
        String isTaken = "true";

        when(bookService.findByIsTaken(Boolean.valueOf(isTaken))).thenReturn(List.of(new Book()));

        ResponseEntity<List<Book>> responseEntity = booksController.booksTaken(isTaken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }
}
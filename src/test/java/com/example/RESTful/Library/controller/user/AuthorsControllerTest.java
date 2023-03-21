package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.service.user.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorsControllerTest {
    @Mock
    private AuthorService authorService;
    @InjectMocks
    private AuthorsController authorsController;
    @Test
    void booksOfAuthor_whenIsNotEmpty() {
        var booksOfTheAuthor = List.of(new Book(), new Book(), new Book());
        var author = new Author();
        author.setBooks(booksOfTheAuthor);

        when(authorService.findById(1L)).thenReturn(author);

        var response = authorsController.booksOfAuthor(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), booksOfTheAuthor);
        assertEquals(Objects.requireNonNull(response.getBody()).size(), booksOfTheAuthor.size());
    }

    @Test
    void booksOfAuthor_whenIsEmpty() {
        var author = new Author();
        author.setBooks( new ArrayList<>());

        when(authorService.findById(1L)).thenReturn(author);

        var response = authorsController.booksOfAuthor(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
        assertEquals(Objects.requireNonNull(response.getBody()).size(), 0);
    }

    @Test
    void testSearchUsersByName_whenIsNotEmpty() {
        var name = "John";
        var user = new Author();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        when(authorService.findByName(name)).thenReturn(List.of(user));

        var response = authorsController.searchUsersByName(name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), List.of(user));
        assertEquals(Objects.requireNonNull(response.getBody()).size(), List.of(user).size());
    }

    @Test
    void testSearchUsersByName_whenIsEmpty() {
        var name = "John";
        when(authorService.findByName(name)).thenReturn(new ArrayList<>());

        var response = authorsController.searchUsersByName(name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
        assertEquals(Objects.requireNonNull(response.getBody()).size(),0);
    }
}
package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.service.user.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorsControllerTest {
    @Mock
    private AuthorService authorService;

    @Test
    void booksOfTheAuthor_whenIsNotEmptyList() {
        var books = List.of(new Book(), new Book(), new Book(), new Book());
        var author = new Author();
        author.setBooks(books);

        when(authorService.findById(1L)).thenReturn(author);

        var resultAuthor = authorService.findById(1L);
        var resultBooks = resultAuthor.getBooks();

        assertEquals(books.size(), resultBooks.size());
        assertEquals(author, resultAuthor);
        assertEquals(books, resultBooks);
    }
    @Test
    void booksOfTheAuthor_whenIsEmptyList() {
        List<Book> books = List.of();
        var author = new Author();
        author.setBooks(books);

        when(authorService.findById(1L)).thenReturn(author);

        var resultAuthor = authorService.findById(1L);
        var resultBooks = resultAuthor.getBooks();

        assertEquals(0, resultBooks.size());
        assertEquals(author, resultAuthor);
        assertEquals(books, resultBooks);
    }
    @Test
    void searchUsersByName_whenIsNotZeroResult() {
        var nameRequest = "User";
        var author1 = new Author();
        author1.setId(1L);
        author1.setFirstname("User");
        author1.setLastname("Example");

        var author2 = new Author();
        author1.setId(2L);
        author1.setFirstname("Example1");
        author1.setLastname("User1");

        var authors = List.of(author1, author2);

        when(authorService.findByName(nameRequest)).thenReturn(authors);

        var resultAuthors = authorService.findByName(nameRequest);
        assertEquals(authors.size(), resultAuthors.size());
        assertEquals(authors, resultAuthors);
    }
    @Test
    void searchUsersByName_whenIsZeroResult() {
        var nameRequest = "User";
        List<Author> authors = new ArrayList<>();
        when(authorService.findByName(nameRequest)).thenReturn(authors);

        var resultAuthors = authorService.findByName(nameRequest);
        assertEquals(0, resultAuthors.size());
        assertEquals(authors, resultAuthors);
    }
}
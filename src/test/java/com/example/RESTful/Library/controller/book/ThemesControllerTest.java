package com.example.RESTful.Library.controller.book;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.book.Theme;
import com.example.RESTful.Library.service.book.ThemeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ThemesControllerTest {
    @Mock
    private ThemeService themeService;
    @InjectMocks
    private ThemesController themesController;

    @Test
    void testBooksOfTheme_whenIsNotEmpty() {
        var theme = new Theme();
        var books = List.of(new Book(), new Book(), new Book());
        theme.setBooks(books);
        when(themeService.findById(1L)).thenReturn(theme);

        ResponseEntity<List<Book>> response = themesController.booksOfTheme(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), theme.getBooks());
        assertEquals(Objects.requireNonNull(response.getBody()).size(), theme.getBooks().size());
    }

    @Test
    void testBooksOfTheme_whenIsEmpty() {
        var theme = new Theme();
        theme.setBooks(new ArrayList<>());
        when(themeService.findById(1L)).thenReturn(theme);

        ResponseEntity<List<Book>> response = themesController.booksOfTheme(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), theme.getBooks());
        assertEquals(Objects.requireNonNull(response.getBody()).size(), theme.getBooks().size());
    }
}
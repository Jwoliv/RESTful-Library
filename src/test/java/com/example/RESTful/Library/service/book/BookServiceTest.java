package com.example.RESTful.Library.service.book;

import com.example.RESTful.Library.dao.impl.book.BookDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookDaoImpl bookDao;

    @InjectMocks
    private BookService bookService;


    @Test
    public void testDelete_whenContractIsNull() {
        var book = new Book();
        book.setContract(null);
        book.setPreviousOwners(new ArrayList<>());
        doNothing().when(bookDao).delete(book);
        bookService.delete(book);
        verify(bookDao, times(1)).delete(book);
    }

    @Test
    void testDelete_whenContractIsNotNull() {
        var book = new Book();
        book.setContract(new Contract());
        book.setPreviousOwners(new ArrayList<>());
        doNothing().when(bookDao).delete(book);
        bookService.delete(book);
        verify(bookDao, times(0)).delete(book);
    }

    @Test
    void testFindById_whenIsExist() {
        var id = 12L;
        var book = new Book();
        book.setId(id);
        when(bookDao.findById(id)).thenReturn(book);

        var result = bookService.findById(id);


        verify(bookDao, times(1)).findById(id);
        assertEquals(result.getId(), book.getId());
        assertEquals(result, book);
    }

    @Test
    void testFindById_whenIsNotExist() {
        var id = 12L;
        when(bookDao.findById(id)).thenReturn(null);

        var result = bookService.findById(id);

        verify(bookDao, times(1)).findById(id);
        assertNull(result);
    }

    @Test
    public void testUpdateBookAfterReturnContract() {
        Book book = new Book();
        User currentOwner = new User();
        List<User> previousOwners = new ArrayList<>();
        previousOwners.add(new User());
        previousOwners.add(new User());
        book.setCurrentOwner(currentOwner);
        book.setPreviousOwners(previousOwners);

        doAnswer(invocation -> {
            Book updatedBook = invocation.getArgument(0);
            assertNull(updatedBook.getCurrentOwner());
            assertFalse(updatedBook.getIsTaken());
            assertEquals(3, updatedBook.getPreviousOwners().size());
            return null;
        }).when(bookDao).update(book);
        bookService.updateBookAfterReturnContract(book);
        verify(bookDao, times(1)).update(book);
    }

    @Test
    void testUpdateBookFieldsOnBorrow_whenContractIsNull() {
        bookService.updateBookFieldsOnBorrow(null, new Book());
        verify(bookDao, times(0)).update(new Book());
    }

    @Test
    void testUpdateBookFieldsOnBorrow_whenBookIsNull() {
        bookService.updateBookFieldsOnBorrow(new Contract(), null);
        verify(bookDao, times(0)).update(null);
    }
    @Test
    void testUpdateBookFieldsOnBorrow_whenBookAndContractIsNotNull() {
        Book book = new Book();
        book.setId(1L);
        bookService.updateBookFieldsOnBorrow(new Contract(), book);
        verify(bookDao, times(1)).update(book);
    }
}
package com.example.RESTful.Library.service.book;

import com.example.RESTful.Library.dao.dao.book.BookDaoImpl;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends AbstractService<Book, BookDaoImpl> {
    protected BookService(BookDaoImpl dao) {
        super(dao);
    }
    @Override
    public void delete(Book book) {
        if (book != null && book.getContract() == null && book.getPreviousOwners().isEmpty()) {
            getDao().delete(book);
        }
    }
    public List<Book> findByIsTaken(Boolean isTaken) {
        return getDao().findByIsTaken(isTaken);
    }
    public void updateBookAfterReturnContract(Book book) {
        if (book != null) {
            book.getPreviousOwners().add(book.getCurrentOwner());
            book.setCurrentOwner(null);
            book.setIsTaken(false);
            update(book);
        }
    }
}

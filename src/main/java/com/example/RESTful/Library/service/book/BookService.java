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

    public List<Book> findByAuthorId(Long authorId) {
        return getDao().findByAuthorId(authorId);
    }
    public List<Book> findByThemeId(Long themeId) {
        return getDao().findByThemeId(themeId);
    }
    public List<Book> findByIsTaken(Boolean isTaken) {
        return getDao().findByIsTaken(isTaken);
    }
}

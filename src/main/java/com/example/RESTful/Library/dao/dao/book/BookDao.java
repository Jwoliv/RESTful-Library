package com.example.RESTful.Library.dao.dao.book;

import com.example.RESTful.Library.dao.dao.AbstractDao;
import com.example.RESTful.Library.model.book.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book> {
    List<Book> findByIsTaken(Boolean isTaken);
    List<Book> findByIsAvailiable(Boolean isAvailiable);
}

package com.example.RESTful.Library.dao.impl.book;

import com.example.RESTful.Library.dao.impl.AbstractDao;
import com.example.RESTful.Library.model.book.Book;

import java.util.List;

public interface BookDao extends AbstractDao<Book> {
    List<Book> findByIsTaken(Boolean isTaken);
}

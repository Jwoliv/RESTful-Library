package com.example.RESTful.Library.dao.impl.user;

import com.example.RESTful.Library.dao.impl.AbstractDao;
import com.example.RESTful.Library.model.user.Author;

import java.util.List;

public interface AuthorDao extends AbstractDao<Author> {
    List<Author> findByName(String name);
}

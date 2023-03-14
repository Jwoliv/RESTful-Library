package com.example.RESTful.Library.service.user;

import com.example.RESTful.Library.dao.dao.user.AuthorDaoImpl;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService extends AbstractService<Author, AuthorDaoImpl> {
    protected AuthorService(AuthorDaoImpl dao) {
        super(dao);
    }
    public List<Author> findByName(String name) {
        return getDao().findByName(name);
    }
}

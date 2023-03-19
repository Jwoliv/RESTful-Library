package com.example.RESTful.Library.dao.dao.user;

import com.example.RESTful.Library.dao.dao.AbstractDao;
import com.example.RESTful.Library.model.user.User;

import java.util.List;

public interface UserDao extends AbstractDao<User> {
    List<User> findByName(String name);
    User findByEmail(String email);
    User findByPhone(String numberPhone);
}

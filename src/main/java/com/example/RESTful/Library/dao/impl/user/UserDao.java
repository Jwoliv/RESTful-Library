package com.example.RESTful.Library.dao.impl.user;

import com.example.RESTful.Library.dao.impl.AbstractDao;
import com.example.RESTful.Library.model.user.User;

import java.util.List;

public interface UserDao extends AbstractDao<User> {
    List<User> findByName(String name);
    User findByEmail(String email);
    User findByPhone(String numberPhone);
}

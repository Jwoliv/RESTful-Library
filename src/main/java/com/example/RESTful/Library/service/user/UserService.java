package com.example.RESTful.Library.service.user;

import com.example.RESTful.Library.dao.dao.user.UserDaoImpl;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends AbstractService<User, UserDaoImpl> {
    protected UserService(UserDaoImpl dao) {
        super(dao);
    }
    public List<User> findByName(String name) {
        return getDao().findByName(name);
    }
    public List<User> findByEmail(String email) {
        return getDao().findByName(email);
    }
}

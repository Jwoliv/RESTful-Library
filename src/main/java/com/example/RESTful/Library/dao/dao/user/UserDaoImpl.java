package com.example.RESTful.Library.dao.dao.user;

import com.example.RESTful.Library.dao.dao.AbstractDaoImpl;
import com.example.RESTful.Library.dao.impl.user.UserDao;
import com.example.RESTful.Library.model.user.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    public List<User> findByName(String name) {
        return getSession().createQuery(
                "SELECT U FROM User AS U WHERE U.firstname LIKE %:name% OR U.lastname LIKE %:name%",
                User.class
        ).setParameter("name", name).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        return getSession().createQuery(
                "SELECT U FROM User AS U WHERE U.email = :email",
                User.class
        ).setParameter("email", email).getSingleResult();
    }
}

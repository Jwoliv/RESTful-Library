package com.example.RESTful.Library.dao.impl.user;

import com.example.RESTful.Library.dao.impl.AbstractDaoImpl;
import com.example.RESTful.Library.dao.dao.user.UserDao;
import com.example.RESTful.Library.model.user.User;
import jakarta.persistence.NoResultException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    public List<User> findByName(String name) {
        try {
            return getSession().createQuery(
                    "SELECT U FROM User AS U WHERE UPPER(U.firstname) LIKE CONCAT('%', UPPER(:name), '%') OR UPPER(U.lastname) LIKE CONCAT('%', UPPER(:name), '%')",
                    User.class
            ).setParameter("name", name).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return getSession().createQuery(
                    "SELECT U FROM User AS U WHERE U.email = :email",
                    User.class
            ).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByPhone(String numberPhone) {
        try {
            return getSession().createQuery(
                    "SELECT U FROM User AS U WHERE U.numberPhone LIKE CONCAT('%', :numberPhone)",
                    User.class
            ).setParameter("numberPhone", numberPhone).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

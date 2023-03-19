package com.example.RESTful.Library.dao.impl.user;

import com.example.RESTful.Library.dao.impl.AbstractDaoImpl;
import com.example.RESTful.Library.dao.dao.user.AuthorDao;
import com.example.RESTful.Library.model.user.Author;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AuthorDaoImpl extends AbstractDaoImpl<Author> implements AuthorDao {
    public AuthorDaoImpl(Class<Author> type, SessionFactory sessionFactory) {
        super(type, sessionFactory);
    }

    public List<Author> findByName(String name) {
        return getSession().createQuery(
                "SELECT A FROM Author AS A WHERE A.firstname LIKE %:name% OR A.lastname LIKE %:name%",
                Author.class
        ).setParameter("name", name).getResultList();
    }
}

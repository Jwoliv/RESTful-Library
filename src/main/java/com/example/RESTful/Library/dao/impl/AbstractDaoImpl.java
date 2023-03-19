package com.example.RESTful.Library.dao.impl;

import com.example.RESTful.Library.dao.dao.AbstractDao;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {
    private final Class<T> type;
    private final SessionFactory sessionFactory;

    public AbstractDaoImpl(Class<T> type, SessionFactory sessionFactory) {
        this.type = type;
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<T> findAll() {
        CriteriaQuery<T> query = getSession().getCriteriaBuilder().createQuery(type);
        query.select(query.from(type));
        return getSession().createQuery(query).getResultList();
    }

    public T findById(Long id) {
        return getSession().get(type, id);
    }

    @Transactional
    public void save(T element) {
        getSession().persist(element);
    }

    @Transactional
    public void delete(T element) {
        getSession().remove(element);
    }

    @Transactional
    public void update(T element) {
        getSession().merge(element);
    }
}
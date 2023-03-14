package com.example.RESTful.Library.service;

import com.example.RESTful.Library.dao.impl.AbstractDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public abstract class AbstractService<T, D extends AbstractDao<T>> {
    private final D dao;

    @Autowired
    protected AbstractService(D dao) {
        this.dao = dao;
    }

    public List<T> findAll() {
        return dao.findAll();
    }
    public T findById(Long id) {
        return dao.findById(id);
    }
    public void save(T element) {
        dao.save(element);
    }
    public void delete(T element) {
        dao.delete(element);
    }
    public void update(T element) {
        dao.update(element);
    }
}

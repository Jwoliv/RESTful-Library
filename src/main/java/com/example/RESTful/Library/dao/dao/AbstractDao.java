package com.example.RESTful.Library.dao.dao;

import java.util.List;

public interface AbstractDao<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T element);
    void delete(T element);
    void update(T element);
}

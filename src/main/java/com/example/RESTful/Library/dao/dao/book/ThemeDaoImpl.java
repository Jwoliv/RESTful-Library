package com.example.RESTful.Library.dao.dao.book;

import com.example.RESTful.Library.dao.dao.AbstractDaoImpl;
import com.example.RESTful.Library.dao.impl.book.ThemeDao;
import com.example.RESTful.Library.model.book.Theme;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ThemeDaoImpl extends AbstractDaoImpl<Theme> implements ThemeDao {
    public ThemeDaoImpl(Class<Theme> type, SessionFactory sessionFactory) {
        super(type, sessionFactory);
    }
}

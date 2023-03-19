package com.example.RESTful.Library.dao.impl.book;

import com.example.RESTful.Library.dao.impl.AbstractDaoImpl;
import com.example.RESTful.Library.dao.dao.book.BookDao;
import com.example.RESTful.Library.model.book.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class BookDaoImpl extends AbstractDaoImpl<Book> implements BookDao {
    public BookDaoImpl(Class<Book> type, SessionFactory sessionFactory) {
        super(type, sessionFactory);
    }

    @Override
    public List<Book> findByIsTaken(Boolean isTaken) {
        return getSession().createQuery(
                "SELECT B FROM Book AS B WHERE B.isTaken = :isTaken",
                Book.class
        ).setParameter("isTaken", isTaken).getResultList();
    }
}

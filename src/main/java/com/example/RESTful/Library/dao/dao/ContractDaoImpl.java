package com.example.RESTful.Library.dao.dao;

import com.example.RESTful.Library.dao.impl.ContractDao;
import com.example.RESTful.Library.model.Contract;
import org.hibernate.SessionFactory;

import java.util.List;

public class ContractDaoImpl extends AbstractDaoImpl<Contract> implements ContractDao {
    public ContractDaoImpl(Class<Contract> type, SessionFactory sessionFactory) {
        super(type, sessionFactory);
    }

    @Override
    public List<Contract> findIsOverdue(Boolean isOverdue) {
        return getSession().createQuery(
                        "SELECT C FROM Contract AS C WHERE C.isOverdue = :isOverdue",
                        Contract.class
                )
                .setParameter("isOverdue", isOverdue).getResultList();
    }

    @Override
    public List<Contract> findIsReturned(Boolean isReturned) {
        return getSession().createQuery(
                        "SELECT C FROM Contract AS C WHERE C.isReturned = :isReturned",
                        Contract.class
                )
                .setParameter("isReturned", isReturned).getResultList();
    }
}

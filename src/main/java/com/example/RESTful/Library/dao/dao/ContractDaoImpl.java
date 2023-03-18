package com.example.RESTful.Library.dao.dao;

import com.example.RESTful.Library.dao.impl.ContractDao;
import com.example.RESTful.Library.model.Contract;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ContractDaoImpl extends AbstractDaoImpl<Contract> implements ContractDao {
    public ContractDaoImpl(Class<Contract> type, SessionFactory sessionFactory) {
        super(type, sessionFactory);
    }

    @Override
    public List<Contract> findIsOverdue() {
        return getSession().createQuery(
                        "SELECT C FROM Contract AS C WHERE C.dateOfReturn < :dateOfReturn",
                        Contract.class
                )
                .setParameter("dateOfReturn", LocalDate.now()).getResultList();
    }

    @Override
    public List<Contract> findIsOverdueByUserId(Long userId) {
        return getSession().createQuery(
                        "SELECT C FROM Contract AS C WHERE C.dateOfReturn < :dateOfReturn AND C.user.id = :id",
                        Contract.class
                )
                .setParameter("dateOfReturn", LocalDate.now())
                .setParameter("id", userId)
                .getResultList();
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

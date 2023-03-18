package com.example.RESTful.Library.dao.impl;

import com.example.RESTful.Library.model.Contract;

import java.util.List;

public interface ContractDao extends AbstractDao<Contract> {
    List<Contract> findIsOverdue();
    List<Contract> findIsOverdueByUserId(Long userId);
    List<Contract> findIsReturned(Boolean isReturned);
}

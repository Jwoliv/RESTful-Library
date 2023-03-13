package com.example.RESTful.Library.dao.impl;

import com.example.RESTful.Library.model.Contract;

import java.util.List;

public interface ContractDao extends AbstractDao<Contract> {
    List<Contract> findIsOverdue(Boolean isOverdue);
    List<Contract> findIsReturned(Boolean isReturned);
}

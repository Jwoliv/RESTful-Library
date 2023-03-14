package com.example.RESTful.Library.service;

import com.example.RESTful.Library.dao.dao.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import org.springframework.stereotype.Service;

@Service
public class ContractService extends AbstractService<Contract, ContractDaoImpl> {
    protected ContractService(ContractDaoImpl dao) {
        super(dao);
    }
}

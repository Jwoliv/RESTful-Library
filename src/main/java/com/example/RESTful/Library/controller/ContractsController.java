package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.dao.dao.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractsController extends AbstractController<Contract, ContractDaoImpl, ContractService> {
    @Autowired
    protected ContractsController(ContractService service) {
        super(service);
    }
    @GetMapping("/is-overdue")
    public ResponseEntity<List<Contract>> contractIsOverdue() {
        return ResponseEntity.ok(service.findIsOverdue());
    }
    @Override
    public ResponseEntity<Contract> saveElement(@RequestBody Contract contract) {
        service.createContract(contract);
        return ResponseEntity.ok(contract);
    }
    @Override
    public ResponseEntity<List<Contract>> deleteElement(Long id) {
        service.checkAvailabilityForDeleteContract(id);
        return ResponseEntity.ok(service.findAll());
    }
    @PatchMapping("/{id}/return-book")
    public ResponseEntity<List<Contract>> returnBook(@PathVariable Long id) {
        service.removeContractAfterReturned(id);
        return ResponseEntity.ok(service.findAll());
    }
    @PatchMapping("/{id}/extend-term")
    public ResponseEntity<Contract> extendTermOfContract(@PathVariable Long id) {
        service.extendDateOfReturn(id);
        return ResponseEntity.ok(service.findById(id));
    }
    @PatchMapping("/{id}/lost-book")
    public ResponseEntity<List<Contract>> lostBookOfContract(@PathVariable Long id) {
        service.removeBookAndContractAfterLost(id);
        return ResponseEntity.ok(service.findAll());
    }
}

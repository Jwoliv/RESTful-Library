package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.controller.AbstractController;
import com.example.RESTful.Library.dao.dao.user.UserDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController extends AbstractController<User, UserDaoImpl, UserService> {
    private final ContractService contractService;
    protected UsersController(UserService service, ContractService contractService) {
        super(service);
        this.contractService = contractService;
    }
    @GetMapping("/{id}/current-books")
    public ResponseEntity<List<Book>> currentBook(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getCurrentTakenBook());
    }
    @GetMapping("/{id}/previous-books")
    public ResponseEntity<List<Book>> previousBooks(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getPreviousBooks());
    }
    @GetMapping("/{id}/overdue-contracts")
    public ResponseEntity<List<Contract>> overdueContractsOfUser(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.findIsOverdueByUser(id));
    }
    @Override
    public ResponseEntity<List<User>> deleteElement(Long id) {
        User user = service.findById(id);
        if (user != null && user.getCurrentTakenBook().isEmpty() && user.getContracts().isEmpty()) {
            service.delete(user);
        }
        return ResponseEntity.ok(service.findAll());
    }
}

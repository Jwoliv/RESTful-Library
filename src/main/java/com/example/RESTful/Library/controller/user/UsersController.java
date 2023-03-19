package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.controller.AbstractController;
import com.example.RESTful.Library.dao.dao.user.UserDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController extends AbstractController<User, UserDaoImpl, UserService> {
    private final ContractService contractService;
    protected UsersController(UserService service, ContractService contractService) {
        super(service);
        this.contractService = contractService;
    }
    @GetMapping("/search-by-name")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByName(name));
    }
    @GetMapping("/search-by-email")
    public ResponseEntity<User> searchUsersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }
    @GetMapping("/search-by-phone")
    public ResponseEntity<User> searchUsersByNumberPhone(@RequestParam String numberPhone) {
        return ResponseEntity.ok(service.findByPhone(numberPhone));
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
        service.delete(user);
        return ResponseEntity.ok(service.findAll());
    }
}

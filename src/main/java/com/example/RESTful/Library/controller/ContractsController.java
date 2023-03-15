package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.dao.dao.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.book.BookService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractsController extends AbstractController<Contract, ContractDaoImpl, ContractService> {
    private final UserService userService;
    private final BookService bookService;
    @Autowired
    protected ContractsController(
            ContractService service,
            UserService userService,
            BookService bookService
    ) {
        super(service);
        this.userService = userService;
        this.bookService = bookService;
    }
    @Override
    public ResponseEntity<Contract> saveElement(@RequestBody Contract contract) {
        createContract(contract);
        return ResponseEntity.ok(contract.getId() != null ? contract : null);
    }
    @GetMapping("/{id}/book")
    public ResponseEntity<Book> bookOfTheContract(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getBook());
    }
    @GetMapping("/{id}/user")
    public ResponseEntity<User> userOfTheContract(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id).getUser());
    }
    @PatchMapping("/{id}/return-book")
    public ResponseEntity<List<Contract>> returnBook(@PathVariable Long id) {
        removeContractAfterReturned(id);
        return ResponseEntity.ok(service.findAll());
    }


    public void removeContractAfterReturned(Long id) {
        Contract contract = service.findById(id);
        if (contract != null && service.findById(id) != null) {
            updateUserIfHeOverdueTheBook(contract);
            updateBookAfterReturnContract(contract.getBook());
            service.delete(contract);
        }
    }
    public void createContract(Contract contract) {
        if (!contract.getBook().getIsTaken()) {
            setFieldsForBorrow(contract);
        }
    }
    public void updateUserIfHeOverdueTheBook(Contract contract) {
        int resultOfCompareDates = contract.getDateOfReturn().compareTo(LocalDate.now());
        User user = contract.getUser();
        if (resultOfCompareDates > 0 && user != null) {
            user.setNumberOfOverdue(user.getNumberOfOverdue() + 1);
            userService.update(user);
        }
    }
    public void updateBookAfterReturnContract(Book book) {
        if (book != null) {
            book.getPreviousOwners().add(book.getCurrentOwner());
            book.setCurrentOwner(null);
            book.setIsTaken(false);
            bookService.update(book);
        }
    }
    public void setFieldsForBorrow(Contract contract) {
        Book book = contract.getBook();
        book.setIsTaken(true);
        contract.setBook(book);
        contract.setDateOfTake(LocalDate.now());
        contract.setDateOfReturn(LocalDate.now().plusWeeks(2));
        contract.setIsReturned(false);
        service.save(contract);
    }
}

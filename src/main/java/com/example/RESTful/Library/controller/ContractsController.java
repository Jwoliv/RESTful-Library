package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.dao.dao.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.book.BookService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if (!contract.getUser().getIsBanned()) {
            createContract(contract);
            return ResponseEntity.ok(contract.getId() != null ? contract : null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @Override
    public ResponseEntity<List<Contract>> deleteElement(Long id) {
        Contract contract = service.findById(id);
        if (contract != null && !contract.getBook().getIsTaken() && contract.getIsReturned()) {
            service.delete(contract);
        }
        return ResponseEntity.ok(service.findAll());
    }
    @PatchMapping("/{id}/return-book")
    public ResponseEntity<List<Contract>> returnBook(@PathVariable Long id) {
        removeContractAfterReturned(id);
        return ResponseEntity.ok(service.findAll());
    }
    @PatchMapping("/{id}/extend-term")
    public ResponseEntity<Contract> extendTermOfContract(@PathVariable Long id) {
        extendDateOfReturn(id);
        return ResponseEntity.ok(service.findById(id));
    }
    @PatchMapping("/{id}/lost-book")
    public ResponseEntity<List<Contract>> lostBookOfContract(@PathVariable Long id) {
        Contract contract = service.findById(id);
        if (contract != null && checkDateOfReturned(contract)) {
            removeBookAndContractAfterLost(contract);
        }
        return ResponseEntity.ok(service.findAll());
    }
    public void extendDateOfReturn(Long id) {
        Contract contract = service.findById(id);
        if (contract != null) {
            contract.setDateOfReturn(contract.getDateOfReturn().plusWeeks(2));
            service.update(contract);
        }
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
        if (contract != null && !contract.getBook().getIsTaken() && contract.getBook().getIsAvailiable()) {
            setFieldsForBorrow(contract);
        }
    }
    public void updateUserIfHeOverdueTheBook(Contract contract) {
        if (contract != null) {
            int resultOfCompareDates = contract.getDateOfReturn().compareTo(LocalDate.now());
            User user = contract.getUser();
            if (resultOfCompareDates > 0 && user != null) {
                user.setNumberOfOverdue(user.getNumberOfOverdue() + 1);
                userService.update(user);
            }
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
        if (contract != null) {
            Book book = contract.getBook();
            if (book != null) {
                updateBookFieldsOnBorrow(contract, book);
                updateContractFieldsOnBorrow(contract, book);
            }
        }
    }
    public void updateBookFieldsOnBorrow(Contract contract, Book book) {
        if (contract != null && book != null) {
            book.setIsTaken(true);
            book.setCurrentOwner(contract.getUser());
            bookService.update(book);
        }
    }
    public void updateContractFieldsOnBorrow(Contract contract, Book book) {
        if (contract != null && book != null) {
            contract.setBook(book);
            contract.setDateOfTake(LocalDate.now());
            contract.setDateOfReturn(LocalDate.now().plusWeeks(2));
            contract.setIsReturned(false);
            service.save(contract);
        }
    }
    public boolean checkDateOfReturned(Contract contract) {
        if (contract != null) {
            LocalDate maxDateOfReturned = contract.getDateOfReturn().plusMonths(2);
            return !maxDateOfReturned.isBefore(contract.getDateOfReturn());
        }
        return false;
    }
    public void removeBookAndContractAfterLost(Contract contract) {
        if (contract != null) {
            Book book = contract.getBook();
            User user = contract.getUser();
            if (user != null && book != null) {
                user.setIsBanned(true);
                userService.update(user);
                service.delete(contract);
                bookService.delete(book);
            }
        }
    }
}

package com.example.RESTful.Library.service;

import com.example.RESTful.Library.dao.dao.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.book.BookService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContractService extends AbstractService<Contract, ContractDaoImpl> {
    private final UserService userService;
    private final BookService bookService;
    protected ContractService(ContractDaoImpl dao, UserService userService, BookService bookService) {
        super(dao);
        this.userService = userService;
        this.bookService = bookService;
    }
    public List<Contract> findIsOverdue() {
        return getDao().findIsOverdue();
    }
    public List<Contract> findIsOverdueByUser(Long userId) {
        return getDao().findIsOverdueByUserId(userId);
    }
    public void extendDateOfReturn(Long id) {
        Contract contract = findById(id);
        if (contract != null) {
            contract.setDateOfReturn(contract.getDateOfReturn().plusWeeks(2));
            update(contract);
        }
    }
    public void removeContractAfterReturned(Long id) {
        Contract contract = findById(id);
        if (contract != null && findById(id) != null) {
            userService.updateUserIfHeOverdueTheBook(contract);
            bookService.updateBookAfterReturnContract(contract.getBook());
            delete(contract);
        }
    }
    public void createContract(Contract contract) {
        if (contract != null && !contract.getBook().getIsTaken() && contract.getBook().getIsAvailiable()) {
            setFieldsForBorrow(contract);
        }
    }
    public void setFieldsForBorrow(Contract contract) {
        if (contract != null) {
            Book book = contract.getBook();
            if (book != null) {
                updateBookFieldsOnBorrow(contract, book);
                setContractFieldsOnBorrow(contract, book);
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
    public void setContractFieldsOnBorrow(Contract contract, Book book) {
        if (contract != null && book != null) {
            contract.setBook(book);
            contract.setDateOfTake(LocalDate.now());
            contract.setDateOfReturn(LocalDate.now().plusWeeks(2));
            contract.setIsReturned(false);
            save(contract);
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
                delete(contract);
                bookService.delete(book);
            }
        }
    }
}

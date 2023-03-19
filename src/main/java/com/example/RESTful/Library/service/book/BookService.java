package com.example.RESTful.Library.service.book;

import com.example.RESTful.Library.dao.impl.book.BookDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.AbstractService;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends AbstractService<Book, BookDaoImpl> {
    private ContractService contractService;
    private final UserService userService;

    @Autowired
    public BookService(BookDaoImpl dao, UserService userService) {
        super(dao);
        this.userService = userService;
    }
    @Override
    public void delete(Book book) {
        if (book != null && book.getContract() == null && book.getPreviousOwners().isEmpty()) {
            getDao().delete(book);
        }
    }
    public List<Book> findByIsTaken(Boolean isTaken) {
        return getDao().findByIsTaken(isTaken);
    }
    public void updateBookAfterReturnContract(Book book) {
        if (book != null) {
            book.getPreviousOwners().add(book.getCurrentOwner());
            book.setCurrentOwner(null);
            book.setIsTaken(false);
            update(book);
        }
    }
    public void updateBookFieldsOnBorrow(Contract contract, Book book) {
        if (contract != null && book != null) {
            book.setIsTaken(true);
            book.setCurrentOwner(contract.getUser());
            update(book);
        }
    }
    public void makeBookUnavailableDuToLoss(Long id) {
        Contract contract = contractService.findById(id);
        if (contract != null && contractService.checkDateOfReturned(contract)) {
            Book book = contract.getBook();
            User user = contract.getUser();
            if (user != null && book != null) {
                user.setIsBanned(true);
                userService.update(user);
                contractService.delete(contract);
                book.setIsAvailiable(false);
                update(book);
            }
        }
    }
}

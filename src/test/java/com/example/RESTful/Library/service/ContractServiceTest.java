package com.example.RESTful.Library.service;

import com.example.RESTful.Library.dao.impl.ContractDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.book.BookService;
import com.example.RESTful.Library.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContractServiceTest {
    private ContractService contractService;

    @Mock
    private ContractDaoImpl contractDao;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        contractService = new ContractService(contractDao, userService, bookService);
    }

    @Test
    void findIsOverdue_whenIsEmptyList() {
        List<Contract> contracts = List.of();
        when(contractDao.findIsOverdue()).thenReturn(contracts);

        var resultContracts = contractService.findIsOverdue();
        assertEquals(contracts, resultContracts);
        assertEquals(0, resultContracts.size());
    }

    @Test
    void findIsOverdueByUser_whenIsEmptyList() {
        var userId = 1L;
        List<Contract> contracts = List.of();
        when(contractDao.findIsOverdueByUserId(userId)).thenReturn(contracts);

        var resultContracts = contractService.findIsOverdueByUser(userId);
        assertEquals(contracts, resultContracts);
        assertEquals(0, resultContracts.size());
    }

    @Test
    void findIsOverdue_whenHasContracts() {
        var contract1 = new Contract();
        contract1.setId(1L);
        contract1.setUser(userService.findById(1L));
        contract1.setBook(bookService.findById(1L));
        contract1.setDateOfTake(LocalDate.now());
        contract1.setDateOfReturn(LocalDate.now().plusMonths(2));

        var contract2 = new Contract();
        contract2.setId(2L);
        contract2.setUser(userService.findById(2L));
        contract2.setBook(bookService.findById(2L));
        contract2.setDateOfTake(LocalDate.now());
        contract2.setDateOfReturn(LocalDate.now().plusMonths(2));

        List<Contract> contracts = List.of(contract1, contract2);
        when(contractDao.findIsOverdue()).thenReturn(contracts);

        var resultContracts = contractService.findIsOverdue();
        assertEquals(contracts, resultContracts);
        assertEquals(contracts.size(), resultContracts.size());
    }

    @Test
    void findIsOverdueByUser_whenHasContracts() {
        var userId = 1L;

        var contract1 = new Contract();
        contract1.setId(1L);
        contract1.setUser(userService.findById(1L));
        contract1.setBook(bookService.findById(1L));
        contract1.setDateOfTake(LocalDate.now());
        contract1.setDateOfReturn(LocalDate.now().plusMonths(2));

        var contract2 = new Contract();
        contract2.setId(2L);
        contract2.setUser(userService.findById(1L));
        contract2.setBook(bookService.findById(2L));
        contract2.setDateOfTake(LocalDate.now());
        contract2.setDateOfReturn(LocalDate.now().plusMonths(2));

        List<Contract> contracts = List.of(contract1, contract2);
        when(contractDao.findIsOverdueByUserId(userId)).thenReturn(contracts);

        var resultContracts = contractService.findIsOverdueByUser(userId);
        assertEquals(contracts, resultContracts);
        assertEquals(contracts.size(), resultContracts.size());
    }
    @Test
    public void testFindIsOverdueByUser() {
        Long userId = 1L;
        List<Contract> overdueContracts = new ArrayList<>();
        overdueContracts.add(new Contract());
        overdueContracts.add(new Contract());

        when(contractDao.findIsOverdueByUserId(userId)).thenReturn(overdueContracts);

        List<Contract> foundContracts = contractService.findIsOverdueByUser(userId);
        assertEquals(overdueContracts, foundContracts);
    }

    @Test
    public void testExtendDateOfReturn() {
        Contract contract = new Contract();
        contract.setDateOfReturn(LocalDate.now());

        when(contractDao.findById(1L)).thenReturn(contract);

        contractService.extendDateOfReturn(1L);
        assertEquals(LocalDate.now().plusWeeks(2), contract.getDateOfReturn());
    }

    @Test
    public void testRemoveContractAfterReturned() {
        User user = new User();
        Book book = new Book();
        book.setIsTaken(true);
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setIsReturned(true);
        contract.setUser(user);
        contract.setBook(book);

        when(contractDao.findById(1L)).thenReturn(contract);

        contractService.removeContractAfterReturned(1L);
        assertEquals(contract, contractService.findById(1L));
    }

    @Test
    public void testCreateContractWithValidData() {
        User user = new User();
        user.setIsBanned(false);
        Book book = new Book();
        book.setIsTaken(false);
        book.setIsAvailiable(true);
        Contract contract = new Contract();
        contract.setUser(user);
        contract.setBook(book);

        contractService.createContract(contract);
        verify(contractDao, times(1)).save(contract);
    }

    @Test
    public void testCreateContractWithBannedUser() {
        User user = new User();
        user.setIsBanned(true);
        Book book = new Book();
        book.setIsTaken(false);
        book.setIsAvailiable(true);
        Contract contract = new Contract();
        contract.setUser(user);
        contract.setBook(book);

        contractService.createContract(contract);
        verify(contractDao, never()).save(contract);
    }

    @Test
    public void testCreateContractWithTakenBook() {
        User user = new User();
        user.setIsBanned(false);
        Book book = new Book();
        book.setIsTaken(true);
        book.setIsAvailiable(false);
        Contract contract = new Contract();
        contract.setUser(user);
        contract.setBook(book);

        contractService.createContract(contract);
        verify(contractDao, never()).save(contract);
    }
}
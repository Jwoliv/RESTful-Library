package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContractsControllerTest {

    @Mock
    private ContractService contractService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private ContractsController contractsController;

    @Test
    void testSaveElement() {
        Contract contract = new Contract();

        var response = contractsController.saveElement(contract);

        verify(contractService, times(1)).createContract(contract);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contract, response.getBody());
    }

    @Test
    void testDeleteElement() {
        Long id = 123L;

        var response = contractsController.deleteElement(id);

        verify(contractService, times(1)).checkAvailabilityForDeleteContract(id);
        verify(contractService, times(1)).findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testContractIsOverdue() {
        when(contractService.findIsOverdue()).thenReturn(List.of(new Contract()));

        var response = contractsController.contractIsOverdue();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testReturnBook() {
        var id = 123L;

        var response = contractsController.returnBook(id);

        verify(contractService, times(1)).removeContractAfterReturned(id);
        verify(contractService, times(1)).findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testExtendTermOfContract() {
        var id = 123L;
        var contract = new Contract();
        when(contractService.findById(id)).thenReturn(contract);

        var response = contractsController.extendTermOfContract(id);

        verify(contractService, times(1)).extendDateOfReturn(id);
        verify(contractService, times(1)).findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contract, response.getBody());
    }
    
    @Test
    void testLostBookOfContract() {
        var id = 12L;
         List<Contract> contracts = List.of(new Contract(), new Contract());

        when(contractService.findAll()).thenReturn(contracts);
        var response = contractsController.lostBookOfContract(id);

        verify(bookService, times(1)).makeBookUnavailableDuToLoss(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contracts, response.getBody());
    }
}
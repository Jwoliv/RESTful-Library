package com.example.RESTful.Library.controller.user;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.ContractService;
import com.example.RESTful.Library.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private ContractService contractService;
    @InjectMocks
    private UsersController usersController;
    @Test
    public void testSearchUsersByName_whenIsNotEmpty() {
        var name = "John";
        var user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        when(userService.findByName(name)).thenReturn(List.of(user));

        var response = usersController.searchUsersByName(name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), List.of(user));
        assertEquals(Objects.requireNonNull(response.getBody()).size(), List.of(user).size());
    }

    @Test
    public void testSearchUsersByName_whenIsEmpty() {
        var name = "John";
        when(userService.findByName(name)).thenReturn(new ArrayList<>());

        var response = usersController.searchUsersByName(name);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(response.getBody(), new ArrayList<>());
        assertEquals(Objects.requireNonNull(response.getBody()).size(),0);
    }

    @Test
    public void testSearchUsersByEmail_whenIsNotEmpty() {
        var email = "johndoe@example.com";
        var user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        when(userService.findByEmail(email)).thenReturn(user);

        var response = usersController.searchUsersByEmail(email);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }
    @Test
    public void testSearchUsersByEmail_whenIsEmpty() {
        var email = "johndoe@example.com";
        when(userService.findByEmail(email)).thenReturn(null);

        var response = usersController.searchUsersByEmail(email);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(null);
    }
    @Test
    public void testSearchUsersByNumberPhone_whenIsNotEmpty() {
        var numberPhone = "+1234567890";
        var user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setEmail("+1234567890");
        when(userService.findByPhone(numberPhone)).thenReturn(user);

        var response = usersController.searchUsersByNumberPhone(numberPhone);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }
    @Test
    void testCurrentBook_whenIsNotEmpty() {
        var user = new User();
        user.setId(1L);
        var book = new Book();
        book.setId(10L);
        book.setTitle("Title example for test");
        user.getCurrentTakenBook().add(book);
        when(userService.findById(1L)).thenReturn(user);

        var response = usersController.currentBook(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response.getBody()).size(), user.getCurrentTakenBook().size());
        assertThat(response.getBody()).isEqualTo(user.getCurrentTakenBook());
    }
    @Test
    void testPreviousBooks_whenIsNotEmpty() {
        var user = new User();
        user.setId(1L);
        var book = new Book();
        book.setId(10L);
        book.setTitle("Title example for test");
        user.getPreviousBooks().add(book);
        when(userService.findById(1L)).thenReturn(user);

        var response = usersController.previousBooks(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response.getBody()).size(), user.getPreviousBooks().size());
        assertThat(response.getBody()).isEqualTo(user.getPreviousBooks());
    }
    @Test
    void testOverdueContractsOfUser_whenIsNotEmpty() {
        var user = new User();
        user.setId(1L);
        var contract = new Contract();
        contract.setId(10L);
        contract.setUser(user);
        user.getContracts().add(contract);
        when(contractService.findIsOverdueByUser(1L)).thenReturn(List.of(contract));

        var response = usersController.overdueContractsOfUser(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response.getBody()).size(), List.of(contract).size());
        assertThat(response.getBody()).isEqualTo(List.of(contract));
    }
}
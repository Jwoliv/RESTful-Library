package com.example.RESTful.Library.model.user;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Table(name = "_user")
public class User extends Person {
    @Email
    private String email;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "number_of_overude")
    private Integer numberOfOverdue;
    @OneToMany(mappedBy = "currentOwner", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    @Column(name = "current_taken_book")
    private List<Book> currentTakenBook = new ArrayList<>();
    @ManyToMany(mappedBy = "previousOwners", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Column(name = "previous_books")
    private List<Book> previousBooks = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Column(name = "contracts")
    private List<Contract> contracts = new ArrayList<>();

    @JsonIgnore
    public List<Contract> getContracts() {
        return contracts;
    }
    @JsonIgnore
    public List<Book> getPreviousBooks() {
        return previousBooks;
    }
    @JsonIgnore
    public List<Book> getCurrentTakenBook() {
        return currentTakenBook;
    }
}

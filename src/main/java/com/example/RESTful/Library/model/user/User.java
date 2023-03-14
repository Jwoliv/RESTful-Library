package com.example.RESTful.Library.model.user;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
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
    private String numberPhone;
    private Integer numberOfOverdue;
    @OneToMany(mappedBy = "currentOwner", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Book> currentTakenBook = new ArrayList<>();
    @ManyToMany(mappedBy = "previousOwners", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Book> previousBooks = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Contract> contracts = new ArrayList<>();
}

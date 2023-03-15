package com.example.RESTful.Library.model.book;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.model.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "is_taken")
    private Boolean isTaken;
    @Column(name = "date_of_create")
    private LocalDate dateOfCreate;
    private Float rating;
    @ManyToOne
    private Author author;
    @JoinColumn(name = "current_owner")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private User currentOwner;
    @ManyToMany
    @ToString.Exclude
    private List<User> previousOwners = new ArrayList<>();
    @ManyToOne
    private Theme theme;
    @OneToOne(mappedBy = "book")
    private Contract contract;
}

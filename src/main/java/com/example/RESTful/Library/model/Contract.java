package com.example.RESTful.Library.model;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate dateOfTake;
    private LocalDate dateOfReturn;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToOne
    @JsonIgnore
    private Book book;
    private Boolean isReturned;
}

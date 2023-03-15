package com.example.RESTful.Library.model;

import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.user.User;
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
    @Column(name = "date_of_take")
    private LocalDate dateOfTake;
    @Column(name = "date_of_return")
    private LocalDate dateOfReturn;
    @ManyToOne
    private User user;
    @OneToOne
    private Book book;
    @Column(name = "is_returned")
    private Boolean isReturned;
}

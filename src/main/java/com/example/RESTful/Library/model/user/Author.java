package com.example.RESTful.Library.model.user;

import com.example.RESTful.Library.model.book.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Author extends Person {
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();
}

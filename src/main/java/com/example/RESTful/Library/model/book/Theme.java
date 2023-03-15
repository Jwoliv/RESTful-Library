package com.example.RESTful.Library.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    @JsonIgnore
    public List<Book> getBooks() {
        return books;
    }
}

package com.example.RESTful.Library.config;

import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.book.Book;
import com.example.RESTful.Library.model.book.Theme;
import com.example.RESTful.Library.model.user.Author;
import com.example.RESTful.Library.model.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelConfig {
    @Bean
    public Class<Book> bookClass() {
        return Book.class;
    }
    @Bean
    public Class<User> userClass() {
        return User.class;
    }
    @Bean
    public Class<Author> authorClass() {
        return Author.class;
    }
    @Bean
    public Class<Contract> contractClass() {
        return Contract.class;
    }
    @Bean
    public Class<Theme> themeClass() {
        return Theme.class;
    }
}

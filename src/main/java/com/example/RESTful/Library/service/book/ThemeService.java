package com.example.RESTful.Library.service.book;

import com.example.RESTful.Library.dao.impl.book.ThemeDaoImpl;
import com.example.RESTful.Library.model.book.Theme;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ThemeService extends AbstractService<Theme, ThemeDaoImpl> {
    protected ThemeService(ThemeDaoImpl dao) {
        super(dao);
    }
}

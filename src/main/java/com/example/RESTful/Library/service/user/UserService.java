package com.example.RESTful.Library.service.user;

import com.example.RESTful.Library.dao.dao.user.UserDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService extends AbstractService<User, UserDaoImpl> {
    protected UserService(UserDaoImpl dao) {
        super(dao);
    }
    public List<User> findByName(String name) {
        return getDao().findByName(name);
    }
    public List<User> findByEmail(String email) {
        return getDao().findByName(email);
    }
    public void updateUserIfHeOverdueTheBook(Contract contract) {
        if (contract != null) {
            int resultOfCompareDates = contract.getDateOfReturn().compareTo(LocalDate.now());
            User user = contract.getUser();
            if (resultOfCompareDates > 0 && user != null) {
                user.setNumberOfOverdue(user.getNumberOfOverdue() + 1);
                update(user);
            }
        }
    }
}

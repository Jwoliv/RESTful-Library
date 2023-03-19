package com.example.RESTful.Library.service.user;

import com.example.RESTful.Library.dao.dao.user.UserDaoImpl;
import com.example.RESTful.Library.model.Contract;
import com.example.RESTful.Library.model.user.User;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class UserService extends AbstractService<User, UserDaoImpl> {
    protected UserService(UserDaoImpl dao) {
        super(dao);
    }

    @Override
    public void delete(User user) {
        if (user != null && user.getCurrentTakenBook().isEmpty() && user.getContracts().isEmpty()) {
            getDao().delete(user);
        }
    }
    @Override
    public void save(User user) {
        if (user == null) {
            return;
        }
        if (findAll().isEmpty()) {
            getDao().save(user);
        } else if (findByEmail(user.getEmail()) == null && findByPhone(user.getNumberPhone()) == null) {
            getDao().save(user);
        }
    }
    public List<User> findByName(String name) {
        if (name != null) {
            return getDao().findByName(name.toUpperCase(Locale.ROOT));
        } return new ArrayList<>();
    }
    public User findByEmail(String email) {
        return getDao().findByEmail(email);
    }
    public User findByPhone(String phoneNumber) {
        return getDao().findByPhone(phoneNumber);
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

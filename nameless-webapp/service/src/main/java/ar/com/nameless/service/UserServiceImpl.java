package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.UserDao;
import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getByUsername(final String username) {
        return userDao.getByUsername(username);
    }

    public void create(final User user) {
        userDao.create(user);
    }
}

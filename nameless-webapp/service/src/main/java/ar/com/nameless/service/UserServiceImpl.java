package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.UserDao;
import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by root on 1/16/17.
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findById(long id) {
        return userDao.findById(id);
    }

    public User newUser(String username, String email, String password) {
        return userDao.newUser(username, email, password);
    }
}

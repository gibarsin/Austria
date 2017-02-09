package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.User;

/**
 * Created by root on 1/16/17.
 */
public interface UserService {

    User findById(long id);

    User newUser(String username, String email, String password);
}

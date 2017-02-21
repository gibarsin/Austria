package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.User;

public interface UserService {

    /**
     * Obtain user's information
     * @param username the username of the user
     * @return  null if the user does not exist;
     *          else the user with its information
     */
    User getByUsername(String username);

    /**
     * Create the user with the information in its state
     * @param user the user to create
     */
    void create(User user);
}

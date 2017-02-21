package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.User;

public interface UserDao {

    /**
     * Obtain user's information
     * @param username the username of the user
     * @return  null if the user does not exist;
     *          else the user with its information
     */
    User getByUsername(String username);

    /**
     * Persist the user with the information in its state.
     * A unique id will be auto generated.
     * @param user the user to persist
     */
    void create(User user);
}

package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.UserDao;
import ar.com.nameless.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by root on 1/16/17.
 */

@Repository
public class UserHibDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User newUser(String username, String email, String password) {
        User user = new User(username, email, password);
        entityManager.persist(user);
        return user;
    }
}

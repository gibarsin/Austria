package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.UserDao;
import ar.com.nameless.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserHibDao implements UserDao {

    /** Table fields **/
    private static final String USERNAME = "username";

    /** Queries **/
    private static final String GET_BY_USERNAME = "from User as u where u.username = :username";

    @PersistenceContext
    private EntityManager entityManager;

    public User getByUsername(final String username) {
        final TypedQuery<User> query = entityManager.createQuery(GET_BY_USERNAME, User.class);

        query.setParameter(USERNAME, username);
        query.setMaxResults(1);

        final List<User> users = query.getResultList();

        return users.isEmpty() ? null : users.get(0);
    }

    @Transactional
    public void create(final User user) {
        entityManager.persist(user);
    }
}

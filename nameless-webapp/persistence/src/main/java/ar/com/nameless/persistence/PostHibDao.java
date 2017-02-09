package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by root on 1/16/17.
 */
@Repository
public class PostHibDao implements PostDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Post newPost(String title, Post.Type type) {
        entityManager.persist(new Post(title, type));
        return null;
    }

    public Post findById(long id) {
        return null;
    }
}

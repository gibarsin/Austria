package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.model.FreshPost;
import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/16/17.
 */
@Repository
public class PostHibDao implements PostDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public FreshPost newFreshPost(String title, Post.Type type, List<Tag> tags) {
        FreshPost freshPost = new FreshPost(new Post(title, type, tags));
        entityManager.persist(freshPost);
        return freshPost;
    }

    @Transactional
    public HotPost newHotPost(String title, Post.Type type, List<Tag> tags) {
        HotPost hotPost = new HotPost(new Post(title, type, tags));
        entityManager.persist(hotPost);
        return hotPost;
    }

    public Post findById(long id) {
        return entityManager.find(Post.class, id);
    }
}

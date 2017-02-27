package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.InteractionDao;
import ar.com.nameless.model.Flag;
import ar.com.nameless.model.Like;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InteractionHibDao implements InteractionDao{

    /** Table fields **/
    private static final String USER = "user";
    private static final String POST = "post";

    /** Queries **/
    private static final String GET_LIKE = "from Like as l where l.user = :user AND l.post = :post";
    private static final String GET_FLAG = "from Flag as f where f.user = :user AND f.post = :post";


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Like like) {
        entityManager.persist(like);
    }

    @Override
    @Transactional
    public void create(Flag flag) {
        entityManager.persist(flag);
    }

    public Like getLike(User user, Post post){
        final TypedQuery<Like> query = entityManager.createQuery(GET_LIKE, Like.class);
        query.setParameter(USER, user);
        query.setParameter(POST, post);

        List<Like> list = query.getResultList();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public void toggleLike(Like like){
        like.setLike(!like.isLike());
        entityManager.merge(like);
    }


    public Flag getFlag(User user, Post post){
        final TypedQuery<Flag> query = entityManager.createQuery(GET_FLAG, Flag.class);
        query.setParameter(USER, user);
        query.setParameter(POST, post);

        List<Flag> list = query.getResultList();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    @Transactional
    public void remove(Like like) {
        entityManager.remove(entityManager.contains(like) ? like : entityManager.merge(like));
    }
}

package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.model.FlaggedPost;
import ar.com.nameless.model.FreshPost;
import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;


@Repository
public class PostHibDao implements PostDao{

    private static final int MAX_POSTS = 15;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public FreshPost newFreshPost(Post post) {
        FreshPost freshPost = new FreshPost(post);
        entityManager.persist(freshPost);
        return freshPost;
    }

    @Transactional
    public HotPost newHotPost(Post post) {
        HotPost hotPost = new HotPost(post);
        entityManager.persist(hotPost);
        return hotPost;
    }

    @Transactional
    public FlaggedPost newFlaggedPost(Post post) {
        FlaggedPost flaggedPost = new FlaggedPost(post);
        entityManager.persist(flaggedPost);
        return flaggedPost;
    }

    @Transactional
    public Post updateRating(long id, int delta){
        Post post = findById(id);
        if(post == null) return null;

        post.setRating(post.getRating()+delta);
        return post;
    }

    @Transactional
    public Post flagPost(long id, int delta){
        Post post = updateRating(id, delta);
        if(post != null){
            post.setFlags(post.getFlags()+1);
        }
        return post;
    }

    public boolean isFreshPost(Post post){
        return findFreshPostByPost(post) != null;
    }

    public boolean isHotPost(Post post){
        return findHotPostByPost(post) != null;
    }

    public Post findById(long id) {
        return entityManager.find(Post.class, id);
    }

    public List<Post> finder(final String search){
        String cleanSearch = search.trim().toLowerCase();
        String titleSearch = "%"+cleanSearch.replace("%", "\\%").replace("_", "\\_")+"%";
        String[] tagSearch = prepareTags(cleanSearch);
        final TypedQuery<Post> query = entityManager.createQuery
                ("select distinct p from Post as p join p.tags t where (LOWER(p.title) like :title)" +
                        "OR (t.tag IN (:tags)) order by p.postId DESC", Post.class);
        query.setParameter("title", titleSearch);
        query.setParameter("tags", Arrays.asList(tagSearch));
        List<Post> posts = query.getResultList();

        //Ver posts
        System.out.println("***Search results***");
        for(Post post : posts){
            System.out.println(post);
        }
        System.out.println("****************");
        return posts;
    }

    public List<HotPost> getHotPosts(long offset) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HotPost> criteria = builder.createQuery(HotPost.class);
        Root<HotPost> hotPostRoot = criteria.from(HotPost.class);
        criteria.select(hotPostRoot);

        if(offset > 0){
            Expression<Number> idExp = hotPostRoot.get("id");
            criteria.where(builder.lt(idExp, offset));
        }

        criteria.orderBy(builder.desc(hotPostRoot.get("id")));
        return entityManager.createQuery(criteria).setMaxResults(MAX_POSTS).getResultList();

/*
        final TypedQuery<HotPost> query = entityManager.createQuery("from HotPost as hp order by hp.id DESC", HotPost.class);
        List<HotPost> list = query.getResultList();
        return list;*/
    }

    @Transactional
    public boolean removeFromHot(long id){
        Post post = findById(id);
        if(post == null) return false;

        HotPost hotPost = findHotPostByPost(post);
        if(hotPost != null){
            entityManager.remove(hotPost);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean removeFromFresh(long id){
        Post post = findById(id);
        if(post == null) return false;

        FreshPost freshPost = findFreshPostByPost(post);
        if(freshPost != null){
            entityManager.remove(freshPost);
            return true;
        }

        return false;
    }


    private HotPost findHotPostByPost(Post post) {
        final TypedQuery<HotPost> query = entityManager.createQuery("from HotPost as hp where hp.post= :post", HotPost.class);
        query.setParameter("post", post);

        List<HotPost> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    private FreshPost findFreshPostByPost(Post post) {
        final TypedQuery<FreshPost> query = entityManager.createQuery("from FreshPost as fp where fp.post= :post", FreshPost.class);
        query.setParameter("post", post);

        List<FreshPost> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    private String[] prepareTags(String search){
        //REMOVER ARTICULOS
        return search.split(" ");
    }
}

package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.InteractionDao;
import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.interfaces.service.InteractionService;
import ar.com.nameless.model.Flag;
import ar.com.nameless.model.Like;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionServiceImpl implements InteractionService{

    private final static int LIKE_POINTS = 10;
    private final static int DISLIKE_POINTS = -10;
    private final static int FLAG_POINTS = -20;

    /*package*/ final static int HOT_BARRIER = 400;
    private final static int FRESH_BARRIER = 300;
    private final static int FLAGS_BARRIER = 10;

    @Autowired
    private PostDao postDao;

    @Autowired
    private InteractionDao interactionDao;

    @Override
    @Transactional
    public boolean likePost(User user, long id) {

        Post post = postDao.findById(id);
        if(post == null){
            return false;
        }

        if(interactionDao.getFlag(user, post) != null){
            return false;
        }

        Like like = interactionDao.getLike(user, post);

        if(like == null){
            interactionDao.create(Like.newLike(user, post));
            post = postDao.updateRating(id, LIKE_POINTS);
        }else{

            if(like.isLike()){
                interactionDao.remove(like);
                post = postDao.updateRating(id, LIKE_POINTS*-1);
            }else{
                interactionDao.toggleLike(like);
                post = postDao.updateRating(id, (DISLIKE_POINTS*-1)+LIKE_POINTS);
            }
        }

        changeRanking(post);

        return true;
    }


    @Override
    @Transactional
    public boolean dislikePost(User user, long id) {
        Post post = postDao.findById(id);
        if(post == null){
            return false;
        }

        if(interactionDao.getFlag(user, post) != null){
            return false;
        }

        Like like = interactionDao.getLike(user, post);

        if(like == null){
            interactionDao.create(Like.newDislike(user, post));
            post = postDao.updateRating(id, DISLIKE_POINTS);
        }else{

            if(like.isLike()){
                interactionDao.toggleLike(like);
                post = postDao.updateRating(id, (LIKE_POINTS*-1)+DISLIKE_POINTS);
            }else{
                interactionDao.remove(like);
                post = postDao.updateRating(id, DISLIKE_POINTS*-1);
            }
        }

        changeRanking(post);

        return true;
    }

    private void changeRanking(Post post){

        if(post.getRating() >= HOT_BARRIER && postDao.isFreshPost(post)){
            System.out.println("*** CHANGING TO HOT ***");
            postDao.removeFromFresh(post.getPostId());
            postDao.newHotPost(post);
        }else if(post.getRating()<= FRESH_BARRIER && postDao.isHotPost(post)){
            System.out.println("*** CHANGING TO FRESH ***");
            postDao.removeFromHot(post.getPostId());
            postDao.newFreshPost(post);
        }

    }

    @Override
    @Transactional
    public boolean flagPost(User user, long id, Flag.Category category) {
        Post post = postDao.findById(id);
        if(post == null){
            return false;
        }

        Flag flag = interactionDao.getFlag(user, post);
        if(flag != null){
            return false;
        }

        flag = new Flag(user, post, category);
        interactionDao.create(flag);

        Like like = interactionDao.getLike(user, post);
        int points = 0;
        if(like != null){
            interactionDao.remove(like);
            if(like.isLike()){
               points = (LIKE_POINTS*-1)+FLAG_POINTS;
            }else{
                points= (DISLIKE_POINTS*-1)+FLAG_POINTS;
            }
        }else{
            points= FLAG_POINTS;
        }

        post = postDao.flagPost(id, points);

        if(post.getFlags() >= FLAGS_BARRIER){
            if(!postDao.removeFromFresh(post.getPostId())){
                postDao.removeFromHot(post.getPostId());
            }
            postDao.newFlaggedPost(post);
        }else {
            changeRanking(post);
        }
        return true;
    }
}

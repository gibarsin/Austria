package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.Flag;
import ar.com.nameless.model.Like;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;

/**
 * Created by root on 2/25/17.
 */
public interface InteractionDao {
    //Likes
    void create(Like like);
    Like getLike(User user, Post post);
    void toggleLike(Like like);
    void remove(Like like);

    //Flags
    void create(Flag flag);
    Flag getFlag(User user, Post post);
}

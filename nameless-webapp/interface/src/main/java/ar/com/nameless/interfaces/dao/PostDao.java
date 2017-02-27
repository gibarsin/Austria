package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.*;

import java.util.List;

public interface PostDao {
    FreshPost newFreshPost(Post post);

    HotPost newHotPost(Post post);

    FlaggedPost newFlaggedPost(Post post);

    Post findById(long id);

    List<Post> finder(String search);

    List<HotPost> getHotPosts(long offset);

    Post updateRating(long id, int delta);

    Post flagPost(long id, int delta);

    boolean isHotPost(Post post);

    boolean isFreshPost(Post post);

    boolean removeFromHot(long id);

    boolean removeFromFresh(long id);

}

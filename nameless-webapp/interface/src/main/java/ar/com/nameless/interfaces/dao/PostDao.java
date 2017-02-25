package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.FreshPost;
import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.Tag;

import java.util.List;

/**
 * Created by root on 1/16/17.
 */
public interface PostDao {
    FreshPost newFreshPost(Post post);

    HotPost newHotPost(Post post);

    Post findById(long id);

    List<Post> finder(String search);

    List<HotPost> getHotPosts(long offset);

    boolean deletePost(long id);

}

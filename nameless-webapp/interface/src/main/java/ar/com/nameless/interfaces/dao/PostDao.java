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
    FreshPost newFreshPost(String title, Post.Type type, List<Tag> tags);

    HotPost newHotPost(String title, Post.Type type, List<Tag> tags);

    Post findById(long id);

    List<Post> finder(String search);

    List<HotPost> getHotPosts(long offset);

    boolean deletePost(long id);

}

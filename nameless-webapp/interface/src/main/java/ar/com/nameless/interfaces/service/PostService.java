package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;

import java.util.List;

/**
 * Created by root on 1/16/17.
 */
public interface PostService {
    Post newFreshPost(String title, String type, List<String> tags);

    Post newHotPost(String title, String type, List<String> tags);

    Post findById(long id);

    List<Post> finder(String search);

    List<HotPost> getHotPosts(long offset);

    boolean deletePost(long id);
}

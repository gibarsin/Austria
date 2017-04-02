package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.IndexedPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;

import java.util.List;

/**
 * Created by root on 1/16/17.
 */
public interface PostService {

    Post newPost(User user, String title, Post.Type type, List<String> tags);

    Post findById(long id);

    List<Post> finder(String search);

    List<? extends IndexedPost> getHotPosts(long offset);

}

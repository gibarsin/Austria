package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.Post;

/**
 * Created by root on 1/16/17.
 */
public interface PostDao {
    Post newPost(String title, Post.Type type);

    Post findById(long id);

}

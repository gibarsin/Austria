package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.Post;

/**
 * Created by root on 1/16/17.
 */
public interface PostService {
    Post newFreshPost(String title, String type);

    Post newHotPost(String title, String type);

    Post findById(long id);
}

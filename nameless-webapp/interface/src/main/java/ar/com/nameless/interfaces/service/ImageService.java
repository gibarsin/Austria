package ar.com.nameless.interfaces.service;

import ar.com.nameless.model.Post;

import java.io.InputStream;

/**
 * Created by root on 2/25/17.
 */
public interface ImageService {
    boolean saveImage(Post post, InputStream inputStream);
}

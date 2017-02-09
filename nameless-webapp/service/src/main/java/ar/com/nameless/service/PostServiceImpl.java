package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 1/16/17.
 */
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    public Post newPost(String title, String type) {
        //TODO: validar el valueOf
        return postDao.newPost(title, Post.Type.valueOf(type));
    }

    public Post findById(long id) {
        return null;
    }
}

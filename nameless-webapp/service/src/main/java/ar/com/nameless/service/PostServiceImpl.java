package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.interfaces.dao.TagDao;
import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by root on 1/16/17.
 */
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    @Autowired
    private TagDao tagDao;

    public Post newFreshPost(String title, String type, List<String> tags) {
        //TODO: validar el valueOf
        List<Tag> tagsList = tagDao.insertTags(tags);
        return postDao.newFreshPost(title, Post.Type.valueOf(type), tagsList).getPost();
    }

    public Post newHotPost(String title, String type, List<String> tags) {
        //TODO: validar el valueOf
        List<Tag> tagsList = tagDao.insertTags(tags);
        return postDao.newHotPost(title, Post.Type.valueOf(type), tagsList).getPost();
    }

    public Post findById(long id) {
        return postDao.findById(id);
    }

    public List<Post> finder(String search) {
        return postDao.finder(search);
    }

    public List<HotPost> getHotPosts() {
        return postDao.getHotPosts();
    }

    public boolean deletePost(long id) {
        return postDao.deletePost(id);
    }
}

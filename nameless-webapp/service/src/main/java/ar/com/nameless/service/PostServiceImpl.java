package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.interfaces.dao.TagDao;
import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.IndexedPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.Tag;
import ar.com.nameless.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Post newPost(User user, String title, Post.Type type, List<String> tags) {
        List<Tag> tagsList = tagDao.insertTags(tags);
        Post post = new Post(user, title, type, tagsList);
        if(user.isVerified()){
            post.setRating(InteractionServiceImpl.HOT_BARRIER*2);
            return postDao.newHotPost(post).getPost();
        }
        return postDao.newFreshPost(post).getPost();
    }

    public Post findById(long id) {
        return postDao.findById(id);
    }

    public List<Post> finder(String search) {
        return postDao.finder(search);
    }

    public List<? extends IndexedPost> getHotPosts(long offset) {
        return postDao.getHotPosts(offset);
    }

}

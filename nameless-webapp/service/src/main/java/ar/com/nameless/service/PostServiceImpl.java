package ar.com.nameless.service;

import ar.com.nameless.interfaces.dao.PostDao;
import ar.com.nameless.interfaces.dao.TagDao;
import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 1/16/17.
 */
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostDao postDao;

    @Autowired
    private TagDao tagDao;


    public Post newFreshPost(String title, String type) {
        //TODO: validar el valueOf
        return postDao.newFreshPost(title, Post.Type.valueOf(type), null).getPost();
    }

    public Post newHotPost(String title, String type) {
        //TODO: validar el valueOf


        String tagOne = "banana";
        String tagTwo = "calamar";
        String tagThree = " CaLaMaR   ";
        List<String> tags = new ArrayList<String>();
        tags.add(tagOne);
        tags.add(tagTwo);
        tags.add(tagThree);

        List<Tag> tagsList = tagDao.insertTags(tags);


        for(Tag t : tagsList){
            System.out.println("Los tags son: " + t.toString());
        }

        return postDao.newHotPost(title, Post.Type.valueOf(type), tagsList).getPost();
    }

    public Post findById(long id) {
        return null;
    }
}

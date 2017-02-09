package ar.com.nameless.interfaces.dao;

import ar.com.nameless.model.Tag;

import java.util.List;

/**
 * Created by root on 1/20/17.
 */
public interface TagDao {

    List<Tag> insertTags(List<String> tagsStringList);
}

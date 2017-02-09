package ar.com.nameless.persistence;

import ar.com.nameless.interfaces.dao.TagDao;
import ar.com.nameless.model.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 1/20/17.
 */
@Repository
public class TagHibDao implements TagDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Tag> insertTags(List<String> tagsStringList){
        Set<String> tagsSet = prepareTags(tagsStringList);
        List<Tag> tagsList = new ArrayList<Tag>();

        for(String tag : tagsSet){
            tagsList.add(putTag(tag));
        }

        return tagsList;
    }

    private Set<String> prepareTags(List<String> tagsList){
        Set<String> tagsSet = new HashSet<String>();
        for(String tag : tagsList){
            tagsSet.add(tag.trim().toLowerCase());
        }
        return tagsSet;
    }


    private Tag putTag(String tag) {
        final TypedQuery<Tag> query = entityManager.createQuery("from Tag as t where t.tag = :tag", Tag.class);
        query.setParameter("tag", tag);
        final List<Tag> list = query.getResultList();

        if(list.isEmpty()){
            Tag aux = new Tag(tag);
            entityManager.persist(aux);
            return aux;
        }

        return list.get(0);
    }
}

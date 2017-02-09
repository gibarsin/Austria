package ar.com.nameless.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by root on 1/16/17.
 */
@Entity
@Table(name ="freshposts")
public class FreshPost extends Post{

    public FreshPost(String title,Type type) {
        super(title, type);
    }

}

package ar.com.nameless.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idgen", sequenceName = "freshposts_id_seq")
@Table(name ="freshposts")
public class FreshPost extends IndexedPost{

    public FreshPost(Post post){
        super(post);
    }
}

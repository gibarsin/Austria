package ar.com.nameless.model;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idgen", sequenceName = "flaggedposts_id_seq")
@Table(name ="flaggedposts")
public class FlaggedPost extends IndexedPost{

    /* package */ FlaggedPost(){
        super();
    }

    public FlaggedPost(Post post){
        super(post);
    }
}

package ar.com.nameless.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(allocationSize = 1, name = "idgen", sequenceName = "hotposts_id_seq")
@Table(name ="hotposts")
public class HotPost extends IndexedPost{

    public HotPost(Post post){
        super(post);
    }
}

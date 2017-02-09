package ar.com.nameless.model;

import javax.persistence.*;

/**
 * Created by root on 1/19/17.
 */

@Entity
@Table(name = "freshposts", uniqueConstraints = @UniqueConstraint(columnNames = {"post_postid"}))
public class FreshPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fresh_posts_id_seq")
    @SequenceGenerator(sequenceName = "fresh_posts_id_seq", name = "fresh_posts_id_seq", allocationSize = 1)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Post post;

    /* package */ FreshPost(){}

    public FreshPost(Post post){
        this.post = post;
    }

    //Getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

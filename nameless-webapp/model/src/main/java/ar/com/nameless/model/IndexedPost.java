package ar.com.nameless.model;

import javax.persistence.*;

@MappedSuperclass
public class IndexedPost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Post post;

    /* package */ IndexedPost(){}

    public IndexedPost(Post post){
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
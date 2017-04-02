package ar.com.nameless.model;

import javax.persistence.*;

@Entity
@Table(name = "flags", uniqueConstraints= @UniqueConstraint(columnNames={"post_postid", "user_id"}))
public class Flag {

    public enum Category {COPYRIGHT, EXPLICIT, OFFENSIVE}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flags_id_seq")
    @SequenceGenerator(sequenceName = "flags_id_seq", name = "flags_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    /* package */ Flag(){
        //Just for hibernate
    }

    public Flag(User user, Post post, Category category){
        this.user = user;
        this.post = post;
        this.category = category;
    }

    @Enumerated(EnumType.STRING)
    private Category category;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package ar.com.nameless.model;

import javax.persistence.*;

@Entity
@Table(name = "likes", uniqueConstraints= @UniqueConstraint(columnNames={"post_postid", "user_id"}))
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_id_seq")
    @SequenceGenerator(sequenceName = "likes_id_seq", name = "likes_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column
    private boolean isLike;

    /* package */ Like(){
        //Just for hibernate
    }

    public static Like newLike(User user, Post post){
        Like like = defaultLike(user, post);
        like.setLike(true);
        return like;
    }

    public static Like newDislike(User user, Post post){
        Like like = defaultLike(user, post);
        like.setLike(false);
        return like;
    }

    private static Like defaultLike(User user, Post post){
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        return like;
    }

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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        this.isLike = like;
    }
}

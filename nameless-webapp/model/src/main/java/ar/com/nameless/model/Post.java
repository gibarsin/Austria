package ar.com.nameless.model;

/*
    Para futuro:
        - Cantidad de veces que fue flagged
        - Date uploaded
 */
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    public enum Type{IMAGE, GIF};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_seq")
    @SequenceGenerator(sequenceName = "posts_id_seq", name = "posts_id_seq", allocationSize = 1)
    private long postId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255) //Necesario ???
    private String url;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private int rating;

    @Column
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "postid"),
            inverseJoinColumns = @JoinColumn(name = "tagid")
    )
    private List<Tag> tags;

    /* package */ Post(){
        //Just for Hibernate
    }

    public Post(String title,Type type, List<Tag> tags) {
        this.title = title;
        this.url = null;
        this.type = type;
        this.rating = 0;
        this.enabled = true;
        this.tags = tags;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return postId == post.postId;

    }

    @Override
    public int hashCode() {
        return (int) (postId ^ (postId >>> 32));
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", rating=" + rating +
                ", enabled=" + enabled +
                '}';
    }
}

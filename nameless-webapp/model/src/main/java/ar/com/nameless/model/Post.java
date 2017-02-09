package ar.com.nameless.model;

/*
    Para futuro:
        - Cantidad de veces que fue flagged
 */
import javax.persistence.*;

@MappedSuperclass
public class Post {

    public enum Type{IMAGE, GIF};

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_seq")
    @SequenceGenerator(sequenceName = "posts_id_seq", name = "posts_id_seq", allocationSize = 1)
    private long id;

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

    /* package */ Post(){
        //Just for Hibernate
    }

    public Post(String title,Type type) {
        this.title = title;
        this.url = null;
        this.type = type;
        this.rating = 0;
        this.enabled = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        return id == post.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", rating=" + rating +
                ", enabled=" + enabled +
                '}';
    }
}

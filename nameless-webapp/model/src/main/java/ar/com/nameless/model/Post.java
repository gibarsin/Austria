package ar.com.nameless.model;

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

    @Column(length = 255)
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
}

package ar.com.nameless.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    public enum Type{
        JPG(".jpg"), JPEG(".jpeg"), PNG(".png"), GIF(".gif");

        private final String extension;

        Type(String extension){
            this.extension = extension;
        }

        public String getExtension(){
            return this.extension;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_id_seq")
    @SequenceGenerator(sequenceName = "posts_id_seq", name = "posts_id_seq", allocationSize = 1)
    private long postId;

    @ManyToOne()
    private User user;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private int rating;

    @Column
    private int flags;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

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

    public Post(User user, String title,Type type, List<Tag> tags) {
        this.user = user;
        this.title = title;
        this.type = type;
        this.tags = tags;
        this.rating = 0;
        this.flags = 0;
        this.uploadDate = new Date();
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDirectory(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getUploadDate());

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(calendar.get(Calendar.YEAR));
        stringBuilder.append("/");
        int aux = calendar.get(Calendar.MONTH)+1;
        String month = aux <10 ? "0"+aux : aux+"";
        stringBuilder.append(month);
        stringBuilder.append("/");
        stringBuilder.append(Long.toString(getPostId(), 36));
        stringBuilder.append(getType().getExtension());

        return stringBuilder.toString();

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
                ", type=" + type +
                ", rating=" + rating +
                ", flags=" + flags +
                ", uploadDate=" + uploadDate +
                //", tags=" + tags +
                '}';
    }
}
package ar.com.nameless.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by root on 1/18/17.P
 */

@Entity
@Table( name= "tags")
public class Tag {

    public final static int MAX_TAGS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_seq")
    @SequenceGenerator(sequenceName = "tags_id_seq", name = "tags_id_seq", allocationSize = 1)
    private long tagId;

    @Column(nullable = false, unique = true)
    private String tag;


    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;

    /* package */ Tag(){
        //Just for Hibernate
    }

    public Tag(String tag){
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag1 = (Tag) o;

        if (tagId != tag1.tagId) return false;
        return tag.equals(tag1.tag);

    }

    @Override
    public int hashCode() {
        return (int) (tagId ^ (tagId >>> 32));
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tag='" + tag + '\'' +
                '}';
    }
}

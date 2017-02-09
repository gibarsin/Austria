package ar.com.nameless.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by root on 1/18/17.
 */

@Entity
@Table( name= "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_id_seq")
    @SequenceGenerator(sequenceName = "tags_id_seq", name = "tags_id_seq", allocationSize = 1)
    private long tagId;

    @Column(nullable = false)
    private String tag;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;

}

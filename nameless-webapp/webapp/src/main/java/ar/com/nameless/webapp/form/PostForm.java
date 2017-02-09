package ar.com.nameless.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by lelv on 1/4/17.
 */
public class PostForm {

    @Size(min = 1, max = 5)
    private String title;
    //private MultipartFile file;

    private List<String> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /*
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }*/

    @Override
    public String toString() {
        return "PostForm{" +
                "title='" + title + '\'' +
                ", tags=" + tags +
                '}';
    }
}

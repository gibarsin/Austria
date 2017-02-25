package ar.com.nameless.webapp.form;

import java.util.List;

/**
 * Created by lelv on 1/4/17.
 */
public class PostNewForm {

    private String title;

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

    @Override
    public String toString() {
        return "PostNewForm{" +
                "title='" + title + '\'' +
                ", tags=" + tags +
                '}';
    }
}

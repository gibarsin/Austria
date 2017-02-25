package ar.com.nameless.webapp.controller.dto;

import ar.com.nameless.model.Post;

public class MinPostDto {

    private String id;
    private String title;
    private String type;
    private String url;

    public MinPostDto(Post post){
        this.id = Long.toString(post.getPostId(), 36);
        this.title = post.getTitle();
        this.type = post.getType().toString();
        this.url = post.getUrl();
    }

    public MinPostDto(){}


    //Getter and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
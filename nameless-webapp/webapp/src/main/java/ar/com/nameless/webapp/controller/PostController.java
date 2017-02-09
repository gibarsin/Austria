package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.HotPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.webapp.controller.dto.MinPostDto;
import ar.com.nameless.webapp.form.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/22/17.
 */
@Path("/posts")
@Component
public class PostController {

    private final static int MAX_QUERY_LENGTH = 128;

    @Autowired
    private PostService postService;

//    @Autowired
//    private Validator validator;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findPosts(@QueryParam("query") final String query){
        if(query == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if(query.length()> MAX_QUERY_LENGTH) return Response.status(Response.Status.REQUEST_URI_TOO_LONG).build();

        List<Post> list = postService.finder(query);
        if(list.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();//devolver vacio
        }

        GenericEntity<List<Post>> posts = new GenericEntity<List<Post>>(list) {};
        return Response.ok(posts).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPostById(@PathParam("id") final String id){

        long dbId = convertId(id);
        System.out.println("-- Id: " + id + "\tdbId: " +dbId+" --");
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        Post post = postService.findById(dbId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(post).build();

    }

    @GET
    @Path(("/hotposts"))
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getHotPosts(){
        //TODO: implement offset and limit
        List<HotPost> list = postService.getHotPosts();
        if(list.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();

        List<MinPostDto> minPostDtos = convertToMinDto(list);

        GenericEntity<List<MinPostDto>> genericEntity = new GenericEntity<List<MinPostDto>>(minPostDtos) {};
        return Response.ok(genericEntity).build();

    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response newPost(@Valid final PostForm postForm){
        System.out.println("PostForm: " + postForm.toString());
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private long convertId(String id){
        long dbId;
        try{
            dbId = Long.valueOf(id, 36);
        }catch (Exception e){
            System.out.println("Bad id");
            return -1;
        }

        return dbId;
    }

    private List<MinPostDto> convertToMinDto(List<HotPost> hotPosts){
        List<MinPostDto> list = new ArrayList<MinPostDto>();
        for(HotPost hp : hotPosts){
            list.add(new MinPostDto(hp.getPost()));
        }

        return list;
    }
}

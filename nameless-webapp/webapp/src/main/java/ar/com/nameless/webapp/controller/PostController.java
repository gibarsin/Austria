package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.model.Post;
import ar.com.nameless.webapp.form.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 1/22/17.
 */
@Path("/posts")
@Component
public class PostController {

    private final static int MAX_QUERY_LENGTH = 128;

    @Autowired
    private PostService postService;

    @Autowired
    private Validator validator;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPosts(@QueryParam("query") final String query){
        if(query.length()> MAX_QUERY_LENGTH){
            return Response.status(Response.Status.REQUEST_URI_TOO_LONG).build();
        }

        List<Post> list = postService.finder(query);
        if(list.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        GenericEntity<List<Post>> posts = new GenericEntity<List<Post>>(list) {};
        return Response.ok(posts).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPostById(@PathParam("id") final String id){
        long dbId = 0;
        try{
            dbId = Long.valueOf(id, 36);
        }catch (Exception e){
            System.out.println("Bad id");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        System.out.println("-- Id: " + id + "\tdbId: " +dbId+" --");
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        Post post = postService.findById(dbId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(post).build();

    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response newPost(final PostForm postForm){

        if(postForm == null){
            System.out.println("Postform null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Set<ConstraintViolation<PostForm>> constraintViolations = validator.validate(postForm);

        if(!constraintViolations.isEmpty()){
            System.out.println("CV: " + constraintViolations.toString());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        System.out.println("PostForm: " + postForm.toString());
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

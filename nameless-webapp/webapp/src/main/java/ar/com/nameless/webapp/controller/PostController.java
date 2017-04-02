package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.ImageService;
import ar.com.nameless.interfaces.service.InteractionService;
import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.Flag;
import ar.com.nameless.model.IndexedPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;
import ar.com.nameless.webapp.controller.dto.MinPostDto;
import ar.com.nameless.webapp.form.PostNewForm;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/posts")
@Component
public class PostController {

    private final static int MAX_QUERY_LENGTH = 128;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private InteractionService interactionService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findPosts(@QueryParam("query") final String query){
        if(query == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if(query.length()> MAX_QUERY_LENGTH) return Response.status(Response.Status.REQUEST_URI_TOO_LONG).build();

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

        long dbId = convertId(id);
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        Post post = postService.findById(dbId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new MinPostDto(post)).build();

    }

    @GET
    @Path(("/hotposts"))
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getHotPosts(@QueryParam("offset") final long offset){
        //TODO: change offset to String for id in base36

        List<? extends IndexedPost> list = postService.getHotPosts(offset);
        if(list.isEmpty()) return Response.status(Response.Status.NOT_FOUND).build();

        List<MinPostDto> minPostDtos = convertToMinDto(list);

        GenericEntity<List<MinPostDto>> genericEntity = new GenericEntity<List<MinPostDto>>(minPostDtos) {};
        return Response.ok(genericEntity).build();

    }

    @PUT
    @Path("/{id}/like")
    public Response likePost(@PathParam("id") final String id){
        long dbId = convertId(id);
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();


        if(interactionService.likePost(getLoggedUser(), dbId)){
            return Response.ok().build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @PUT
    @Path("/{id}/dislike")
    public Response dislikePost(@PathParam("id") final String id){
        long dbId = convertId(id);
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        if(interactionService.dislikePost(getLoggedUser(), dbId)){
            return Response.ok().build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}/flag/{reason}")
    public Response flagPost(@PathParam("id") final String id, @PathParam("reason") String reason){
        long dbId = convertId(id);
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        Flag.Category category;
        try{
            category = Flag.Category.valueOf(reason);
        }catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        if(interactionService.flagPost(getLoggedUser(), dbId, category)){
            return Response.ok().build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /*
        Changed FormDataParam("form") PostNewForm postNewForm to
        FormDataBodyPart due to the Content-type of the form inside the
        multipart-form-data being empty with Postman. May change it back
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    //TODO: Limit size of input
    public Response newPost(@FormDataParam("form") FormDataBodyPart jsonPart,
                            @FormDataParam("file") InputStream input,
                            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader){

        jsonPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
        PostNewForm postNewForm = jsonPart.getValueAs(PostNewForm.class);

        Post.Type type;
        try{
            String fileName = contentDispositionHeader.getFileName();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1);
            type = Post.Type.valueOf(extension.toUpperCase());
        }catch (IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        //User user = userService.getByUsername("usuario");
        //Post newPost = postService.newPost(user, postNewForm.getTitle(), type, postNewForm.getTags());
        Post newPost = postService.newPost(getLoggedUser(), postNewForm.getTitle(), type, postNewForm.getTags());


        if(imageService.saveImage(newPost, input)){
            final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(newPost.getPostId(),36)).build();
            return Response.created(uri).build();
        }

        //Delete post(?)
        return Response.status(Response.Status.BAD_REQUEST).build();

    }


    /****** Auxiliary methods ******/

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

    private List<MinPostDto> convertToMinDto(List<? extends IndexedPost> indexedPosts){
        List<MinPostDto> list = new ArrayList<MinPostDto>();
        for(IndexedPost indexedPost : indexedPosts){
            list.add(new MinPostDto(indexedPost.getPost()));
        }

        return list;
    }

    private User getLoggedUser(){
        return userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}

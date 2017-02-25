package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.IndexedPost;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;
import ar.com.nameless.webapp.controller.dto.MinPostDto;
import ar.com.nameless.webapp.form.PostNewForm;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Path("/posts")
@Component
public class PostController {

    private final static int MAX_QUERY_LENGTH = 128;
    private final static String SAVE_DIRECTORY = "/home/kali/Documents/";
    private final static String[] SUPPORTED_EXTENSIONS_IMAGE = {"jpg", "png"};
    private final static String SUPPORTED_EXTENSIONS_GIF = "gif";

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

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
        System.out.println("-- Id: " + id + "\tdbId: " +dbId+" --");
        if(dbId<=0) return Response.status(Response.Status.BAD_REQUEST).build();

        Post post = postService.findById(dbId);
        if(post == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        System.out.println("El post es: " + post);
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

        String fileName = contentDispositionHeader.getFileName();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        Post.Type type = checkType(extension);
        if(type == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Post newPost = postService.newPost(user, postNewForm.getTitle(), type, postNewForm.getTags());

        if(saveImage(directory(newPost, extension), input)){
            final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newPost.getPostId())).build();
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

    private Post.Type checkType(String extension){

        for(int i=0; i<SUPPORTED_EXTENSIONS_IMAGE.length; i++){
            if(SUPPORTED_EXTENSIONS_IMAGE[i].compareTo(extension) == 0){
                return Post.Type.IMAGE;
            }
        }
        if(SUPPORTED_EXTENSIONS_GIF.compareTo(extension) == 0){
            return Post.Type.GIF;
        }

        return null;
    }

    private boolean saveImage(String directory, InputStream inputStream){
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            File targetFile = new File(directory);
            targetFile.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(targetFile);
            outputStream.write(bytes);
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String directory(Post post, String ext){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(post.getUploadDate());

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(SAVE_DIRECTORY);
        stringBuilder.append(calendar.get(Calendar.YEAR));
        stringBuilder.append("/");
        int aux = calendar.get(Calendar.MONTH)+1;
        String month = aux <10 ? "0"+aux : aux+"";
        stringBuilder.append(month);
        stringBuilder.append("/");
        stringBuilder.append(Long.toString(post.getPostId(), 36));
        stringBuilder.append(".");
        stringBuilder.append(ext);

        return stringBuilder.toString();

    }

    /***For reference***/
/*
    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response newPost(final PostNewForm postNewForm){
        Post created = postService.newFreshPost(postNewForm.getTitle(), "IMAGE", postNewForm.getTags());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getPostId())).build();
        return Response.created(uri).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/image")
    public Response setImageForOffer(@FormDataParam("file") InputStream input,
                                     @FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
        System.out.println("cdh: " + contentDispositionHeader);
        try {
            byte[] bytes = IOUtils.toByteArray(input);
            File targetFile = new File("doc.jpg");
            OutputStream outputStream = new FileOutputStream(targetFile);
            outputStream.write(bytes);
            outputStream.close();
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }*/


    /*
        public Response newPost(PostNewForm postNewForm,
                            InputStream input,
                            FormDataContentDisposition contentDispositionHeader){

        System.out.println("************1*****************");
        User user = userService.getByUsername("usuario");
        //User user = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        System.out.println("************2*****************");
        String fileName = "giphy.gif";
        //String fileName = contentDispositionHeader.getFileName();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        Post.Type type = checkType(extension);
        if(type == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        System.out.println("************3*****************");
        Post newPost = postService.newPost(user, postNewForm.getTitle(), type, postNewForm.getTags());

        System.out.println("************4*****************");
        if(saveImage(directory(newPost, extension), input)){
            final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newPost.getPostId())).build();
            return Response.created(uri).build();
        }

        System.out.println("************5*****************");
        return Response.status(Response.Status.BAD_REQUEST).build();

    }


    @GET
    @Path("/test")
    public Response testUpload(){
        System.out.println("STARTING TEST");
        File file = new File("/root/Desktop/giphy.gif");
        try {
            InputStream inputStream = new FileInputStream(file);
            PostNewForm form = new PostNewForm();
            form.setTitle("TITULO");
            List<String> tags = new ArrayList<>();
            tags.add("uno");
            tags.add("dos");
            form.setTags(tags);

            return newPost(form, inputStream, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

     */
}

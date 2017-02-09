package ar.com.nameless.webapp.controller;

import ar.com.nameless.interfaces.service.PostService;
import ar.com.nameless.interfaces.service.UserService;
import ar.com.nameless.model.Post;
import ar.com.nameless.model.User;
import ar.com.nameless.webapp.form.PostForm;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;

/**
 * Created by root on 12/26/16.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping("/")
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("test");
        Post post = postService.newHotPost("Titulo piola2", "IMAGE");
        System.out.println("Post creado: " + post);
        return mav;
    }

    @RequestMapping(value = "/nuevoPost", method={RequestMethod.POST, })
    public ModelAndView creatingPost(@Valid @ModelAttribute("postForm") final PostForm form){
        ModelAndView mav = new ModelAndView("redirect:/");
        System.out.println("Valor de title es: " + form.getTitle());
        if(form.getFile() != null){
            System.out.println("Guardando archivo");
            try{
                FileUtils.writeByteArrayToFile(new File("/home/lelv/Desktop/archivo"), form.getFile().getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return mav;
    }

    @RequestMapping(value = "/nuevoPost", method={RequestMethod.GET, })
    public ModelAndView newPost(@Valid @ModelAttribute("postForm") final PostForm form){
        ModelAndView mav = new ModelAndView("new_post");
        return mav;
    }


}

package ar.com.nameless.service;

import ar.com.nameless.interfaces.service.ImageService;
import ar.com.nameless.model.Post;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Calendar;

/**
 * Created by root on 2/25/17.
 */
@Service
public class ImageServiceImpl implements ImageService {
    private final static String SAVE_DIRECTORY = "/home/kali/Documents/";

    private final static String[] SUPPORTED_EXTENSIONS_IMAGE = {"jpg", "png"};
    private final static String SUPPORTED_EXTENSIONS_GIF = "gif";

    public Post.Type checkType(String extension){

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

    @Override
    public boolean saveImage(Post post, String extension, InputStream inputStream) {
        String directory = directory(post, extension );
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
}

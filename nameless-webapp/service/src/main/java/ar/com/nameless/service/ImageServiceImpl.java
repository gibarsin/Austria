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

    @Override
    public boolean saveImage(Post post, InputStream inputStream) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            File targetFile = new File(SAVE_DIRECTORY+post.getDirectory());
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

}

package com.team_musible.musible.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class ImageUploadService {
    public void uploadImage(List<MultipartFile> files, HttpServletResponse response) throws Exception {
        int number = 1;

        for(MultipartFile file : files) {
            String fileType = file.getContentType();

            if (fileType.contains("image") || fileType.contains("Image")){
                File dest = new File(System.getenv("IMAGEPATH") + "/" + "image" + number++);
                file.transferTo(dest);
            }
            else response.sendError(400, "Invalid files!");
        }
    }
}

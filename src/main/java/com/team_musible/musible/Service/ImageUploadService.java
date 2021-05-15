package com.team_musible.musible.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageUploadService {
    public void uploadImage(List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            String originalfileName = file.getOriginalFilename();
            File dest = new File(System.getenv("IMAGEPATH") + originalfileName);
            file.transferTo(dest);
        }
    }
}

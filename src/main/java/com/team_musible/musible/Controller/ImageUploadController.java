package com.team_musible.musible.Controller;

import com.team_musible.musible.Service.ImageUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class ImageUploadController{
    ImageUploadService imageUploadService = new ImageUploadService();

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestPart List<MultipartFile> files) throws Exception {
        imageUploadService.uploadImage(files);
    }
}

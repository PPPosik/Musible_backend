package com.team_musible.musible.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@RestController
public class uploadController {
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> upload(@RequestPart List<MultipartFile> files) throws Exception {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalfileName = file.getOriginalFilename();
            File dest = new File(System.getenv("MUSIBLE_IMAGE") + originalfileName);
            file.transferTo(dest);
        }
        return list;
    }
}

package com.team_musible.musible.Controller;

import com.team_musible.musible.Service.ImageUploadService;
import com.team_musible.musible.Service.MidiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    ImageUploadService imageUploadService = new ImageUploadService();
    MidiService midiService = new MidiService();

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String imageUpload(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        imageUploadService.uploadImage(files, response);

        File dir = new File(System.getenv("IMAGEPATH"));
        File[] imagefiles = dir.listFiles();
        String[] tempFileNames = new String[imagefiles.length];
        String args = "";

        for (int i = 0; i < imagefiles.length; i++)
            tempFileNames[i] = imagefiles[i].getAbsolutePath();

        Arrays.sort(tempFileNames);

        for (int i = 0; i < imagefiles.length; i++)
            args += tempFileNames[i] + " ";

        return args;
    }

    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public void downloadMidi(HttpServletResponse response) throws IOException {
        midiService.responseMidi(response);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteImage() throws IOException{
        File imageFolder = new File(System.getenv("IMAGEPATH") + "/");
        File[] imageFiles = imageFolder.listFiles();

        for(File image : imageFiles)
            image.delete();
    }

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    public void requestTest(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        imageUploadService.uploadImage(files, response);
        midiService.responseMidi(response);

        File imageFolder = new File(System.getenv("IMAGEPATH") + "/");
        File[] imageFiles = imageFolder.listFiles();

        for(File image : imageFiles)
            image.delete();
    }
}
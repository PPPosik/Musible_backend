package com.team_musible.musible.Controller;

import com.team_musible.musible.Module.GetImageFiles;
import com.team_musible.musible.Service.ImageUploadService;
import com.team_musible.musible.Service.MidiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    ImageUploadService imageUploadService = new ImageUploadService();
    MidiService midiService = new MidiService();
    GetImageFiles getImageFiles = new GetImageFiles();

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "Musible Test";
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String imageUpload(@RequestPart List<MultipartFile> files) throws IOException {
        imageUploadService.uploadImage(files);
        return getImageFiles.getFileNames();
    }

    @GetMapping("/download")
    @ResponseStatus(HttpStatus.OK)
    public void downloadMidi(HttpServletResponse response) throws IOException {
        midiService.responseMidi(response);
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public String healthCheckc() throws IOException {
        return "Healthy";
    }
}
package com.team_musible.musible.Controller;

import com.team_musible.musible.Service.ImageUploadService;
import com.team_musible.musible.Service.MidiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MidiController {
    ImageUploadService imageUploadService = new ImageUploadService();
    MidiService midiService = new MidiService();

    @GetMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        imageUploadService.uploadImage(files);
        midiService.createMidi();
        midiService.responseMidi(response);
    }
}

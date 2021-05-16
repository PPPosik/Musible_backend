package com.team_musible.musible.Controller;

import com.team_musible.musible.Service.ImageUploadService;
import com.team_musible.musible.Service.MidiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
public class MidiController {
    ImageUploadService imageUploadService = new ImageUploadService();
    MidiService midiService = new MidiService();

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestPart List<MultipartFile> files) throws Exception {
        imageUploadService.uploadImage(files);
        midiService.createMidi();
    }
}

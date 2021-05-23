package com.team_musible.musible.Controller;

import com.team_musible.musible.Service.ImageUploadService;
import com.team_musible.musible.Service.MidiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/musible")
public class MidiController {
    ImageUploadService imageUploadService = new ImageUploadService();
    MidiService midiService = new MidiService();

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        imageUploadService.uploadImage(files);
        int statusCode = midiService.createMidi();
        if(statusCode == 200) {
            System.out.print("success create midi file");
            midiService.responseMidi(response);
        }
        else {
            String message = "error create midi file";
            System.out.print(message);
            response.sendError(statusCode, message);
        }
    }
}

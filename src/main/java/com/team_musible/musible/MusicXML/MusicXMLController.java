package com.team_musible.musible.MusicXML;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Common.ImageUpload;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MusicXMLController {
    ImageUpload imageUpload = new ImageUpload();
    MusicXMLService musicXMLService = new MusicXMLService();

    @GetMapping("/musicXML")
    @ResponseStatus(HttpStatus.OK)
    public void requestXML(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        // imageUpload.uploadImage(files, response);
        String xml = musicXMLService.createXML();
        Boolean success = musicXMLService.makeXML("musicXML.xml", xml);

        if(success) {
            try{
                musicXMLService.attachFileToResponse(response);
            }
            catch(Exception error) {
                error.printStackTrace();
                response.sendError(500, error.getMessage());
            }
        }
        else {
            System.out.println("Error create MusicXML");
            response.sendError(500, "Error create MusicXML");
        }
    }
}

package com.team_musible.musible.MusicXML;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Common.ImageUpload;
import com.team_musible.musible.MusicXML.DTO.MusicXMLDTO;
import com.team_musible.musible.MusicXML.DTO.XMLBodyDTO;

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
    public void requestXML(HttpServletResponse response) throws Exception {
        // imageUpload.uploadImage(files, response);

        String testData = "16/55 16/60 8/59 8/60 16/62 8/60 8/62 16/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/55 8/55 16/60 8/59 8/60 16/62 8/60 8/62 16/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/60 8/64 16/67 8/64 8/62 16/60 8/59 8/60 8/62 8/60 8/59 8/57 16/55 8/60 8/64 16/67 8/64 8/62 16/60 8/59 8/60 48/62 8/55 8/55 16/60 16/-1 16/62 16/-1 8/64 8/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/55 8/55 16/60 8/55 8/55 8/57 8/57 16/55 16/52 16/55 16/52 8/55 8/55 16/60 8/55 8/55 8/57 8/57 16/55 16/52 16/55 16/52 8/55 8/55 16/60 16/-1 16/62 16/-1 8/64 8/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 16/55";
        XMLBodyDTO xmlBody = musicXMLService.createXMLBody(testData);

        if (xmlBody.success) {
            MusicXMLDTO xmlRes = musicXMLService.makeXML("musicXML.xml", xmlBody.xmlBody);

            if (xmlRes.statusCode == 200) {
                try {
                    // musicXMLService.attachFileToResponse(response);
                } catch (Exception error) {
                    error.printStackTrace();
                    response.sendError(500, error.getMessage());
                }
            } else {
                System.out.println(xmlRes.message);
                response.sendError(xmlRes.statusCode, xmlRes.message);
            }
        } else {
            response.sendError(500, xmlBody.error);
        }
    }
}

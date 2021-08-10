package com.team_musible.musible.MusicXML;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Common.ConvertSheet;
import com.team_musible.musible.Common.ImageUpload;
import com.team_musible.musible.Common.MusicFile;
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
    final ImageUpload imageUpload = new ImageUpload();
    final MusicXMLService musicXMLService = new MusicXMLService();

    @GetMapping("/musicXML")
    @ResponseStatus(HttpStatus.OK)
    public void requestXML(@RequestPart List<MultipartFile> files, HttpServletResponse response) throws Exception {
        imageUpload.uploadImage(files, response);

        File dir = new File(System.getenv("IMAGEPATH"));
        File[] imagefiles = dir.listFiles();
        String[] tempFileNames = new String[imagefiles.length];
        String args = "";

        for (int i = 0; i < imagefiles.length; i++) {
            tempFileNames[i] = imagefiles[i].getAbsolutePath();
        }

        Arrays.sort(tempFileNames);

        for (int i = 0; i < imagefiles.length; i++) {
            args += tempFileNames[i] + " ";
        }

        final String musicXMLMetaData = ConvertSheet.execForMusicXML(args);
        final XMLBodyDTO xmlBody = musicXMLService.createXMLBody(musicXMLMetaData);

        if (xmlBody.success) {
            MusicXMLDTO xmlRes = null;
            try {
                xmlRes = musicXMLService.makeXML("musicXML.xml", xmlBody.xmlBody);
            } catch (Exception error) {
                error.printStackTrace();
                response.sendError(500, error.getMessage());
            }

            if (xmlRes != null && xmlRes.statusCode == 200) {
                try {
                    MusicFile.attachFileToResponse(response, "converted.mid");
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

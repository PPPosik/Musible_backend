package com.team_musible.musible.MusicXML;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Common.MusicXML;
import com.team_musible.musible.MusicXML.DTO.MusicXMLDTO;
import com.team_musible.musible.MusicXML.DTO.XMLBodyDTO;

public class MusicXMLService {
    public XMLBodyDTO createXMLBody(String data) {
        MusicXML musicXML = new MusicXML();
        XMLBodyDTO xmlBody = new XMLBodyDTO();

        try {
            musicXML.xmlStart();
            musicXML.makePartlist("P1", "Part 1");
            musicXML.makePartStart("P1", 1);
            musicXML.makeAttribute(1, 0, 4, 4, "G", 2);
            musicXML.makeNote("C", 4, 4, "whole");
            musicXML.makeNote("C", 4, 4, "whole");
            musicXML.makeNote("C", 4, 4, "whole");
            musicXML.makeNote("C", 4, 4, "whole");
            musicXML.makePartEnd();
            musicXML.xmlEnd();

            xmlBody.xmlBody = musicXML.getXML();
            xmlBody.success = true;
            System.out.println(xmlBody.xmlBody);
        } catch (Exception error) {
            error.printStackTrace();
            xmlBody.error = error.getMessage();
            xmlBody.success = false;
        }

        return xmlBody;
    }

    public MusicXMLDTO makeXML(String fileName, String body) {
        MusicXMLDTO res = new MusicXMLDTO();

        try {
            FileWriter file = new FileWriter(fileName);
            file.write(body);
            file.close();
            System.out.println("Successfully write");

            res.statusCode = 200;
            res.message = "Successfully write";
        } catch (Exception error) {
            error.printStackTrace();
            res.statusCode = 500;
            res.message = error.getMessage();
        }

        return res;
    }

    public void attachFileToResponse(HttpServletResponse response) throws IOException {
        String fileName = "converted.mid";
        File midiFile = new File(System.getenv("MIDIPATH") + "/" + fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        byte byteStrem[] = new byte[2048000];

        if (midiFile.isFile() && midiFile.length() > 0) {
            FileInputStream fileInputStream = new FileInputStream(midiFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

            int read = 0;
            while ((read = bufferedInputStream.read(byteStrem)) != -1) {
                bufferedOutputStream.write(byteStrem, 0, read);
            }

            bufferedOutputStream.close();
            bufferedInputStream.close();
        }
    }
}

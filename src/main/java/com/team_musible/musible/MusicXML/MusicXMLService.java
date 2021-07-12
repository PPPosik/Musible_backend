package com.team_musible.musible.MusicXML;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Module.MusicXML;

public class MusicXMLService {
    public String createXML() {
        MusicXML musicXML = new MusicXML();
        String xml = null;

        String testData = "16/55 16/60 8/59 8/60 16/62 8/60 8/62 16/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/55 8/55 16/60 8/59 8/60 16/62 8/60 8/62 16/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/60 8/64 16/67 8/64 8/62 16/60 8/59 8/60 8/62 8/60 8/59 8/57 16/55 8/60 8/64 16/67 8/64 8/62 16/60 8/59 8/60 48/62 8/55 8/55 16/60 16/-1 16/62 16/-1 8/64 8/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 8/55 8/55 16/60 8/55 8/55 8/57 8/57 16/55 16/52 16/55 16/52 8/55 8/55 16/60 8/55 8/55 8/57 8/57 16/55 16/52 16/55 16/52 8/55 8/55 16/60 16/-1 16/62 16/-1 8/64 8/64 8/65 8/64 16/57 8/62 8/62 16/60 8/60 8/60 16/59 8/57 8/59 48/60 16/55";

        try {
            musicXML.init();
            musicXML.makePartlist("P1", "Part 1");
            musicXML.makePartStart("Part 1", 1);
            musicXML.makeAttribute(5, 0, 5, 4, "G", 2);
            musicXML.makeNote("C", 4, 1, "quarter");
            musicXML.makeNote("D", 4, 1, "quarter");
            musicXML.makeNote("E", 4, 1, "quarter");
            musicXML.makeNote("F", 4, 1, "quarter");
            musicXML.makeNote("G", 4, 1, "whole");
            musicXML.makePartEnd();

            xml = musicXML.getXML();
            System.out.println(xml);
        } catch (Exception error) {
            error.printStackTrace();
        }

        return xml;
    }

    public Boolean makeFile(String fileName, String body) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(body);
            file.close();
            System.out.println("Successfully write");
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }

        return true;
    }

    public void attachFileToResponse(HttpServletResponse response) throws IOException {
        String fileName = "converted.mid";
        File midiFile = new File(System.getenv("MIDIPATH") + "/" + fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        byte byteStrem[] = new byte[2048000];

        if(midiFile.isFile() && midiFile.length() > 0){
            FileInputStream fileInputStream = new FileInputStream(midiFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

            int read = 0;
            while((read = bufferedInputStream.read(byteStrem)) != -1){
                bufferedOutputStream.write(byteStrem, 0, read);
            }

            bufferedOutputStream.close();
            bufferedInputStream.close();
        }
    }
}

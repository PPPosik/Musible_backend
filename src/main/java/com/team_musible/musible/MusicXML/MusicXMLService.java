package com.team_musible.musible.MusicXML;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.team_musible.musible.Common.MidiToXML;
import com.team_musible.musible.Common.MusicXML;
import com.team_musible.musible.MusicXML.DTO.MusicXMLDTO;
import com.team_musible.musible.MusicXML.DTO.XMLBodyDTO;

public class MusicXMLService {
    public XMLBodyDTO createXMLBody(String data) {
        final MusicXML musicXML = new MusicXML();
        final XMLBodyDTO xmlBody = new XMLBodyDTO();

        try {
            musicXML.xmlStart();
            musicXML.makePartlist("P1", "Part 1");
            musicXML.makePartStart("P1", 1);
            musicXML.makeAttribute(1, 0, 4, 4, "G", 2);

            final String[] notes = data.split(" ");
            for (final String note : notes) {
                final String[] s = note.split("/");
                System.out.println(s[0] + " " + s[1]);

                final String step = s[1].equals("-1") ? "-1" : MidiToXML.step.get(Integer.parseInt(s[1]) % 12);
                if (step == null) {
                    throw new NullPointerException(s[1] + " step is null");
                }

                final int octave = Integer.parseInt(s[1]) / 12;

                final int duration = 1;

                final String type = MidiToXML.type.get(Integer.parseInt(s[0]));
                if (type == null) {
                    throw new NullPointerException(s[0] + " type is null");
                }

                /*
                 * TODO 1. BPM 2. rest 3. flat?
                 */
                if (octave > 0) {
                    if (type.equals("dot")) {
                        musicXML.makeNote(step, octave, duration, MidiToXML.type.get(Integer.parseInt(s[0]) / 3 * 2),
                                true);
                    } else {
                        musicXML.makeNote(step, octave, duration, type, false);
                    }
                }
            }

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
        final MusicXMLDTO res = new MusicXMLDTO();

        try {
            final FileWriter file = new FileWriter(fileName);
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
        final File midiFile = new File(System.getenv("MIDIPATH") + "/" + fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        byte byteStrem[] = new byte[2048000];

        if (midiFile.isFile() && midiFile.length() > 0) {
            final FileInputStream fileInputStream = new FileInputStream(midiFile);
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

            int read = 0;
            while ((read = bufferedInputStream.read(byteStrem)) != -1) {
                bufferedOutputStream.write(byteStrem, 0, read);
            }

            bufferedOutputStream.close();
            bufferedInputStream.close();
        }
    }
}

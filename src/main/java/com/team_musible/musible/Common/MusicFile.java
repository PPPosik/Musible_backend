package com.team_musible.musible.Common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class MusicFile {
    public static void attachFileToResponse(HttpServletResponse response, String fileName) throws IOException {
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

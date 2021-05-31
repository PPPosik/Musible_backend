package com.team_musible.musible.Service;

import com.team_musible.musible.Module.ConvertSheet;
import com.team_musible.musible.Module.MidiFile;
import org.springframework.data.util.Pair;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MidiService {
    public int createMidi() throws Exception {
        MidiFile mf = new MidiFile();
        String midiName = "converted";

        File dir = new File(System.getenv("IMAGEPATH"));
        File[] imagefiles = dir.listFiles();
        String[] tempFileNames = new String[imagefiles.length];
        String args = "";

        for (int i = 0; i < imagefiles.length; i++)
            tempFileNames[i] = imagefiles[i].getAbsolutePath();

        Arrays.sort(tempFileNames);

        for (int i = 0; i < imagefiles.length; i++)
            args += tempFileNames[i] + " ";

        List<Pair<Integer, Integer>> midiMetaData = ConvertSheet.exec(args);

        int rest = 0;
        boolean lastWasRest = false;
        for (int i = 0; i < midiMetaData.size(); i++) {
            System.out.println(midiMetaData.get(i).getFirst() + " " + midiMetaData.get(i).getSecond());
            if(midiMetaData.get(i).getSecond() < 0) {
                rest += midiMetaData.get(i).getFirst();
                lastWasRest = true;
            }
            else {
                if(lastWasRest) {
                    mf.noteOn(rest, midiMetaData.get(i).getSecond(), 127);
                    mf.noteOff(midiMetaData.get(i).getFirst(), midiMetaData.get(i).getSecond());
                }
                else {
                    mf.noteOnOffNow(midiMetaData.get(i).getFirst(), midiMetaData.get(i).getSecond(), 127);
                }
                rest = 0;
                lastWasRest = false;
            }
        }

        if (midiMetaData.size() > 0) {
            mf.progChange(10);
            mf.writeToFile(System.getenv("MIDIPATH") + "/" + midiName + ".mid");
            System.out.println("MIDIPATH " + System.getenv("MIDIPATH"));

            File imageFolder = new File(System.getenv("IMAGEPATH") + "/");
            File[] imageFiles = imageFolder.listFiles();

            for(File image : imageFiles)
                image.delete();

            return 200;
        }
        else {
            System.out.println("error");

            File imageFolder = new File(System.getenv("IMAGEPATH") + "/");
            File[] imageFiles = imageFolder.listFiles();

            for(File image : imageFiles)
                image.delete();

            return 400;
        }
    }

    public void responseMidi(HttpServletResponse response) throws IOException {
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

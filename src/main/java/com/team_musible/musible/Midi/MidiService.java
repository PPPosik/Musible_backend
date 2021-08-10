package com.team_musible.musible.Midi;

import org.springframework.data.util.Pair;

import com.team_musible.musible.Common.ConvertSheet;
import com.team_musible.musible.Common.MidiFile;

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

        List<Pair<Integer, Integer>> midiMetaData = ConvertSheet.execForMidi(args);

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
}

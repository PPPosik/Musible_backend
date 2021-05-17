package com.team_musible.musible.Module;

import java.io.File;
import java.util.Arrays;

public class GetImageFiles {
    public String getFileNames(){
        File dir = new File(System.getenv("IMAGEPATH"));
        File[] imagefiles = dir.listFiles();
        String[] tempFileNames = new String[imagefiles.length];
        String fileNames = "";

        for (int i = 0; i < imagefiles.length; i++)
            tempFileNames[i] = imagefiles[i].getAbsolutePath();

        Arrays.sort(tempFileNames);

        for (int i = 0; i < imagefiles.length; i++)
            fileNames += tempFileNames[i] + " ";

        return fileNames;
    }
}

package com.team_musible.musible.Module;

import java.io.File;
import java.io.IOException;

public class DeleteImageFiles {
    public void deleteFiles() throws IOException {
        File imageFolder = new File(System.getenv("IMAGEPATH") + "/");
        File[] imageFiles = imageFolder.listFiles();

        for(File image : imageFiles)
            image.delete();
    }
}

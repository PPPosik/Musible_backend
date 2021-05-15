package com.team_musible.musible.Controller;

import com.team_musible.musible.Module.MidiFile;

import java.util.List;

import com.team_musible.musible.Module.ConvertSheet;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Test method — creates a file test1.mid when the class
 *
 * is executed
 */

@Controller
public class MidiFileController {
    @RequestMapping("/makeMidiFile")
    @ResponseBody
    public String create() throws Exception {
        MidiFile mf = new MidiFile();
        String fileName = "converted";
        // TODO update path
        String args = "/home/musible_admin/Musible/MUSIBLE_OpenCV/score/score2.jpg";
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
            mf.writeToFile(System.getenv("MIDIPATH") + "/" + fileName + ".mid");
            System.out.println("MIDIPATH " + System.getenv("MIDIPATH"));
            
            return fileName + ".mid succesfully made";
        }
        else {
            return "image error";
        }
    }
}

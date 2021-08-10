package com.team_musible.musible.Common;

public class MusicXML {
    private String xml;

    public void xmlStart() {
        final String declare = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";

        final String dtd = "<!DOCTYPE score-partwise PUBLIC\n" + "\"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n"
                + "\"http://www.musicxml.org/dtds/partwise.dtd\">";

        final String type = "<score-partwise version=\"3.1\">";

        xml = declare + dtd + type;
    }

    public void makePartlist(String partID, String partName) {
        final String part = "<part-list>" + "<score-part id=\"" + partID + "\">" + "<part-name>" + partName
                + "</part-name>" + "</score-part>" + "</part-list>";

        xml += part;
    }

    public void makePartStart(String partID, int number) {
        xml += "<part id=\"" + partID + "\">";
        xml += "<measure number=\"" + Integer.toString(number) + "\">";
    }

    public void makePartEnd() {
        xml += "</measure>";
        xml += "</part>";
    }

    public void xmlEnd() {
        xml += "</score-partwise>";
    }

    public void makeAttribute(int divisions, int key, int beats, int beat_type, String sign, int line) {
        xml += "<attributes>";
        xml += "<divisions>" + Integer.toString(divisions) + "</divisions>";
        xml += "<key><fifths>" + Integer.toString(key) + "</fifths></key>";
        xml += "<time><beats>" + Integer.toString(beats) + "</beats><beat-type>" + Integer.toString(beat_type)
                + "</beat-type></time>";
        xml += "<clef><sign>" + sign + "</sign><line>" + Integer.toString(line) + "</line></clef>";
        xml += "</attributes>";
    }

    public void makeNote(String step, int octave, int duration, String type, boolean dot) {
        xml += "<note>";
        if (step.equals("-1")) {
            xml += "<rest/>";
        } else {
            xml += "<pitch>";
            xml += "<step>" + step + "</step>";
            xml += "<octave>" + Integer.toString(octave) + "</octave>";
            xml += "</pitch>";
        }
        xml += "<duration>" + Integer.toString(duration) + "</duration>";
        xml += "<type>" + type + "</type>";
        if (dot) {
            xml += "<dot/>";
        }
        xml += "</note>";
    }

    public String getXML() {
        return this.xml;
    }
}

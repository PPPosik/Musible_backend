package com.team_musible.musible.Common;

import java.util.HashMap;
import java.util.Map;

public class MidiToXML {
    public static final Map<Integer, String> type = new HashMap<>();
    public static final Map<Integer, String> step = new HashMap<>();

    static {
        // TODO fix
        type.put(128, "128th");
        type.put(64, "64th");
        type.put(32, "32nd");
        type.put(16, "16th");
        type.put(8, "eighth");
        type.put(1, "quater");
        type.put(2, "half");
        type.put(4, "whole");

        step.put(0, "C");
        step.put(2, "D");
        step.put(4, "E");
        step.put(5, "F");
        step.put(7, "G");
        step.put(9, "A");
        step.put(11, "B");
    }
}

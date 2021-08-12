package com.team_musible.musible.Common;

import java.util.HashMap;
import java.util.Map;

public class MidiToXML {
    public static final Map<Integer, String> type = new HashMap<>();
    public static final Map<Integer, String> step = new HashMap<>();

    static {
        type.put(2, "32nd");
        type.put(4, "16th");
        type.put(6, "dot");
        type.put(8, "eighth");
        type.put(12, "dot");
        type.put(16, "quarter");
        type.put(24, "dot");
        type.put(32, "half");
        type.put(48, "dot");
        type.put(64, "whole");

        step.put(0, "C");
        step.put(2, "D");
        step.put(4, "E");
        step.put(5, "F");
        step.put(7, "G");
        step.put(9, "A");
        step.put(11, "B");
    }
}

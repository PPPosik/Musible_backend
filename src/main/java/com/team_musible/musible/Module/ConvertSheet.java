package com.team_musible.musible.Module;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.springframework.data.util.Pair;

public class ConvertSheet {
    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        public void run() {
            try {
                new BufferedReader(new InputStreamReader(inputStream, "euc-kr")).lines().forEach(consumer);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Pair<Integer, Integer>> exec(String args) throws IOException, InterruptedException {
        List<Pair<Integer, Integer>> ret = new ArrayList<Pair<Integer, Integer>>();
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        Process process;
        String ML_ENV = System.getenv("ML_ENV");
        System.out.println("ML_ENV " + ML_ENV);
        if (isWindows) {
            process = Runtime.getRuntime().exec(String.format("cmd.exe /c %s", ML_ENV));
        } else {
            process = Runtime.getRuntime().exec(String.format("sh -c %s %s", ML_ENV, args));
        }

        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), (item) -> {
            System.out.println(item);
            String[] input = item.split("/");
            int duration = Integer.parseInt(input[0]);
            int note = Integer.parseInt(input[1]);
            ret.add(Pair.of(duration, note));
        });
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        assert exitCode == 0;

        return ret;
    }
}
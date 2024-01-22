package sokoban;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ConfigReader {

    private static ConfigReader instance;
    private String filename;

    private ConfigReader(String filename) {
        this.filename = filename;
    }

    public static ConfigReader getInstance(String filename) {
        if (instance == null) {
            instance = new ConfigReader(filename);
        }
        return instance;
    }

    public int[][] readConfig() throws IOException {
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("");
                int[] intValues = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    intValues[i] = Integer.parseInt(values[i].trim());
                }
                rows.add(intValues);
            }
        }

        if (rows.size() != 16 || rows.get(0).length != 16) {
            throw new IOException("Configuration file format error: Expected a 16x16 array.");
        }

        return rows.toArray(new int[0][]);
    }}
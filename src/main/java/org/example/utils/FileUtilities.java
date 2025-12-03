package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public final class FileUtilities {
    private FileUtilities() {
    }

    public static ArrayList<String> ReadPuzzleInput(String filename) {
        var input = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileUtilities.class.getResourceAsStream("/" + filename))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }
}

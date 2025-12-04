package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FileUtilities {
    private FileUtilities() {
    }

    public static List<String> readPuzzleInput(String filename) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileUtilities.class.getResourceAsStream("/" + filename))))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

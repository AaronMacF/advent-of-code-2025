package org.example.day7;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.*;

@SuppressWarnings("unused")
public class Day7Part2 implements Day {

    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day7.txt");
        List<List<Character>> inputChars = input.stream().map(line -> line.chars().mapToObj(i -> (char) i).toList()).toList();

        int lines = input.size();
        int charsPerLine = inputChars.getFirst().size();

        int indexOfStart = inputChars.getFirst().indexOf('S');
        Map<Integer, Long> currentSplits = new HashMap<>();
        currentSplits.put(indexOfStart, (long) 1);

        for (int i = 1; i < lines; i++) {
            List<Character> currentLine = inputChars.get(i);
            currentSplits = getNextState(currentSplits, currentLine, charsPerLine);
        }
        long timelines = currentSplits.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("Number of timelines: " + timelines);
    }

    private Map<Integer, Long> getNextState(Map<Integer, Long> currentPaths, List<Character> currentLine, int charsPerLine) {
        Map<Integer, Long> nextPaths = new HashMap<>(currentPaths);
        for (int j = 0; j < charsPerLine; j++) {
            if (!currentLine.get(j).equals('^')) {
                continue;
            }
            long splitsAtIndex = currentPaths.get(j);
            nextPaths.put(j, nextPaths.get(j) - splitsAtIndex);
            if (j != 0) {
                nextPaths.put(j - 1, nextPaths.getOrDefault(j - 1, (long) 0) + splitsAtIndex);
            }
            if (j != charsPerLine - 1) {
                nextPaths.put(j + 1, nextPaths.getOrDefault(j + 1, (long) 0) + splitsAtIndex);
            }
        }
        return nextPaths;
    }
}

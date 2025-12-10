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
        long[] currentSplits = new long[charsPerLine];
        currentSplits[indexOfStart] = 1;

        for (int i = 1; i < lines; i++) {
            List<Character> currentLine = inputChars.get(i);
            currentSplits = getNextState(currentSplits, currentLine, charsPerLine);
        }
        long timelines = Arrays.stream(currentSplits).sum();
        System.out.println("Number of timelines: " + timelines);
    }

    private long[] getNextState(long[] currentPaths, List<Character> currentLine, int charsPerLine) {
        long[] nextPaths = currentPaths.clone();
        for (int j = 0; j < charsPerLine; j++) {
            if (!currentLine.get(j).equals('^')) {
                continue;
            }
            long splitsAtIndex = currentPaths[j];
            nextPaths[j] -= splitsAtIndex;
            if (j != 0) {
                nextPaths[j - 1] += splitsAtIndex;
            }
            if (j != charsPerLine - 1) {
                nextPaths[j + 1] += splitsAtIndex;
            }
        }
        return nextPaths;
    }
}

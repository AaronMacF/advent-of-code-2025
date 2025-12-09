package org.example.day7;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day7Part1 implements Day {
    private int splits = 0;

    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day7.txt");
        List<List<Character>> inputChars = input.stream().map(line -> line.chars().mapToObj(i -> (char) i).toList()).toList();
        List<Boolean> currentState = inputChars.getFirst().stream().map(c -> c.equals('S')).toList();

        int lines = input.size();
        int charsPerLine = inputChars.getFirst().size();

        for (int i = 1; i < lines; i++) {
            List<Character> currentLine = inputChars.get(i);
            currentState = getNextState(currentState, currentLine, charsPerLine);
        }
        System.out.println("Number of splits: " + splits);
    }

    private List<Boolean> getNextState(List<Boolean> currentState, List<Character> currentLine, int charsPerLine) {
        List<Boolean> nextState = new ArrayList<>(currentState);
        for (int j = 0; j < charsPerLine; j++) {
            if (currentState.get(j).equals(true) && currentLine.get(j).equals('^')) {
                // split
                splits += 1;
                nextState.set(j, false);
                if (j != 0) {
                    nextState.set(j - 1, true);
                }
                if (j != charsPerLine - 1) {
                    nextState.set(j + 1, true);
                }
            }
        }
        return nextState;
    }
}

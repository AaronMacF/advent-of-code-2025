package org.example.day2;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.List;

public class Day2Part2 implements Day {
    private String puzzleInput = "";
    private final List<List<Long>> idRanges = new ArrayList<>();
    private long answer;

    public void run() {
        getPuzzleInput();
        populateIds();
        for (var idRange : idRanges) {
            addInvalidIdsToAnswer(idRange);
        }
        System.out.println("Answer is: " + answer);

    }

    private void getPuzzleInput() {
        List<String> inputArr = FileUtilities.ReadPuzzleInput("day2.txt");
        if (inputArr.size() > 1) {
            System.out.println("Expected a single-line input");
            return;
        }
        puzzleInput = inputArr.getFirst();
    }

    private void populateIds() {
        var ranges = puzzleInput.split(",");
        for (String range : ranges) {
            System.out.println("For range: " + range);
            var idsForRange = new ArrayList<Long>();
            var limits = range.split("-");
            for (long i = Long.parseLong(limits[0]); i <= Long.parseLong(limits[1]); i++) {
                idsForRange.add(i);
            }
            System.out.println("Added numbers between " + idsForRange.getFirst() + " and " + idsForRange.getLast());
            idRanges.add(idsForRange);
        }
    }

    private void addInvalidIdsToAnswer(List<Long> idsToCheck) {
        for (long id : idsToCheck) {
            String idStr = Long.toString(id);
            int idLen = idStr.length();
            System.out.println("Testing id " + id);
            for (int i = 1; i < idLen; i++) {
                System.out.println("Checking for patterns of length " + i);
                if (idLen % i == 0) {
                    int timesToRepeat = idLen / i;
                    System.out.println(idLen + " is a multiple of " + i + " repeating " + timesToRepeat + " times");
                    var firstPattern = idStr.substring(0, i);
                    var idToCheckStr = firstPattern.repeat(timesToRepeat);
                    if (idStr.equals(idToCheckStr)) {
                        System.out.println("Found invalid id: " + id);
                        System.out.println("Invalid as it is " + firstPattern + " repeated " + timesToRepeat + " times");
                        answer += id;
                        break;
                    }
                }
            }
        }
    }
}

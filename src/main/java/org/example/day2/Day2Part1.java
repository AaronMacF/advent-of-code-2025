package org.example.day2;

import org.example.utils.FileUtilities;

import java.util.ArrayList;

public class Day2Part1 {
    private String puzzleInput = "";
    private ArrayList<ArrayList<Long>> idRanges = new ArrayList<ArrayList<Long>>();
    private long answer;

    public void run() {
        getPuzzleInput();
        populateIds();
        for (ArrayList<Long> idRange : idRanges) {
            addInvalidIdsToAnswer(idRange);
        }
        System.out.println("Answer is: " + answer);

    }

    private void getPuzzleInput() {
        ArrayList<String> inputArr = FileUtilities.ReadPuzzleInput("day2.txt");
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

    private void addInvalidIdsToAnswer(ArrayList<Long> idsToCheck) {
        for (long id : idsToCheck) {
            String idStr = Long.toString(id);
            if (idStr.length() % 2 != 0) {
                continue;
            }
            int midPointIndex = (idStr.length() / 2);
            String firstHalf = idStr.substring(0, midPointIndex);
            String secondHalf = idStr.substring(midPointIndex);
            System.out.println("Checking id " + id);
            System.out.println("First half: " + firstHalf);
            System.out.println("Second half: " + secondHalf);

            if (firstHalf.equals(secondHalf)) {
                System.out.println("Found invalid id: " + id);
                answer += id;
            }
        }
    }
}
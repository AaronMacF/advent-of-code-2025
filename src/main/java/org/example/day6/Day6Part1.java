package org.example.day6;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Day6Part1 implements Day {
    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day6.txt");
        List<String> tidiedInput = input.stream().map(s -> s.trim().replaceAll("(\\s+)", ",")).toList();
        List<List<String>> operations = tidiedInput.stream().map(s -> Arrays.stream(s.split(",")).toList()).toList();
        int numberOfProblems = operations.getFirst().size();
        int numberOfWorksheetLines = operations.size();
        List<List<String>> problemNumbers = operations.subList(0, numberOfWorksheetLines - 1);
        List<String> operators = operations.getLast();

        long grandTotal = 0;
        for (int i = 0; i < numberOfProblems; i++) {
            int finalI = i;
            List<Integer> numbers = problemNumbers.stream().map(ls -> ls.get(finalI)).map(Integer::parseInt).toList();
            if (operators.get(i).equals("*")) {
                grandTotal += numbers.stream().mapToLong(n -> n).reduce(1, (a, b) -> a * b);
            } else {
                grandTotal += numbers.stream().mapToInt(Integer::intValue).sum();
            }
        }

        System.out.println("Grand Total: " + grandTotal);
    }
}

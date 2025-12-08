package org.example.day6;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day6Part2 implements Day {
    private long grandTotal = 0;

    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day6.txt");
        int numberOfWorksheetLines = input.size();

        // Pad right lines with whitespace to all be same length
        int maxLineLength = input.stream().mapToInt(String::length).max().orElseThrow();
        List<String> paddedInput = new ArrayList<>();
        for (String s : input) {
            paddedInput.add(String.format("%-" + maxLineLength + "s", s));
        }

        String operator = "";
        var numbers = new ArrayList<Integer>();

        for (int i = maxLineLength - 1; i >= 0; i--) {
            int finalI = i;
            List<Character> section = paddedInput.stream().map(s -> s.charAt(finalI)).toList();
            if (section.stream().allMatch(s -> s.equals(' '))) {
                // Finished previous problem - add to total and reset
                this.updateGrandTotal(operator, numbers);
                operator = "";
                numbers = new ArrayList<>();
            } else {
                String lineChars = section.stream().limit(numberOfWorksheetLines - 1).filter(c -> !c.equals(' ')).map(Object::toString).reduce("", (a, b) -> a + b);
                int number = Integer.parseInt(lineChars);
                numbers.add(number);
                var operatorChar = section.getLast();
                if (!operatorChar.equals(' ')) {
                    operator = operatorChar.toString();
                }
            }
        }
        // Resolve final problem
        this.updateGrandTotal(operator, numbers);

        System.out.println("Grand Total: " + grandTotal);
    }

    private void updateGrandTotal(String operator, List<Integer> numbers) {
        if (operator.equals("*")) {
            grandTotal += numbers.stream().mapToLong(n -> n).reduce(1, (a, b) -> a * b);
        } else {
            grandTotal += numbers.stream().mapToInt(Integer::intValue).sum();
        }
    }
}

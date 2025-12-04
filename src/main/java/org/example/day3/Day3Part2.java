package org.example.day3;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day3Part2 implements Day {
    private final List<List<Integer>> banks = new ArrayList<>();

    public void run() {
        this.getPuzzleInput();
        long answer = 0;
        for (var bank : banks) {
            List<Integer> remainingBank = new ArrayList<>(bank);
            long bankJoltage = 0;
            for (int i = 11; i >= 0; i--) {
                int[] results = this.findNextDigit(remainingBank, i);
                bankJoltage += (long) (results[0] * (Math.pow(10, i)));
                remainingBank = remainingBank.subList(results[1] + 1, remainingBank.size());
            }
            System.out.println("Joltage for bank " + bank + " is " + bankJoltage);
            answer += bankJoltage;
        }
        System.out.println("Answer is: " + answer);
    }

    private void getPuzzleInput() {
        var banksStr = FileUtilities.ReadPuzzleInput("day3.txt");
        var intPattern = Pattern.compile("\\d");
        for (var bankStr : banksStr) {
            var levels = new ArrayList<Integer>();
            var matcher = intPattern.matcher(bankStr);
            while (matcher.find()) {
                levels.add(Integer.parseInt(matcher.group()));
            }
            banks.add(levels);
        }
    }

    private int[] findNextDigit(List<Integer> options, int requiredOpsLeft) {
        System.out.println("Remaining bank: " + options);
        System.out.println("Number of operations to go: " + requiredOpsLeft);

        int maxValue = -1;
        int maxIndex = -1;
        for (int i = 0; i < options.size() - requiredOpsLeft; i++) {
            int value = options.get(i);
            if (value > maxValue) {
                maxValue = value;
                maxIndex = i;
            }
        }

        if (maxValue < 0) {
            throw new RuntimeException("No max value found for remaining options");
        }

        int[] results = new int[2];
        results[0] = maxValue;
        results[1] = maxIndex;

        System.out.println("Found value: " + maxValue);
        return results;
    }
}

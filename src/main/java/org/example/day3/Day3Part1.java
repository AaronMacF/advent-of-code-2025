package org.example.day3;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Day3Part1 implements Day {
    private final List<List<Integer>> banks = new ArrayList<>();

    public void run() {
        this.getPuzzleInput();
        int answer = 0;
        for (var bank : banks) {
            answer += this.findOutputForBank(bank);
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

    private int findOutputForBank(List<Integer> bank) {
        // Top number in first n-1
        int joltageTens = -1;
        int maxIndex = -1;
        for (int i = 0; i < bank.size() - 1; i++) {
            int value = bank.get(i);
            if (value > joltageTens) {
                joltageTens = value;
                maxIndex = i;
            }
        }
        if (joltageTens < 0) {
            throw new RuntimeException("No max value found in bank");
        }

        // Next number in remaining string
        var remainingBank = bank.subList(maxIndex + 1, bank.size());
        System.out.println("Bank: " + bank);
        System.out.println("First digit: " + joltageTens);
        System.out.println("Remaining options: " + remainingBank);

        int joltageDigits = remainingBank.stream().max(Comparator.naturalOrder()).orElse(-1);
        if (joltageDigits < 0) {
            throw new RuntimeException("Max option for bank not found");
        }

        int maxJoltage = 10 * joltageTens + joltageDigits;
        System.out.println("Max joltage: " + joltageTens + joltageDigits);

        return maxJoltage;
    }
}

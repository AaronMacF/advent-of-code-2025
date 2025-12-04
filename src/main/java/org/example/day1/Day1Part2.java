package org.example.day1;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day1Part2 implements Day {
    private final Pattern commandRegexPattern = Pattern.compile("([LR])(\\d*)");

    private int currentPos = 50;
    private int password = 0;

    public void run() {
        List<String> puzzleInput = FileUtilities.ReadPuzzleInput("day1.txt");
        for (String command : puzzleInput) {
            var matcher = commandRegexPattern.matcher(command);
            if (!matcher.matches()) {
                System.out.println("Getting directions failed - match not found. Failing command: " + command);
                return;
            }
            String direction = matcher.group(1);
            String rotationsStr = matcher.group(2);
            int rotations = Integer.parseInt(rotationsStr);

            rotate(direction, rotations);
        }
        System.out.println("Password is: " + password);
    }

    private void rotate(String direction, int rotations) {
        if (!direction.equals("L") && !direction.equals("R")) {
            throw new RuntimeException("direction for rotation invalid. Value given: " + direction);
        }

        int nextPos = currentPos;
        if (direction.equals("R")) {
            nextPos += rotations;
        } else {
            nextPos -= rotations;
        }

        if (currentPos == 0 && direction.equals("L")) {
            // Turning left from 0 does NOT count as a click
            password -= 1;
        }

        if (nextPos >= 100) {
            System.out.println("Current before: " + currentPos + ", command: " + direction + rotations);
            int diff = Math.floorDiv(nextPos, 100);
            password += diff;
            nextPos = nextPos % 100;
            System.out.println("Added " + diff + " to the password");
            System.out.println("Position after: " + nextPos);
        } else if (nextPos <= 0) {
            System.out.println("Current before: " + currentPos + ", command: " + direction + rotations);
            int diff = Math.ceilDiv(Math.abs(nextPos - 1), 100);
            password += diff;
            nextPos = (100 + (nextPos % 100)) % 100;
            System.out.println("Added " + diff + " to the password");
            System.out.println("Position after: " + nextPos);
        }
        currentPos = nextPos;
    }
}

package org.example;

import org.example.day1.Day1Part1;
import org.example.day1.Day1Part2;
import org.example.day2.Day2Part1;
import org.example.day2.Day2Part2;

public class Main {
    public static void main(String[] args) {
        var day = 2;
        var part = 2;

        switch (day) {
            case 1:
                if (part == 1) {
                    new Day1Part1().run();
                } else {
                    new Day1Part2().run();
                }
                break;
            case 2:
                if (part == 1) {
                    new Day2Part1().run();
                } else {
                    new Day2Part2().run();
                }
                break;
            default:
                System.out.println("Day " + day + " not found");
        }
    }
}
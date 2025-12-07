package org.example.day5;

import org.example.Day;
import org.example.shared.Pair;
import org.example.utils.FileUtilities;

import java.util.*;

@SuppressWarnings("unused")
public class Day5Part1 implements Day {
    private final List<Pair<Long, Long>> freshIdRanges = new ArrayList<>();

    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day5.txt");
        var emptyLineIdx = input.indexOf("");
        List<String> freshIdRangesStr = input.subList(0, emptyLineIdx);
        List<String> availableIdsStr = input.subList(emptyLineIdx + 1, input.size());
        freshIdRangesStr.forEach(rangeStr -> {
            var limits = rangeStr.split("-");
            freshIdRanges.add(new Pair<>(Long.parseLong(limits[0]), Long.parseLong(limits[1])));
        });
        var freshIngredients = availableIdsStr
            .stream()
            .mapToLong(Long::parseLong)
            .filter(id -> freshIdRanges.stream().anyMatch(range -> id >= range.first() && id <= range.second()))
            .count();
        System.out.println("Number of fresh ingredients: " + freshIngredients);
    }
}

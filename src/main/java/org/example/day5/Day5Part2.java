package org.example.day5;

import org.example.Day;
import org.example.shared.Pair;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class Day5Part2 implements Day {
    private final List<Pair<Long, Long>> freshIdRanges = new ArrayList<>();

    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day5.txt");
        var emptyLineIdx = input.indexOf("");
        List<String> freshIdRangesStr = input.subList(0, emptyLineIdx);
        freshIdRangesStr.forEach(rangeStr -> {
            var limits = rangeStr.split("-");
            freshIdRanges.add(new Pair<>(Long.parseLong(limits[0]), Long.parseLong(limits[1])));
        });
        // Order id ranges by size
        freshIdRanges.sort(Comparator.comparingLong(a -> a.first() - a.second()));
        var uniqueRanges = new ArrayList<Pair<Long, Long>>();
        for (var range : freshIdRanges) {
            // Case 1: Range entirely encapsulated - ignore new range
            if (uniqueRanges.stream().anyMatch(existing -> this.rangeEntirelyEncapsulated(existing, range))) {
                continue;
            }

            // Case 2: Joins two existing ranges
            var rangeToExtendEarlier = uniqueRanges.stream().filter(existing -> this.extendRangeEarlier(existing, range)).findFirst();
            var rangeToExtendLater = uniqueRanges.stream().filter(existing -> this.extendRangeLater(existing, range)).findFirst();
            if (rangeToExtendEarlier.isPresent() && rangeToExtendLater.isPresent()) {
                uniqueRanges.remove(rangeToExtendEarlier.get());
                uniqueRanges.remove(rangeToExtendLater.get());
                uniqueRanges.add(new Pair<>(rangeToExtendLater.get().first(), rangeToExtendEarlier.get().second()));
                continue;
            }

            // Case 3: Just extend existing range earlier
            if (rangeToExtendEarlier.isPresent()) {
                uniqueRanges.remove(rangeToExtendEarlier.get());
                uniqueRanges.add(new Pair<>(range.first(), rangeToExtendEarlier.get().second()));
                continue;
            }

            // Case 4: Just extend existing range later
            if (rangeToExtendLater.isPresent()) {
                uniqueRanges.remove(rangeToExtendLater.get());
                uniqueRanges.add(new Pair<>(rangeToExtendLater.get().first(), range.second()));
                continue;
            }

            // Case 5: New unique range
            uniqueRanges.add(range);
        }

        var totalFreshIds = uniqueRanges.stream().mapToLong(r -> r.second() - r.first() + 1).sum();
        System.out.println("Total fresh ids: " + totalFreshIds);
    }

    private boolean rangeEntirelyEncapsulated(Pair<Long, Long> existingRange, Pair<Long, Long> newRange) {
        return newRange.first() >= existingRange.first() && newRange.second() <= existingRange.second();
    }

    private boolean extendRangeEarlier(Pair<Long, Long> existingRange, Pair<Long, Long> newRange) {
        // New range starts earlier than existing range, so decrease start of range
        return newRange.first() < existingRange.first() && this.idInRange(newRange.second(), existingRange);
    }

    private boolean extendRangeLater(Pair<Long, Long> existingRange, Pair<Long, Long> newRange) {
        // New range ends later than existing range, so increase end of range
        return newRange.second() > existingRange.second() && this.idInRange(newRange.first(), existingRange);
    }

    private boolean idInRange(long id, Pair<Long, Long> range) {
        return id >= range.first() && id <= range.second();
    }
}

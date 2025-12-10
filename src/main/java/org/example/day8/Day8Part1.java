package org.example.day8;

import com.google.common.collect.Sets;
import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day8Part1 implements Day {
    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day8.txt");
        Set<Box> boxes = input
            .stream()
            .map(s -> s.split(","))
            .map(coords -> new Box(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])))
            .collect(Collectors.toSet());

        Set<Set<Box>> chains = boxes
            .stream()
            .map(b -> new HashSet<>(Set.of(b)))
            .collect(Collectors.toSet());

        Set<Set<Box>> combos = Sets.combinations(boxes, 2);
        var closestBoxes = combos.stream().sorted(Comparator.comparingDouble(this::straightLineDistance)).toList();
        var closestBoxesIter = closestBoxes.iterator();

        for (int i = 0; i < 1000; i++) {
            var nextPair = closestBoxesIter.next();
            Box[] boxesArr = nextPair.toArray(new Box[2]);
            var setA = chains.stream().filter(s -> s.contains(boxesArr[0])).findFirst().orElseThrow();
            var setB = chains.stream().filter(s -> s.contains(boxesArr[1])).findFirst().orElseThrow();
            if (setA.equals(setB)) {
                continue;
            }
            chains.remove(setA);
            chains.remove(setB);
            var union = new HashSet<>(setA);
            union.addAll(setB);
            chains.add(union);
        }

        long top3Prod = chains
            .stream()
            .map(Set::size)
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToLong(Integer::intValue)
            .reduce(1, (num, total) -> num * total);
        System.out.println("Product of top 3 chains: " + top3Prod);
    }

    private double straightLineDistance(Set<Box> boxes) {
        Box[] boxesArr = boxes.toArray(new Box[2]);
        var box1 = boxesArr[0];
        var box2 = boxesArr[1];
        return Math.sqrt(Math.pow(box1.x - box2.x, 2) + Math.pow(box1.y - box2.y, 2) + Math.pow(box1.z - box2.z, 2));
    }
}

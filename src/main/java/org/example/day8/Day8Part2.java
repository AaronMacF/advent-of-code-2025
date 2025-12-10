package org.example.day8;

import com.google.common.collect.Sets;
import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day8Part2 implements Day {
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

        Box lastBox1 = null;
        Box lastBox2 = null;
        while (chains.size() > 1)
        {
            var nextPair = closestBoxesIter.next();
            Box[] boxesArr = nextPair.toArray(new Box[2]);
            var box1 = boxesArr[0];
            var box2 = boxesArr[1];
            var setA = chains.stream().filter(s -> s.contains(box1)).findFirst().orElseThrow();
            var setB = chains.stream().filter(s -> s.contains(box2)).findFirst().orElseThrow();
            if (setA.equals(setB)) {
                continue;
            }
            chains.remove(setA);
            chains.remove(setB);
            var union = new HashSet<>(setA);
            union.addAll(setB);
            chains.add(union);
            lastBox1 = box1;
            lastBox2 = box2;
        }
        if (lastBox1 == null || lastBox2 == null) {
            throw new RuntimeException();
        }

        long xCoords = (long) lastBox1.x * (long) lastBox2.x;
        System.out.println("Result: " + xCoords);

    }

    private double straightLineDistance(Set<Box> boxes) {
        Box[] boxesArr = boxes.toArray(new Box[2]);
        var box1 = boxesArr[0];
        var box2 = boxesArr[1];
        return Math.sqrt(Math.pow(box1.x - box2.x, 2) + Math.pow(box1.y - box2.y, 2) + Math.pow(box1.z - box2.z, 2));
    }
}

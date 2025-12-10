package org.example.day8;

import org.example.Day;
import org.example.shared.Pair;
import org.example.utils.FileUtilities;
import org.jgrapht.alg.util.UnionFind;

import java.util.*;

@SuppressWarnings("unused")
public class Day8Part2 implements Day {
    public void run() {
        List<String> input = FileUtilities.readPuzzleInput("day8.txt");
        List<Box> boxes = input
            .stream()
            .map(s -> s.split(","))
            .map(coords -> new Box(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])))
            .toList();

        UnionFind<Box> chains = new UnionFind<>(new HashSet<>(boxes));

        List<Pair<Box, Box>> boxPairs = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i ++) {
            for (int j = i + 1; j < boxes.size(); j++) {
              boxPairs.add(new Pair<>(boxes.get(i), boxes.get(j)));
            }
        }
        boxPairs.sort(Comparator.comparingDouble(this::straightLineDistance));
        var boxPairsIter = boxPairs.iterator();

        Box lastBox1 = null;
        Box lastBox2 = null;
        while (chains.numberOfSets() > 1)
        {
            var nextPair = boxPairsIter.next();
            var box1 = nextPair.first();
            var box2 = nextPair.second();
            chains.union(box1, box2);
            lastBox1 = box1;
            lastBox2 = box2;
        }
        if (lastBox1 == null || lastBox2 == null) {
            throw new RuntimeException();
        }

        long xCoords = (long) lastBox1.x * (long) lastBox2.x;
        System.out.println("Result: " + xCoords);

    }

    private double straightLineDistance(Pair<Box, Box> pair) {
        var box1 = pair.first();
        var box2 = pair.second();
        return Math.sqrt(Math.pow(box1.x - box2.x, 2) + Math.pow(box1.y - box2.y, 2) + Math.pow(box1.z - box2.z, 2));
    }
}

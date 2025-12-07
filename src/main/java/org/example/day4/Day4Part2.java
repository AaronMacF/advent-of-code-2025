package org.example.day4;

import org.example.Day;
import org.example.utils.FileUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Day4Part2 implements Day {
    private static class Cell {
        int xIndex;
        int yIndex;
        boolean isRoll;

        public Cell(boolean isRoll, int xIndex, int yIndex) {
            this.isRoll = isRoll;
            this.xIndex = xIndex;
            this.yIndex = yIndex;
        }
    }

    private final List<List<Cell>> grid = new ArrayList<>();
    private int maxXIndex = -1;
    private int maxYIndex= -1;

    public void run() {
        this.populateGrid();
        long totalRemovals = 0;
        long removalsInPass;
        do {
            removalsInPass = grid.stream().mapToLong(cells -> cells.stream().filter(this::cellRemoved).count()).sum();
            totalRemovals += removalsInPass;
        } while (removalsInPass > 0);

        System.out.println("Total removals: " + totalRemovals);
    }

    private void populateGrid() {
        List<String> input = FileUtilities.readPuzzleInput("day4-test.txt");
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            List<Cell> cells = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                cells.add(new Cell(c == '@', j, i));
                System.out.println("Cell (" + j + "," + i + ") : " + c);
            }
            grid.add(cells);
        }
        maxXIndex = grid.getFirst().size() - 1;
        maxYIndex = grid.size() - 1;
    }

    private boolean cellRemoved(Cell cell) {
        System.out.println("Checking cell (" + cell.xIndex + "," + cell.yIndex + ")");

        boolean canBeRemoved = cell.isRoll && this.numberOfNeighbourRolls(cell) < 4;
        if (canBeRemoved) {
            cell.isRoll = false;
        }
        return canBeRemoved;
    }

    private long numberOfNeighbourRolls(Cell cell) {
        String[] directions = {"N", "E", "S", "W", "NE", "SE", "SW", "NW"};
        long neighbourRolls = Arrays.stream(directions).filter(d -> this.neighbourIsRoll(cell, d)).count();
        System.out.println(neighbourRolls + " neighbours are rolls");
        return neighbourRolls;
    }

    private boolean neighbourIsRoll(Cell cell, String direction) {
        int xIndex;
        if (direction.contains("E")) {
            xIndex = cell.xIndex + 1;
        } else if (direction.contains(("W"))) {
            xIndex = cell.xIndex - 1;
        } else {
            xIndex = cell.xIndex;
        }

        int yIndex;
        if (direction.contains(("N"))) {
            yIndex = cell.yIndex - 1;
        } else if (direction.contains(("S"))) {
            yIndex = cell.yIndex + 1;
        } else {
            yIndex = cell.yIndex;
        }

        System.out.println("Checking neighbour (" + xIndex + "," + yIndex + ")");
        if (xIndex < 0 || xIndex > maxXIndex) {
            System.out.println("Direction " + direction + " is out of the grid");
            return false;
        }
        if (yIndex < 0 || yIndex > maxYIndex) {
            System.out.println("Direction " + direction + " is out of the grid");
            return false;
        }

        var isRoll = grid.get(yIndex).get(xIndex).isRoll;
        System.out.println("Direction " + direction + " " + isRoll);
        return isRoll;
    }
}

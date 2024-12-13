
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class main {

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        System.out.println("AoC Day 12 Part 1");

        boolean full = true;
        Scanner scanner = null;

        ArrayList<String> lines = new ArrayList<>();
 
        try {
            if (full) {
                scanner = new Scanner(new File("input_full.txt"));
            } else {
                scanner = new Scanner(new File("input_test.txt"));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            lines.add(line);
        }
        
        int i = 0;
        char [][] gardenMap = new char[lines.size()][lines.get(0).length()];
        
        for (String l : lines) {
            gardenMap[i] = l.toCharArray();
            i++;
        }

        System.out.println("Number of lines: " + lines.size());
        System.out.println("Total price: " + calculateTotalPrice(gardenMap));
    }

    public static int calculateTotalPrice(char[][] gardenMap) {
        int rows = gardenMap.length;
        int cols = gardenMap[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int totalPrice = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    // Calculate area and perimeter for the region
                    int[] result = calculateAreaAndPerimeter(gardenMap, visited, i, j);
                    int area = result[0];
                    int perimeter = result[1];
                    totalPrice += area * perimeter;
                }
            }
        }

        return totalPrice;
    }

    private static int[] calculateAreaAndPerimeter(char[][] gardenMap, boolean[][] visited, int startRow, int startCol) {
        int rows = gardenMap.length;
        int cols = gardenMap[0].length;
        char plantType = gardenMap[startRow][startCol];
        int area = 0;
        int perimeter = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            area++;

            for (int[] direction : DIRECTIONS) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || gardenMap[newRow][newCol] != plantType) {
                    // Increment perimeter if the neighboring cell is out of bounds or not the same plant type
                    perimeter++;
                } else if (!visited[newRow][newCol]) {
                    // Add the neighboring cell to the queue if not visited
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        return new int[]{area, perimeter};
    }
}
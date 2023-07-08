package org.example;

import java.util.*;

import static java.util.Arrays.asList;

public class ExplorationMap {
    private int n;
    SharedMemory memory = new SharedMemory(n);

    public ExplorationMap(int n, SharedMemory memory) {
        this.n = n;
        this.memory = memory;
        this.matrix = new List[n][n];
        init();
    }

    private List<Token>[][] matrix;

    public void init() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new ArrayList<Token>();
            }
    }

    public int getN() {
        return n;
    }

    //Cell should be a wrapper or alias for List<Token>
    public boolean visit(
            int row, int col, Robot robot) {
        if (row >= n || row < 0 || col < 0 || col >= n)
            return false;
        synchronized (matrix[row][col]) {
            if (matrix[row][col].isEmpty()) {
                matrix[row][col] = memory.extractTokens(n);
                return true;
            } else {
                return false;
            }
        }
    }
//    @Override
//    public String toString() {
//    }
}

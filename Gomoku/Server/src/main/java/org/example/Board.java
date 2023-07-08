package org.example;

import java.util.Arrays;

public class Board {
    //    ArrayList<ArrayList<Integer>> init = new ArrayList<ArrayList<Integer>>();
    private int[][] matrix = new int[19][19];

    public void initMatrix() {
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 19; j++)
                matrix[i][j] = 0;
    }

    public Board() {
        initMatrix();
    }

    public void setCell(int i, int j, int val) {
        matrix[i][j] = val;
    }

    public void displayMatrix() {
        System.out.println(" ");
        for (int row[] : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(" ");
    }

    public String StringMatrix() {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++)
                str.append(" " + matrix[i][j]);
            str.append("\n");
        }
        str.append("\n");
        return str.toString();
    }

    public boolean checkVictory() {
        if (vertical() == true || diagonals() == true || horizontal() == true)
            return true;
        else
            return false;
    }

    public boolean vertical() {
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 14; j++) {
                int count = 1;
                for (int k = 1; k < 5; k++) {
                    if (matrix[i][k] == matrix[i][j] && matrix[i][j] != 0)
                        count++;
                }
                if (count == 5)
                    return true;
            }
        return false;
    }

    public boolean horizontal() {
        for (int i = 0; i < 14; i++)
            for (int j = 0; j < 19; j++) {
                int count = 1;
                for (int k = 1; k < 5; k++) {
                    if (matrix[i][j] == matrix[k][j] && matrix[i][j] != 0)
                        count++;
                }
                if (count == 5)
                    return true;
            }
        return false;
    }

    public boolean diagonals() {
        for (int i = 0; i < 14; i++)
            for (int j = 0; j < 14; j++) {
                if (matrix[i][j] == matrix[i + 1][j + 1] &&
                        matrix[i + 1][j + 1] == matrix[i + 2][j + 2] &&
                        matrix[i + 2][j + 2] == matrix[i + 3][j + 3] &&
                        matrix[i + 3][j + 3] == matrix[i + 4][j + 4] &&
                        matrix[i][j] != 0)
                    return true;
            }
        for (int i = matrix.length - 1; i >= 4; i--)
            for (int j = 0; j < matrix.length - 4; j++) {
                if (matrix[i][j] == matrix[i - 1][j + 1] &&
                        matrix[i - 1][j + 1] == matrix[i - 2][j + 2] &&
                        matrix[i - 2][j + 2] == matrix[i - 3][j + 3] &&
                        matrix[i - 3][j + 3] == matrix[i - 4][j + 4] &&
                        matrix[i][j] != 0)
                    return true;
            }
        return false;
    }
}

package org.example;

public class Board {
    private char[][] grid;

    public Board() {
        this.grid = new char[10][10];

        //init with water
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = '~';
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean placeShip(int x, int y, int length, boolean horizontal) {
        if (horizontal) {
            if(y + length > 10) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if(grid[x][y + i] == 'S' || grid[x][y + i] == 'X') {
                    return false;//ship placed
                }
            }
            for (int i = 0; i < length; i++) {
                grid[x][y + i] = 'S';
            }
        } else {
            if(x + length > 10) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if(grid[x + i][y] == 'S' || grid[x + i][y] == 'X') {
                    return false;//ship placed
                }
            }

            for (int i = 0; i < length; i++) {
                grid[x + i][y] = 'S';
            }
        }
        return true;
    }

    public boolean submitMove(int x, int y) {
        if (grid[x][y] == 'S') {
            grid[x][y] = 'X'; //hit
            return true;
        } else {
            //grid[x][y] = 'O'; //miss
            return false;
        }
    }
}


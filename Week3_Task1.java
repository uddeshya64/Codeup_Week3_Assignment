package practice.java;

import java.util.Random;

import java.util.Scanner;

public class Week3_Task1 {

    private static final int SIZE = 4;  // Size of the board
    private int[][] board;  // 2D array representing the game board
    private Random random;

    public Week3_Task1() {
        board = new int[SIZE][SIZE];  // Initialize the board
        random = new Random();  // Initialize Random object to generate numbers
        startNewGame(); 
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();  // Display the initial state of the board

            if (isGameOver()) {  // Check if no moves are left
                System.out.println("Game Over!");
                break;  // Exit the loop if the game is over
            }

            System.out.print("Enter move (W = Up, A = Left, S = Down, D = Right, N = New Game): ");
            char move = scanner.next().charAt(0);

            switch (move) {
                case 'W':
                case 'w':
                    moveUp();
                    break;
                case 'S':
                case 's':
                    moveDown();
                    break;
                case 'A':
                case 'a':
                    moveLeft();
                    break;
                case 'D':
                case 'd':
                    moveRight();
                    break;
                case 'N':
                case 'n':
                    startNewGame();  // Start a new game when 'N' or 'n' is pressed
                    continue;  // Skip the rest and start the next loop
                default:
                    System.out.println("Invalid move! Try again.");
                    continue;
            }

            if (!isBoardFull()) {
                addNewTile();  // Add a new tile after a valid move
            }
        }
        scanner.close();  // Close the scanner when the game ends
    }

    // Start a new game by resetting the board and adding two new tiles
    private void startNewGame() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
        // Add two new tiles at the beginning of the game
        addNewTile();
        addNewTile();
    }

    private void addNewTile() {
        int row, col;
        do {
            row = random.nextInt(SIZE);
            col = random.nextInt(SIZE);
        } while (board[row][col] != 0);

        board[row][col] = random.nextInt(10) < 9 ? 2 : 4;  // 90% chance of 2, 10% chance of 4
    }

    private void moveLeft() {
        for (int i = 0; i < SIZE; i++) {
            mergeRow(board[i]);
        }
    }

    private void moveRight() {
        for (int i = 0; i < SIZE; i++) {
            reverse(board[i]);
            mergeRow(board[i]);
            reverse(board[i]);
        }
    }

    private void moveUp() {
        for (int i = 0; i < SIZE; i++) {
            int[] column = getColumn(i);
            mergeRow(column);
            setColumn(i, column);
        }
    }

    private void moveDown() {
        for (int i = 0; i < SIZE; i++) {
            int[] column = getColumn(i);
            reverse(column);
            mergeRow(column);
            reverse(column);
            setColumn(i, column);
        }
    }

    private void mergeRow(int[] row) {
        int[] newRow = new int[SIZE];
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            if (row[i] != 0) {
                if (index > 0 && newRow[index - 1] == row[i]) {
                    newRow[index - 1] *= 2;
                } else {
                    newRow[index++] = row[i];
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            row[i] = newRow[i];
        }
    }

    private void reverse(int[] row) {
        for (int i = 0; i < SIZE / 2; i++) {
            int temp = row[i];
            row[i] = row[SIZE - 1 - i];
            row[SIZE - 1 - i] = temp;
        }
    }

    private int[] getColumn(int index) {
        int[] column = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            column[i] = board[i][index];
        }
        return column;
    }

    private void setColumn(int index, int[] column) {
        for (int i = 0; i < SIZE; i++) {
            board[i][index] = column[i];
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameOver() {
        if (!isBoardFull()) {
            return false;
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i > 0 && board[i][j] == board[i - 1][j]) ||
                    (j > 0 && board[i][j] == board[i][j - 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print(".\t");  // Empty cell
                } else {
                    System.out.print(board[i][j] + "\t");  // Non-empty cell
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Week3_Task1 game = new Week3_Task1();  // Start a new game
        game.play();  
    }
}

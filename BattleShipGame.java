// BattleShipGame.java
// A 2-player simplified Battleship game implementation
// Author: Anup Sharma - Date: 05/27/2025
// This Java program handles player setup, ship placement, turn-based attacks,
// and game conclusion, while showing only hits and misses (not ship locations)

import java.util.Scanner;

public class BattleShipGame {

    // Constants for board size and symbols
    static final int BOARD_SIZE = 5;
    static final String EMPTY = "-";
    static final String SHIP = "@";
    static final String HIT = "X";
    static final String MISS = "O";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x, y;

        // Game boards: each player has a ship board and a tracking board
        String[][] player1Board = new String[BOARD_SIZE][BOARD_SIZE];
        String[][] player2Board = new String[BOARD_SIZE][BOARD_SIZE];
        String[][] tempBoard = new String[BOARD_SIZE][BOARD_SIZE]; // Used during ship placement
        String[][] player1TrackingBoard = new String[BOARD_SIZE][BOARD_SIZE]; // What player 1 sees of player 2
        String[][] player2TrackingBoard = new String[BOARD_SIZE][BOARD_SIZE]; // What player 2 sees of player 1

        // Initialize all boards with default value
        resetBoard(player1Board);
        resetBoard(player2Board);
        resetBoard(tempBoard);
        resetBoard(player1TrackingBoard);
        resetBoard(player2TrackingBoard);

        System.out.println("Welcome to Battleship!\n");

        // ========== SHIP PLACEMENT PHASE ==========
        for (int i = 1; i <= 2; i++) {
            System.out.println("PLAYER " + i + ", ENTER YOUR SHIPS' COORDINATES.");
            int shipsPlaced = 0;

            // Loop until all 5 ships are placed
            while (shipsPlaced < 5) {
                System.out.println("Enter ship " + (shipsPlaced + 1) + " location:");
                x = getValidatedInt(input);
                y = getValidatedInt(input);

                // Validate coordinates within board limits
                if (!isValidCoord(x, y)) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                    continue;
                }

                // Prevent overlapping ships
                if (SHIP.equals(tempBoard[x][y])) {
                    System.out.println("You already selected that space. Choose different coordinates.");
                    continue;
                }

                // Place ship
                tempBoard[x][y] = SHIP;
                shipsPlaced++;
            }

            // Copy temp board to appropriate player's board
            if (i == 1) {
                copyBoard(tempBoard, player1Board);
                printBoard(player1Board);  // Only visible to player placing ships
            } else {
                copyBoard(tempBoard, player2Board);
                printBoard(player2Board);  // Only visible to player placing ships
            }

            // Clear temp board for next player
            resetBoard(tempBoard);
        }

        // ========== BATTLE PHASE ==========
        int player1Ships = 5;
        int player2Ships = 5;

        // Continue until one player's ships are all sunk
        while (player1Ships > 0 && player2Ships > 0) {
            for (int currentPlayer = 1; currentPlayer <= 2; currentPlayer++) {
                int opponentPlayer = (currentPlayer == 1) ? 2 : 1;
                String[][] opponentBoard = (currentPlayer == 1) ? player2Board : player1Board;
                String[][] trackingBoard = (currentPlayer == 1) ? player2TrackingBoard : player1TrackingBoard;

                System.out.println("Player " + currentPlayer + ", enter hit row/column:");
                x = getValidatedInt(input);
                y = getValidatedInt(input);

                // Validate input again
                if (!isValidCoord(x, y)) {
                    System.out.println("Invalid coordinates. Choose different coordinates.");
                    currentPlayer--; // Retry same player's turn
                    continue;
                }

                // Prevent firing on the same cell twice
                if (HIT.equals(trackingBoard[x][y]) || MISS.equals(trackingBoard[x][y])) {
                    System.out.println("You already fired on this spot. Choose different coordinates.");
                    currentPlayer--; // Retry same player's turn
                    continue;
                }

                // Execute attack
                boolean hit = attackAndPrint(opponentBoard, trackingBoard, currentPlayer, opponentPlayer, x, y);
                if (hit) {
                    if (opponentPlayer == 1) player1Ships--;
                    else player2Ships--;
                }

                // End game early if ships are gone mid-turn
                if (player1Ships == 0 || player2Ships == 0) break;
            }
        }

        // ========== GAME OVER ==========
        if (player2Ships == 0)
            System.out.println("PLAYER 1 WINS!");
        else
            System.out.println("PLAYER 2 WINS!");

        // Print full boards at the end of the game
        System.out.println("\nFinal boards:\n");
        System.out.println("Player 1:\n");
        printBoard(player1Board);
        System.out.println("\nPlayer 2:\n");
        printBoard(player2Board);

        input.close(); // Free up system resources
    }

    /**
     * Handles the logic when a player attacks a cell.
     * Updates both the opponent's actual board and the attacker's tracking board.
     * Returns true if a ship was hit, false if it was a miss.
     */
    public static boolean attackAndPrint(String[][] opponentBoard, String[][] trackingBoard, int currentPlayer, int opponentPlayer, int x, int y) {
        if (SHIP.equals(opponentBoard[x][y])) {
            System.out.println("PLAYER " + currentPlayer + " HIT PLAYER " + opponentPlayer + "'s SHIP!");
            opponentBoard[x][y] = HIT;
            trackingBoard[x][y] = HIT;
            printBoard(trackingBoard);
            return true;
        } else {
            System.out.println("PLAYER " + currentPlayer + " MISSED!");
            opponentBoard[x][y] = MISS;
            trackingBoard[x][y] = MISS;
            printBoard(trackingBoard);
            return false;
        }
    }

    /**
     * Resets the board by filling it with the EMPTY symbol.
     */
    public static void resetBoard(String[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Copies the contents of one board into another.
     * Used to assign ships after placement from tempBoard.
     */
    public static void copyBoard(String[][] source, String[][] target) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                target[i][j] = source[i][j];
            }
        }
    }

    /**
     * Prints the board in a grid format with row and column indices.
     */
    public static void printBoard(String[][] board) {
        System.out.println("  0 1 2 3 4");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Returns true if the coordinates are within the board boundaries.
     */
    public static boolean isValidCoord(int x, int y) {
        return x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE;
    }

    /**
     * Gets a valid integer from the user.
     * If input is invalid (non-integer), it prompts again.
     */
    public static int getValidatedInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number between 0 and 4:");
            input.next(); // discard invalid input
        }
        return input.nextInt();
    }
}

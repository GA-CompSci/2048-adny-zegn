import java.util.ArrayList;
import java.util.Random;

/**
 * Game class for 2048
 * Contains all game logic and state management
 * 
 * STUDENT VERSION: Complete the TODO sections to make the game work!
 */
public class Game {
    private static final int BOARD_SIZE = 4;
    private static final int WIN_VALUE = 2048;
    
    private int[][] board;
    private int score;
    private Random random;
    private boolean hasWon;
    private boolean gameOver;
    
    /**
     * Constructor - initializes a new game
     */
    public Game() {
        random = new Random();
        resetGame();
    }
    
    /**
     * Resets the game to initial state
     */
    public void resetGame() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        score = 0;
        hasWon = false;
        gameOver = false;
        
        // Add two initial tiles
        addRandomTile();
        addRandomTile();
    }
    
    /**
     * TODO #1: Implement the method to add a random tile to the board
     * Requirements:
     * - 90% chance of adding a 2
     * - 10% chance of adding a 4
     * - Should only add to empty cells
     * - Use the getEmptyCells() method to find empty positions
     * 
     * Hint: Use random.nextInt(10) < 9 for 90% probability
     */
    private void addRandomTile() {
        // TODO: Complete this method
        
    }
    
    /**
     * TODO #2: Implement the method to get all empty cells on the board
     * Requirements:
     * - Return an ArrayList of int arrays [row, col] for each empty cell
     * - A cell is empty if its value is 0
     * 
     * Hint: Loop through the board and check each cell
     */
    private ArrayList<int[]> getEmptyCells() {
        // TODO: Complete this method
        ArrayList<int[]> emptyCells = new ArrayList<>();
        
        return emptyCells;
    }
    
    /**
     * TODO #3: Implement the moveLeft method
     * Requirements:
     * - Slide all tiles to the left (remove gaps)
     * - Merge adjacent tiles with same value
     * - Each tile can only merge once per move
     * - Update score when tiles merge (add merged value to score)
     * - Add a random tile after a successful move
     * - Return true if any tiles moved, false otherwise
     * 
     * Algorithm hints:
     * 1. For each row:
     *    a. Compress tiles to the left (remove zeros)
     *    b. Merge adjacent equal tiles
     *    c. Check if the row changed
     * 2. If any row changed, add a random tile
     */
    public boolean moveLeft() {
        // TODO: Complete this method
        boolean moved = false;
        
        return moved;
    }
    
    /**
     * TODO #4: Implement the moveRight method
     * Requirements:
     * - Similar to moveLeft but in opposite direction
     * - Slide tiles to the right
     * - Merge from right to left
     * 
     * Hint: Process from right to left instead of left to right
     */
    public boolean moveRight() {
        // TODO: Complete this method
        boolean moved = false;
        
        return moved;
    }
    
    /**
     * TODO #5: Implement the moveUp method
     * Requirements:
     * - Similar logic to moveLeft but operates on columns
     * - Slide tiles up
     * - Merge from top to bottom
     * 
     * Hint: Work with columns instead of rows
     */
    public boolean moveUp() {
        // TODO: Complete this method
        boolean moved = false;
        
        return moved;
    }
    
    /**
     * TODO #6: Implement the moveDown method
     * Requirements:
     * - Similar to moveUp but in opposite direction
     * - Slide tiles down
     * - Merge from bottom to top
     */
    public boolean moveDown() {
        // TODO: Complete this method
        boolean moved = false;
        
        return moved;
    }
    
    /**
     * TODO #7: Implement method to check if the player has won
     * Requirements:
     * - Return true if any tile has value >= WIN_VALUE (2048)
     * - Once won, should continue returning true (use hasWon field)
     * 
     * Hint: Check all tiles and update the hasWon field
     */
    public boolean hasWon() {
        // TODO: Complete this method
        
        return false;
    }
    
    /**
     * TODO #8: Implement method to check if game is over
     * Requirements:
     * - Game is over when:
     *   1. No empty cells remain AND
     *   2. No adjacent tiles (horizontal or vertical) can be merged
     * - Update the gameOver field when game ends
     * 
     * Hint: First check for empty cells, then check all adjacent pairs
     */
    public boolean isGameOver() {
        // TODO: Complete this method
        
        return false;
    }
    
    // ===================== PROVIDED METHODS - DO NOT MODIFY =====================
    
    /**
     * Gets a copy of the current board state
     */
    public int[][] getBoard() {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    /**
     * Gets the current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the board size
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }
    
    /**
     * Helper method for debugging - prints the board to console
     */
    public void printBoard() {
        System.out.println("Score: " + score);
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.printf("%4d ", board[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------");
    }
}
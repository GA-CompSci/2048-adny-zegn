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
     * Requirements:
     * - 90% chance of adding a 2
     * - 10% chance of adding a 4
     * - Should only add to empty cells
     * - Use the getEmptyCells() method to find empty positions
     */
    private void addRandomTile() {
        // Check if there are any empty cells
        ArrayList<int[]> emptyCells = getEmptyCells();
        if (emptyCells.isEmpty()) return;

        // Pick a random cell
        int[] cell = emptyCells.get((int)(Math.random() * emptyCells.size()));

        // Randomly assign 
        if (Math.random() < 0.9) board[cell[0]][cell[1]] = 2;
        else board[cell[0]][cell[1]] = 4;
    }
    
    /**
     * Requirements:
     * - Return an ArrayList of int arrays [row, col] for each empty cell
     * - A cell is empty if its value is 0
     */
    private ArrayList<int[]> getEmptyCells() {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) emptyCells.add(new int[] {row, col});
            }
        }
        return emptyCells;
    }
    
    /**
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
        boolean moved = false;
        for (int row = 0; row < BOARD_SIZE; row++) {
            int[] temp = new int[BOARD_SIZE];
            int skipIterator = 0;
            // Consolidate all numbers towards the left
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != 0) {
                    temp[skipIterator] = board[row][col];
                    skipIterator++;
                }
            }
            skipIterator = 0; // We will reuse this later
            // Merge all doubles - this will create new zeroes
            for (int i = 0; i < BOARD_SIZE - 1; i++) {
                if (temp[i] == temp[i + 1])  {
                    score += 2 * temp[i];
                    temp[i] *= 2;
                    temp[i + 1] = 0;
                }
            }
            // Check if the board has changed at this point
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (temp[col] == board[row][col]) continue;
                moved = true;
            }
            // Clear board
            board[row] = new int[BOARD_SIZE];
            // Consolidate while moving everything to the board
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (temp[i] != 0) {
                    board[row][skipIterator] = temp[i];
                    skipIterator++;
                }
            }
        }
        if (moved) addRandomTile();

        return moved;
    }
    
    /**
     * Requirements:
     * - Similar to moveLeft but in opposite direction
     * - Slide tiles to the right
     * - Merge from right to left
     * 
     * Hint: Process from right to left instead of left to right
     */
    public boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < BOARD_SIZE; row++) {
            int[] temp = new int[BOARD_SIZE];
            int skipIterator = BOARD_SIZE - 1;
            // Consolidate all numbers towards the right
            for (int col = BOARD_SIZE - 1; col >= 0 ; col--) {
                if (board[row][col] != 0) {
                    temp[skipIterator] = board[row][col];
                    skipIterator--;
                }
            }
            skipIterator = BOARD_SIZE - 1; // We will reuse this later
            // Merge all doubles - this will create new zeroes
            for (int i = BOARD_SIZE - 1; i > 0; i--) {
                if (temp[i] == temp[i - 1])  {
                    score += 2 * temp[i];
                    temp[i] *= 2;
                    temp[i - 1] = 0;
                }
            }
            // Check if the board has changed at this point
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (temp[col] == board[row][col]) continue;
                moved = true;
            }
            // Clear board
            board[row] = new int[BOARD_SIZE];
            // Consolidate while moving everything to the board
            for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                if (temp[i] != 0) {
                    board[row][skipIterator] = temp[i];
                    skipIterator--;
                }
            }
        }
        if (moved) addRandomTile();

        return moved;
    }
    
    /**
     * Requirements:
     * - Similar logic to moveLeft but operates on columns
     * - Slide tiles up
     * - Merge from top to bottom
     * 
     * Hint: Work with columns instead of rows
     */
    public boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < BOARD_SIZE; col++) {
            int[] temp = new int[BOARD_SIZE];
            int skipIterator = 0;
            // Consolidate all numbers towards the left
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (board[row][col] != 0) {
                    temp[skipIterator] = board[row][col];
                    skipIterator++;
                }
            }
            skipIterator = 0; // We will reuse this later
            // Merge all doubles - this will create new zeroes
            for (int i = 0; i < BOARD_SIZE - 1; i++) {
                if (temp[i] == temp[i + 1])  {
                    score += 2 * temp[i];
                    temp[i] *= 2;
                    temp[i + 1] = 0;
                }
            }
            // Check if the board has changed at this point
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (temp[row] == board[row][col]) continue;
                moved = true;
            }
            // Clear board
            for (int row = 0; row < BOARD_SIZE; row++) {
                board[row][col] = 0;
            }
            // Consolidate while moving everything to the board
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (temp[i] != 0) {
                    board[skipIterator][col] = temp[i];
                    skipIterator++;
                }
            }
        }
        if (moved) addRandomTile();

        return moved;
    }
    
    /**
     * Requirements:
     * - Similar to moveUp but in opposite direction
     * - Slide tiles down
     * - Merge from bottom to top
     */
    public boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < BOARD_SIZE; col++) {
            int[] temp = new int[BOARD_SIZE];
            int skipIterator = BOARD_SIZE - 1;
            // Consolidate all numbers towards the right
            for (int row = BOARD_SIZE - 1; row >= 0 ; row--) {
                if (board[row][col] != 0) {
                    temp[skipIterator] = board[row][col];
                    skipIterator--;
                }
            }
            skipIterator = BOARD_SIZE - 1; // We will reuse this later
            // Merge all doubles - this will create new zeroes
            for (int i = BOARD_SIZE - 1; i > 0; i--) {
                if (temp[i] == temp[i - 1])  {
                    score += 2 * temp[i];
                    temp[i] *= 2;
                    temp[i - 1] = 0;
                }
            }
            // Check if the board has changed at this point
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (temp[row] == board[row][col]) continue;
                moved = true;
            }
            // Clear board
            for (int row = 0; row < BOARD_SIZE; row++) {
                board[row][col] = 0;
            }
            // Consolidate while moving everything to the board
            for (int i = BOARD_SIZE - 1; i >= 0; i--) {
                if (temp[i] != 0) {
                    board[skipIterator][col] = temp[i];
                    skipIterator--;
                }
            }
        }
        if (moved) addRandomTile();

        return moved;
    }
    
    /**
     * Requirements:
     * - Return true if any tile has value >= WIN_VALUE (2048)
     * - Once won, should continue returning true (use hasWon field)
     * 
     * Hint: Check all tiles and update the hasWon field
     */
    public boolean hasWon() {
        for (int[] row : board) {
            for (int tile : row) {
                if (tile >= WIN_VALUE) hasWon = true;
            }
        }
        return hasWon;
    }
    
    /**
     * Requirements:
     * - Game is over when:
     *   1. No empty cells remain AND
     *   2. No adjacent tiles (horizontal or vertical) can be merged
     * - Update the gameOver field when game ends
     * 
     * Hint: First check for empty cells, then check all adjacent pairs
     */
    public boolean isGameOver() {
        if (getEmptyCells().size() > 0) return false;
        for (int row = 0; row < BOARD_SIZE - 1; row++) {
            for (int col = 0; col < BOARD_SIZE - 1; col++) {
                if (board[row][col] == board[row + 1][col] || board[row][col] == board[row][col + 1]) return false;
            }
        }
        return true;
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
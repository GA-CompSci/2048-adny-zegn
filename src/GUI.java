import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GUI class for the 2048 game
 * Handles all visual components and user input
 */
public class GUI extends JFrame {
    private static final int GRID_SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int TILE_MARGIN = 12;

    // =====================================================================
    // COLOR CONFIGURATION — Change these to customize the look of the game!
    // =====================================================================

    // Background & text colors
    private static final Color BACKGROUND_COLOR    = new Color(250, 248, 239);
    private static final Color TEXT_COLOR           = new Color(119, 110, 101);
    private static final Color BOARD_COLOR         = new Color(187, 173, 160);
    private static final Color BUTTON_COLOR        = new Color(143, 122, 102);

    // Tile text colors (low = 2 & 4, high = 8+)
    private static final Color TILE_TEXT_DARK       = new Color(119, 110, 101);
    private static final Color TILE_TEXT_LIGHT      = Color.WHITE;

    // Status message colors
    private static final Color WIN_COLOR           = new Color(237, 194, 46);
    private static final Color GAME_OVER_COLOR     = Color.RED;

    // Tile background colors — one per tile value
    private static final Color TILE_EMPTY  = new Color(238, 228, 218);
    private static final Color TILE_2      = new Color(238, 228, 218);
    private static final Color TILE_4      = new Color(237, 224, 200);
    private static final Color TILE_8      = new Color(242, 177, 121);
    private static final Color TILE_16     = new Color(245, 149, 99);
    private static final Color TILE_32     = new Color(246, 124, 95);
    private static final Color TILE_64     = new Color(246, 94, 59);
    private static final Color TILE_128    = new Color(237, 207, 114);
    private static final Color TILE_256    = new Color(237, 204, 97);
    private static final Color TILE_512    = new Color(237, 200, 80);
    private static final Color TILE_1024   = new Color(237, 197, 63);
    private static final Color TILE_2048   = new Color(237, 194, 46);

    private static final Color[] TILE_COLORS = {
        TILE_EMPTY, TILE_2, TILE_4, TILE_8, TILE_16, TILE_32,
        TILE_64, TILE_128, TILE_256, TILE_512, TILE_1024, TILE_2048
    };

    // =====================================================================

    private JLabel[][] tiles;
    private JLabel scoreLabel;
    private JLabel statusLabel;
    private Game game;
    private JPanel gamePanel;
    
    public GUI() {
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Initialize the game logic
        game = new Game();
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Create header panel with score and status
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title and score panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel titleLabel = new JLabel("2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(TEXT_COLOR);
        
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(TEXT_COLOR);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(scoreLabel, BorderLayout.EAST);
        
        // Status and instructions panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND_COLOR);
        
        statusLabel = new JLabel("Use arrow keys to play!");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(TEXT_COLOR);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 14));
        newGameButton.setBackground(BUTTON_COLOR);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setOpaque(true);
        newGameButton.setBorderPainted(false);
        newGameButton.setFocusPainted(false);
        newGameButton.addActionListener(e -> resetGame());
        
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(newGameButton, BorderLayout.EAST);
        
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Create game board panel
        gamePanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, TILE_MARGIN, TILE_MARGIN));
        gamePanel.setBackground(BOARD_COLOR);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(TILE_MARGIN, TILE_MARGIN, TILE_MARGIN, TILE_MARGIN));
        gamePanel.setPreferredSize(new Dimension(
            GRID_SIZE * TILE_SIZE + (GRID_SIZE + 1) * TILE_MARGIN,
            GRID_SIZE * TILE_SIZE + (GRID_SIZE + 1) * TILE_MARGIN
        ));
        
        // Initialize tile labels
        tiles = new JLabel[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                tiles[i][j] = new JLabel("", SwingConstants.CENTER);
                tiles[i][j].setFont(new Font("Arial", Font.BOLD, 32));
                tiles[i][j].setOpaque(true);
                tiles[i][j].setBorder(BorderFactory.createLineBorder(BOARD_COLOR, 2));
                gamePanel.add(tiles[i][j]);
            }
        }
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Add keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (game.isGameOver()) {
                    return;
                }
                
                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        moved = game.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        moved = game.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        moved = game.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        moved = game.moveRight();
                        break;
                }
                
                if (moved) {
                    updateDisplay();
                    checkGameStatus();
                }
            }
        });
        
        setFocusable(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        updateDisplay();
    }
    
    private void updateDisplay() {
        int[][] board = game.getBoard();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = board[i][j];
                if (value == 0) {
                    tiles[i][j].setText("");
                    tiles[i][j].setBackground(TILE_COLORS[0]);
                } else {
                    tiles[i][j].setText(String.valueOf(value));
                    tiles[i][j].setBackground(getTileColor(value));
                    tiles[i][j].setForeground(value <= 4 ? TILE_TEXT_DARK : TILE_TEXT_LIGHT);
                    
                    // Adjust font size based on number of digits
                    if (value < 100) {
                        tiles[i][j].setFont(new Font("Arial", Font.BOLD, 48));
                    } else if (value < 1000) {
                        tiles[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                    } else {
                        tiles[i][j].setFont(new Font("Arial", Font.BOLD, 28));
                    }
                }
            }
        }
        scoreLabel.setText("Score: " + game.getScore());
    }
    
    private Color getTileColor(int value) {
        int index = 0;
        int temp = value;
        while (temp > 1) {
            temp /= 2;
            index++;
        }
        return index < TILE_COLORS.length ? TILE_COLORS[index] : TILE_COLORS[TILE_COLORS.length - 1];
    }
    
    private void checkGameStatus() {
        if (game.hasWon()) {
            statusLabel.setText("Congratulations! You won!");
            statusLabel.setForeground(WIN_COLOR);
            JOptionPane.showMessageDialog(this, 
                "Congratulations! You reached 2048!\nYour score: " + game.getScore(), 
                "You Won!", 
                JOptionPane.INFORMATION_MESSAGE);
        } else if (game.isGameOver()) {
            statusLabel.setText("Game Over! No moves left.");
            statusLabel.setForeground(GAME_OVER_COLOR);
            JOptionPane.showMessageDialog(this, 
                "Game Over!\nFinal Score: " + game.getScore(), 
                "Game Over", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void resetGame() {
        game.resetGame();
        updateDisplay();
        statusLabel.setText("Use arrow keys to play!");
        statusLabel.setForeground(TEXT_COLOR);
        requestFocus();
    }
}
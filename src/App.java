/**
 * Launcher class for the 2048 game
 * This is the entry point of the application
 */
public class App {
    public static void main(String[] args) {
        // Create and display the game window
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
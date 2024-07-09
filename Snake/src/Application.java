import javax.swing.*;

public class Application {
    public static void main(String[] args) {

        int boardWidth = 600;
        int boardHeight = 600; // Set board height equal to board width

        // Create the JFrame (window) for the game
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true); // Make the frame visible
        frame.setSize(boardWidth, boardHeight); // Set the size of the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setResizable(false); // Make the frame non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the frame is closed

        // Create an instance of the SnakeGame
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame); // Add the game to the frame
        // frame.pack(); // This line is removed to prevent resizing
        snakeGame.requestFocus(); // Request focus so that key events are received
    }
}

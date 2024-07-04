import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    // Inner class to represent each tile on the board
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // Snake attributes
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food attributes
    Tile food;
    Random random;

    // Game logic attributes
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;

    // Constructor to initialize the game
    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight)); // Set the size of the game area
        setBackground(Color.black); // Set the background color
        addKeyListener(this); // Add key listener for keyboard input
        setFocusable(true); // Ensure the panel can gain focus

        // Initialize snake
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();

        // Initialize food
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        // Initialize movement
        velocityX = 1;
        velocityY = 0;

        // Initialize game timer
        gameLoop = new Timer(100, this); // Timer to control game loop
        gameLoop.start();
    }

    // Override paintComponent to draw the game
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Method to draw the game elements
    public void draw(Graphics g) {
        // Draw grid lines
        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // Draw food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // Draw snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Draw snake body
        for (Tile snakePart : snakeBody) {
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // Draw score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + snakeBody.size(), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
        }
    }

    // Method to place food at a random location
    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    // Method to move the snake
    public void move() {
        // Check if snake eats the food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(snakeHead.x, snakeHead.y)); // Add new part to snake body
            placeFood(); // Place new food
        }

        // Move snake body
        for (int i = snakeBody.size() - 1; i > 0; i--) {
            Tile prev = snakeBody.get(i - 1);
            snakeBody.set(i, new Tile(prev.x, prev.y));
        }
        if (snakeBody.size() > 0) {
            snakeBody.set(0, new Tile(snakeHead.x, snakeHead.y));
        }

        // Move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Check for collisions with body or borders
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
                snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
            gameOver = true;
        }
    }

    // Method to check for collision between two tiles
    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    // Override actionPerformed to update game state
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move(); // Move snake
            repaint(); // Repaint game
        } else {
            gameLoop.stop(); // Stop game loop if game is over
        }
    }

    // Override keyPressed to handle user input
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // Override keyTyped and keyReleased as they are not needed
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
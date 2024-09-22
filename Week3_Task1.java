/***
 * Task: 2048 Game in Java
 * @author Uddeshya Patidar
 * Date: 20/09/2024
 */

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class Game2048App extends JPanel implements KeyListener {

    private static final int SIZE = 4; 
    private static final int TILE_SIZE = 100; 
    private static final int TILE_MARGIN = 10; 
    private static final int SCORE_PANEL_HEIGHT = 50; 
    private int[][] board = new int[SIZE][SIZE]; 
    private int score = 0; 
    private java.util.Random random = new java.util.Random();
    private boolean moved = false; 

    // Constructor: Initializes the game panel and the board.
    public Game2048App() {
        setPreferredSize(new Dimension(450, 500)); 
        setBackground(Color.darkGray);
        setFocusable(true);
        addKeyListener(this);
        initializeBoard(); 
    }
    // Main method: Creates the game window and starts the game.
    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game");
        Game2048App game = new Game2048App();  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
    }

    // Initializes the game board by adding two random tiles.
    private void initializeBoard() {
        addRandomTile();
        addRandomTile();
    }

    // Adds a random tile 2 or 4 to an empty spot on the board.
    private void addRandomTile() {
        int x, y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (board[x][y] != 0);
        // 90% chance for 2, 10% chance for 4
        board[x][y] = random.nextDouble() < 0.9 ? 2 : 4;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // score panel
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, getWidth(), SCORE_PANEL_HEIGHT);
        
        // Display the score
        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString("Score: " + score, 10, SCORE_PANEL_HEIGHT - 10);
        g.translate(0, SCORE_PANEL_HEIGHT);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                drawTile(g, row, col); // Draw each tile
            }
        }
    }

    // Draws a single tile on the game board.
    private void drawTile(Graphics g, int row, int col) {
        int value = board[row][col];
        int xOffset = TILE_MARGIN + col * (TILE_SIZE + TILE_MARGIN);
        int yOffset = TILE_MARGIN + row * (TILE_SIZE + TILE_MARGIN);
        g.setColor(getTileColor(value));
        g.fillRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE);

        // Draw the number on the tile, if not 0
        if (value != 0) {
            g.setColor(Color.black);
            String text = String.valueOf(value);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            g.drawString(text, xOffset + (TILE_SIZE - textWidth) / 2, yOffset + (TILE_SIZE + textHeight) / 2);
        }
    }

    // Adjust the color of tiles.
    private Color getTileColor(int value) {
        switch (value) {
            case 2:
                return Color.white;
            case 4:
                return Color.white;
            case 8:
                return Color.white;
            case 16:
                return Color.white;
            case 32:
                return Color.white;
            case 64:
                return Color.white;
            case 128:
                return Color.white;
            case 256:
                return Color.white;
            case 512:
                return Color.white;
            case 1024:
                return Color.white;
            case 2048:
                return Color.white;
            default:
                return Color.white;
        }
    }

    // Key press to move the tiles based on the arrow key pressed
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        moved = false;
        switch (key) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (moveUp()) moved = true;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (moveLeft()) moved = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (moveDown()) moved = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (moveRight()) moved = true;
                break;
        }
        // Add a random tile if the board was moved
        if (moved) {
            addRandomTile();
        }
        repaint();
        // if game over
        if (isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
            int response = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                resetGame(); 
            } else {
                System.exit(0); 
            }
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    // Moves all tiles up and combines them
    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < SIZE; col++) {
            int[] temp = new int[SIZE];
            int index = 0;
            for (int row = 0; row < SIZE; row++) {
                if (board[row][col] != 0) {
                    temp[index++] = board[row][col];
                }
            }
            for (int i = 0; i < SIZE - 1; i++) {
                if (temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i]; 
                    temp[i + 1] = 0;
                    moved = true;
                }
            }
            index = 0;
            // Move combined tiles to the top
            for (int i = 0; i < SIZE; i++) {
                if (temp[i] != 0) {
                    if (board[index][col] != temp[i]) moved = true;
                    board[index++][col] = temp[i];
                }
            }
            // Fill remaining spaces with 0
            while (index < SIZE) {
                if (board[index][col] != 0) moved = true;
                board[index++][col] = 0;
            }
        }
        return moved; 
    }

    // Moves all tiles to the left and combines them if necessary
    private boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < SIZE; row++) {
            int[] temp = new int[SIZE];
            int index = 0;
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != 0) {
                    temp[index++] = board[row][col];
                }
            }
            // Combine same tiles
            for (int i = 0; i < SIZE - 1; i++) {
                if (temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i]; 
                    temp[i + 1] = 0;
                    moved = true;
                }
            }
            index = 0;
            // Move combined tiles to the left
            for (int i = 0; i < SIZE; i++) {
                if (temp[i] != 0) {
                    if (board[row][index] != temp[i]) moved = true;
                    board[row][index++] = temp[i];
                }
            }
            while (index < SIZE) {
                if (board[row][index] != 0) moved = true;
                board[row][index++] = 0;
            }
        }
        return moved; 
    }
    // Moves all tiles down and combines them 
    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < SIZE; col++) {
            int[] temp = new int[SIZE];
            int index = SIZE - 1;
            for (int row = SIZE - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    temp[index--] = board[row][col];
                }
            }
            for (int i = SIZE - 1; i > 0; i--) {
                if (temp[i] == temp[i - 1]) {
                    temp[i] *= 2;
                    score += temp[i]; 
                    temp[i - 1] = 0;
                    moved = true;
                }
            }
            index = SIZE - 1;
            for (int i = SIZE - 1; i >= 0; i--) {
                if (temp[i] != 0) {
                    if (board[index][col] != temp[i]) moved = true;
                    board[index--][col] = temp[i];
                }
            }
    
            while (index >= 0) {
                if (board[index][col] != 0) moved = true;
                board[index--][col] = 0;
            }
        }
        return moved; 
    }

    // Moves all tiles to the right and combines them if necessary.
    private boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < SIZE; row++) {
            int[] temp = new int[SIZE];
            int index = SIZE - 1;
            // Collect non-zero tiles
            for (int col = SIZE - 1; col >= 0; col--) {
                if (board[row][col] != 0) {
                    temp[index--] = board[row][col];
                }
            }
            for (int i = SIZE - 1; i > 0; i--) {
                if (temp[i] == temp[i - 1]) {
                    temp[i] *= 2;
                    score += temp[i]; 
                    temp[i - 1] = 0;
                    moved = true;
                }
            }
            index = SIZE - 1;
            for (int i = SIZE - 1; i >= 0; i--) {
                if (temp[i] != 0) {
                    if (board[row][index] != temp[i]) moved = true;
                    board[row][index--] = temp[i];
                }
            }
            while (index >= 0) {
                if (board[row][index] != 0) moved = true;
                board[row][index--] = 0;
            }
        }      
        return moved; 
    }

    private boolean isGameOver() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false; 
                }
                if (col < SIZE - 1 && board[row][col] == board[row][col + 1]) {
                    return false; // same tiles can be combined horizontally
                }
                if (row < SIZE - 1 && board[row][col] == board[row + 1][col]) {
                    return false; // same tiles can be combined vertically
                }
            }
        }
        return true;
    }

    // Resets the game board and score for a new game.
    private void resetGame() {
        board = new int[SIZE][SIZE]; 
        score = 0; 
        initializeBoard(); 
        repaint(); 
    }
}

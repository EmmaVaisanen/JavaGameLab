package gev3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Brickwall {
    private List<Color> blockColors;
    private List<Block> blocks;
    private List<Block> blockCollided;
    private int score;
    private Game game;

    public Brickwall(int x, int y, int width, int height, int numRows, int numCols, Game game) {
        // Konstruktor som skapar en vägg med block som har olika färger beroende på rad.
        blocks = new ArrayList<>();
        blockColors = new ArrayList<>();
        blockCollided = new ArrayList<>();
        blockColors.add(Color.BLUE); // Rad 1 score 1
        blockColors.add(Color.GREEN); // Rad 2 score 2
        blockColors.add(Color.YELLOW);// Rad 3 score 3
        blockColors.add(Color.RED);// Rad 4 score 4

        score = 0;

        this.game = game;

        int blockWidth = width / numCols;
        int blockHeight = height / numRows;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int blockX = x + col * blockWidth;  // Kolumn
                int blockY = y + row * blockHeight; // Rad
                int startHealth = blockColors.size() - row; // Avgör vad hälsan är för blocken beroende på rad.
                Block block = new Block(blockX, blockY, blockWidth, blockHeight, startHealth, blockColors); //parametrar för blocken
                blocks.add(block);
            }
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void update(Keyboard keyboard) {

    }

    public void draw(Graphics2D graphics) {
        // Metod för att rita ut brickväggen.
        for (Block block : blocks) {
            if (block.isVisible()) {
                graphics.setColor(block.getColor());
                graphics.fillRect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
                graphics.setColor(Color.BLACK);
                graphics.drawRect(block.getX(), block.getY(), block.getWidth(), block.getHeight());
            }
        }
    }

    public void collisionHandling(List<Ball> balls) {
        // Används för att hantera borttagning av block.
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (!block.isVisible()) {
                continue;
            }
            boolean blockCollided = false;
            for (Ball ball : balls) {
                if (ball.intersect(block)) {
                    ball.collisionHandling(block);
                    block.collisionHandling(ball);
                    Color blockColor = block.getColor();
                    block.setColor(blockColor);

                    score += block.getHealth() + 1; // Poänglogik

                    int lives = ball.getLives();

                    game.updateScore(block.getHealth() + 1, lives);

                    if (block.getHealth() <= 0) {
                        iterator.remove();
                    }
                    blockCollided = true;
                    break;
                }
            }
            if (blockCollided) {
                System.out.println(score); // Används för debugsyften
            }

            if (blocks.isEmpty()) {
                game.gameOver();    // Avslutar spelet när Brickwall är tomt
            }
        }

    }
}

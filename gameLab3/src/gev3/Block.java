package gev3;

import java.awt.*;
import java.util.List;

public class Block extends Sprite implements Collidable {
    private int health;
    private List<Color> blockColors;
    private boolean isVisible; // Avgör om blocket ska ritas eller inte

    public Block(int x, int y, int width, int height, int startHealth, List<Color> blockColors) {
        super(x, y, width, height);
        this.isVisible = true;
        this.blockColors = blockColors;
        this.health = startHealth;
    }

    // Minskar blockets hälsa
    public void decreaseHealth() {
        health--;
    }

    // Returnerar blockets hälsa
    public int getHealth() {
        return health;
    }

    // Returnerar true om blocket är synligt
    public boolean isVisible() {
        return isVisible;
    }

    // Färglogik för blocken
    public Color getColor() {
        int colorIndex = Math.max(0, Math.min(health - 1, blockColors.size() - 1));
        return blockColors.get(colorIndex);
    }

    public void setColor(Color color) {

    }

    @Override
    public void update(Keyboard keyboard) {
    }

    @Override
    public void draw(Graphics2D graphics) {
        if ((isVisible)) {
            graphics.setColor(getColor());
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
            graphics.setColor(Color.BLACK);
            graphics.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public boolean intersect(Collidable other) {
        // Gör ett standard returvärde, då detta redan har implementerats i boll klassen.
        return false;
    }

    @Override
    public void collisionHandling(Collidable other) {
        // Hanterar kollisionen mellan blocket och bollen.
        if (other instanceof Ball) {
            Ball ball = (Ball) other;
            if (isVisible) {
                int ax = ball.getX() + ball.getWidth() / Const.BALL_CENTER_HORIZONTAL;
                int ay = ball.getY() + ball.getHeight() / Const.BALL_CENTER_VERTICAL;
                int bx = getX() + getWidth() / Const.BLOCK_CENTER_HORIZONTAL;
                int by = getY() + getHeight() / Const.BLOCK_CENTER_VERTICAL;
                int bw = getWidth() / Const.BLOCK_HALF_WIDTH;
                int bh = getHeight() / Const.BLOCK_HALF_HEIGHT;

                // Beräkning av distansen från centerpunkten av boll och block
                int dx = Math.abs(ax - bx) - bw;
                int dy = Math.abs(ay - by) - bh;

                if (dx > dy) {
                    ball.setDx(Math.abs(ball.getDx()));
                } else {  // Viktigt för att se till så att bollen inte studsar uppåt eller tvärtom vid kollision mellan två block.
                    if (ball.getY() + ball.getHeight() / Const.BALL_HALF_HEIGHT < by) {
                        ball.setDy(-Math.abs(ball.getDy()));
                    } else {
                        ball.setDy(Math.abs(ball.getDy()));
                    }
                }
                if (health > 0) {
                    decreaseHealth();
                }
                if (health <= 0) {
                    isVisible = false;
                }
            }
        }
    }

}

package gev3;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Sprite implements Collidable {
    private int dx, dy;
    private int lives;
    private Game game;

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getLives() {
        return lives;
    }

    public Ball(int x, int y, int width, int height, int dx, int dy, int lives, Game game) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.lives = lives;
        this.game = game;
    }

    @Override
    public void update(Keyboard keyboard) {
        if (!game.isGameOver()) {
            setX(getX() + dx);
            setY(getY() + dy);
        }

        // Kollisionen för taket implementerades här.
        if (getY() <= 0) {      // Taklogik
            setDy(Math.abs(getDy()));
        }

        // Logiken för när spelaren förlorar en boll.
        if (getY() + getHeight() >= Const.SCREEN_HEIGHT) {
            setX(Const.BALL_RESPAWNX);
            setY(Const.BALL_RESPAWNY);
            setDy(-(getDy()));
            --lives;
            game.updateScore(0, lives);
        }
        // Game over state om spelaren får slut på bollar
        if (lives <= 0) {
            game.gameOver();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.PINK);
        Ellipse2D.Double Circle = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        graphics.fill(Circle);
    }

    // Kollar om bollen träffar ett block.
    @Override
    public boolean intersect(Collidable other) {
        if (other instanceof Block) {
            Block block = (Block) other;
            if (!block.isVisible()) {
                return false;
            }
            Rectangle ballBounds = getBounds();
            Rectangle blockBounds = block.getBounds();
            return ballBounds.intersects(blockBounds);
        }
        return false;
    }

    @Override
    public void collisionHandling(Collidable other) {
    }

}



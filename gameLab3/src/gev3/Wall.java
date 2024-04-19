package gev3;

import java.awt.*;

public class Wall extends Sprite implements Collidable {
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update(Keyboard keyboard) {
    }

    // Ritar upp väggarna
    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.blue);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean intersect(Collidable other) {
        return false;
    }

    @Override
    public void collisionHandling(Collidable other) {
        // Hanterar kollision om bollen träffar en av väggarna
        if (other instanceof Ball) {
            Ball ball = (Ball) other;
            Rectangle ballBounds = ball.getBounds();
            Rectangle wallBounds = getBounds();

            // Kollar om bollen träffar den västra eller östra väggen
            Boolean isBallWestWall = ballBounds.getMaxX() <= wallBounds.getMinX();
            Boolean isBallEastWall = ballBounds.getMaxX() >= wallBounds.getMaxX();

            // Speglar tillbaka bollens horisontella hastighet om den träffar någon av väggarna.
            if (isBallWestWall || isBallEastWall) {
                ball.setDx(-ball.getDx());
            }
        }
    }
}

package Gev2;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Sprite{
    private int horizontal;
    private int vertical;
    private int fart = 2;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);

        horizontal = 0;
        vertical = 0;
    }

    @Override
    public void update(Keyboard keyboard) {
        if (keyboard.isKeyDown(Key.Up)) {
            setY(getY() - fart);
        }
        if (keyboard.isKeyDown(Key.Down)) {
            setY(getY() + fart);
        }
        if (keyboard.isKeyDown(Key.Left)) {
            setX(getX() - fart);
        }
        if (keyboard.isKeyDown(Key.Right)) {
            setX(getX() + fart);
        }

        setX(getX() + horizontal);
        setY(getY() + vertical);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.ORANGE);
        Ellipse2D.Double Circle = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        graphics.fill(Circle);
    }
}

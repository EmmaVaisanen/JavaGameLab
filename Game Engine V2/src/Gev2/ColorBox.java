package Gev2;

import java.awt.*;

public class ColorBox extends Sprite {
    private Color color;
    public ColorBox(int x, int y, int width, int height, Color color){
        super(x,y,width,height);
        this.color = color;
    }

    public void update(Keyboard keyboard) {
        setX(getX() + 1);
        setY(getY() + 1);
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}

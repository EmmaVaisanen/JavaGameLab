package gev3;

import java.awt.*;

public class Bat extends Sprite {
    private Color color;
    private Color initialsColor;
    private String initials;

    public Bat(int x, int y, int width, int height, Color color, String initials, Color initialsColor) {
        super(x, y, width, height);
        this.color = color;
        this.initials = initials;
        this.initialsColor = initialsColor;
    }

    // Uppdaterar slagträdets position beroende på vilken piltangent spelaren trycker på.
    @Override
    public void update(Keyboard keyboard) {
        if (keyboard.isKeyDown(Key.Left)) {
            setX(getX() - Const.BAT_SPEED);
        }
        if (keyboard.isKeyDown(Key.Right)) {
            setX(getX() + Const.BAT_SPEED);
        }
    }

    // Ritar upp slagträdet och initialerna på slagträdet
    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        graphics.setColor(initialsColor);
        graphics.drawString(initials, getX() + getWidth() / 2, getY() + getHeight() / 2); //Mitten av slagträdet

    }
}

package gev3;
import java.awt.*;

// Abstrakt klass som används för objekten i spelet.
public abstract class Sprite {
	public Rectangle getBounds() {return new Rectangle(x,y,width,height);} // La till detta för kollisionslogiken
	private int x, y, width, height;
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public void setX(int x) { this.x = x; };
	public void setY(int y) { this.y = y; };
	public void setWidth(int width) { this.width = width; };
	public void setHeight(int height) { this.height = height; };
	public Sprite(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract void update(Keyboard keyboard);
	public abstract void draw(Graphics2D graphics);
}

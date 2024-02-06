package gev3;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.Rectangle;

public class Game {
	private Ball circle;
	private Bat bat;
	private Bricks brick;
	private ArrayList<Ball> ball;
	private boolean checkCollision(Ball circle, Bat bat) {
		Rectangle circleRect = new Rectangle(circle.getX(), circle.getY(), circle.getWidth(), circle.getHeight());
		Rectangle batRect = new Rectangle(bat.getX(), bat.getY(), bat.getWidth(), bat.getHeight());

		return circleRect.intersects(batRect);
	}

	public Game(GameBoard board) {
		circle = new Ball(350, 100, 40, 40, 0, 2);
		bat = new Bat(300, 550,150,20, Color.LIGHT_GRAY);
		ball = new ArrayList<>();
		brick = new Bricks(50, 50, 400, 200, 3, 5);
	}

	public void update(Keyboard keyboard) {
		circle.update(keyboard);
		bat.update(keyboard);
		brick.update(keyboard);

		if(checkCollision(circle, bat, brick)){
			circle.setDy(-circle.getDy());
		}
	}


	public void draw(Graphics2D graphics) {
		circle.draw(graphics);
		bat.draw(graphics);
		brick.draw(graphics);
	}
}

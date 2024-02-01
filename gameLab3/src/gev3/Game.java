package gev3;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class Game {
	private Ball circle;
	private Bat bat;
	private ArrayList<Ball> ball;
	public Game(GameBoard board) {
		circle = new Ball(350, 100, 40, 40);
		bat = new Bat(300, 550,150,20, Color.LIGHT_GRAY);
		ball = new ArrayList<>();
	}

	public void update(Keyboard keyboard) {
		circle.update(keyboard);
		bat.update(keyboard);
	}

	public void draw(Graphics2D graphics) {
		circle.draw(graphics);
		bat.draw(graphics);
	}
}

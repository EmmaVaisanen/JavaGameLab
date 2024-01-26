package Gev2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class Game {
	/*private ArrayList<ColorBox>boxes;*/
	private SquareCollection squareCollection;
	private Ball circle;

	/*private Redbox rod;
	private Bluebox bla;
	private Greenbox gron;*/
	private int tickCounter = 0;
	public Game(GameBoard board) {
		/*boxes = new ArrayList<>();
		rod = new Redbox(20, 20, 20, 20);
		bla = new Bluebox(40,20,20,20);
		gron = new Greenbox(60,20,20,20);*/
		squareCollection = new SquareCollection(20,20,20,20,10);
		circle = new Ball(100, 100, 20, 20);
	}

	public void update(Keyboard keyboard) {
		tickCounter++;

		/*rod.update(keyboard);
		bla.update(keyboard);
		gron.update(keyboard);*/
		squareCollection.update(keyboard);

		if(tickCounter % 40 == 0){
			ColorBox newBox = new Redbox(40,20,20,20);
			/*boxes.add(newBox);*/
		}
		/*for (ColorBox box : boxes) {
			box.update(keyboard);
		}*/
	}

	public void draw(Graphics2D graphics) {
		/*rod.draw(graphics);
		bla.draw(graphics);
		gron.draw(graphics);*/

		/*for (ColorBox box : boxes) {
			box.draw(graphics);*/
		squareCollection.draw(graphics);
		circle.draw(graphics);
		}
	}



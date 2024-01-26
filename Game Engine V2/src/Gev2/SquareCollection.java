package Gev2;

import java.awt.*;
import java.util.ArrayList;

public class SquareCollection {
    private ArrayList<Redbox> squares;

    public SquareCollection(int x, int y, int Width, int Height, int numSquare) {
        squares = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            int mellanrum = i * (Width + 10) + x;
            squares.add(new Redbox(mellanrum, 20 ,20 ,20));
        }
    }

    public void draw(Graphics2D graphics){
        for (Redbox square : squares) {
            square.draw(graphics);
        }
    }

    public void update(Keyboard keyboard) {
       for (Redbox square : squares) {
           square.setY(square.getY() + 1);
       }
       for (Redbox square : squares){
           if (square.getY() + square.getHeight() >= 600){
               System.exit(0);
           }
       }
    }
}

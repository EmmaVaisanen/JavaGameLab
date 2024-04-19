package gev3;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    // Initieringen av lite krimskrams.
    private Ball circle;
    private Bat bat;
    private ArrayList<Sprite> scaryMonstersAndNiceSprites; // Sprites för block och väggar
    private ArrayList<Brickwall> bricks;
    private ArrayList<Ball> ball;
    private int score;
    private GameBoard gameBoard;
    private boolean isGameOver = false; // Initierar game over och förklarar det falsk vid start under programmet
    private ArrayList<Wall> walls;
    public int getScore() {
        return score;
    }

    // Konstruktor som initierar variabler och spelet.
    public Game(GameBoard board) {
        scaryMonstersAndNiceSprites = new ArrayList<>();
        circle = new Ball(Const.BALL_SPAWNX, Const.BALL_SPAWNY, Const.BALL_SIZE, Const.BALL_SIZE, Const.BALL_SPEEDX, -(Const.BALL_SPEEDY), Const.BALL_LIVES, this);
        bat = new Bat(Const.BAT_POSX, Const.BAT_POSY, Const.BAT_WIDTH, Const.BAT_HEIGHT, Color.LIGHT_GRAY, "EV", Color.BLACK);
        ball = new ArrayList<>();
        bricks = new ArrayList<>();
        walls = new ArrayList<>();
        score = 0;
        gameBoard = board;


        Brickwall brick = new Brickwall(0, Const.BRICKWALL_POSY, Const.BRICKWALL_WIDTH, Const.BRICKWALL_HEIGHT, Const.NUM_ROWS, Const.NUM_COLS, this);
        bricks.add(brick);
        for (Block block : brick.getBlocks()) {
            scaryMonstersAndNiceSprites.add(block);
        }

        Wall westWall = new Wall(0, 0, Const.WALL_WIDTH, Const.WALL_HEIGHT);
        Wall eastWall = new Wall(board.getPreferredSize().width - Const.WALL_WIDTH, 0, Const.WALL_WIDTH, board.getPreferredSize().height);
        Wall northWall = new Wall(0, 0, board.getPreferredSize().width, Const.WALL_NORTHHEIGHT);

        walls.add(westWall);
        walls.add(eastWall);
        walls.add(northWall);

        scaryMonstersAndNiceSprites.add(westWall);
        scaryMonstersAndNiceSprites.add(eastWall);
        scaryMonstersAndNiceSprites.add(northWall);

        ball.add(circle);
    }

    // Sätter game over till true
    public void gameOver() {
        isGameOver = true;
    }

    // Returnerar game over till hela programmet
    public boolean isGameOver() {
        return isGameOver;
    }

    public void updateScore(int points, int lives) {
        score += points;
        gameBoard.updateStatus(score, lives); // Används för att uppdatera statusen på poäng och liv.
    }

    // Uppdaterar spelets logik och olika tillstånd
    public void update(Keyboard keyboard) {
        for (Sprite sp : scaryMonstersAndNiceSprites) {
            sp.update(keyboard);
        }

        for (Sprite sp : ball) {
            sp.update(keyboard);
            if (sp instanceof Ball) {
                Ball ball = (Ball) sp;
                if (bat.getBounds().intersects(ball.getBounds())) {
                    ball.setDy(-ball.getDy());
                }
                for (Wall wall : walls) {
                    if (ball.getBounds().intersects(wall.getBounds())) {
                        wall.collisionHandling(ball);
                    }
                }
                if (isGameOver) {
                    return;
                }
            }
        }

        for (Brickwall brickwall : bricks) {
            brickwall.update(keyboard);
            brickwall.collisionHandling(ball);
            for (Block block : brickwall.getBlocks()) {
                block.getColor();
            }
        }
        bat.update(keyboard);
    }

    // Ritar ut objekten i spelet
    public void draw(Graphics2D graphics) {
        circle.draw(graphics);
        bat.draw(graphics);

        for (Brickwall block : bricks) {
            block.draw(graphics);
        }

        for (Sprite sprite : scaryMonstersAndNiceSprites) {
            sprite.draw(graphics);
        }
    }
}
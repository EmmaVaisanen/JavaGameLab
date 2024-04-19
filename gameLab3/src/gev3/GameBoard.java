package gev3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel {
    private final int FPS = Const.FRAME_RATE;
    private Game game;
    private Keyboard keyboard;

    // UI-komponenter
    private JLabel scoreSign;
    private JLabel livesSign;
    private JLabel highscoreSign;
    private JLabel previousScoreSign;
    private JList<String> highScoresList;
    private JList<String> previousScoresList;
    private JTextField initialsField;

    // Kollar om spelaren har matat in sitt namn
    private boolean nameEntered = false;
    private HighScoresModel highScoresModel;
    private PreviousScoresModel previousScoresModel;
    public GameBoard() {
        keyboard = new Keyboard();
        game = new Game(this);
        setLayout(null);

        // Skylt för antalet bollar som finns kvar
        livesSign = new JLabel();
        livesSign.setForeground(Color.WHITE);
        livesSign.setFont(new Font("Arial", Font.BOLD, Const.STATUS_FONT_SIZE));
        livesSign.setBounds(Const.LIVES_POSX, Const.LIVES_POSY, Const.STATUS_WIDTH, Const.STATUS_HEIGHT);
        add(livesSign);

        // Skylt för antalet poäng som spelaren har fått
        scoreSign = new JLabel();
        scoreSign.setForeground(Color.WHITE);
        scoreSign.setFont(new Font("Arial", Font.BOLD, Const.STATUS_FONT_SIZE));
        scoreSign.setBounds(Const.SCORE_POSX, Const.SCORE_POSY, Const.STATUS_WIDTH, Const.STATUS_HEIGHT);
        add(scoreSign);

        // Text som ritas upp över highscoresList
        highscoreSign = new JLabel();
        highscoreSign.setVisible(false);
        highscoreSign.setForeground(Color.WHITE);
        highscoreSign.setFont(new Font("Arial", Font.BOLD, Const.STATUS_FONT_SIZE));
        highscoreSign.setBounds(Const.HIGHSCORESIGN_POSX, Const.HIGHSCORESIGN_POSY, Const.STATUS_WIDTH, Const.STATUS_HEIGHT);
        add(highscoreSign);

        // Text som ritas upp över previousScoresList
        previousScoreSign = new JLabel();
        previousScoreSign.setVisible(false);
        previousScoreSign.setForeground(Color.WHITE);
        previousScoreSign.setFont(new Font("Arial", Font.BOLD, Const.STATUS_FONT_SIZE));
        previousScoreSign.setBounds(Const.PREVIOUS_SCORES_SIGN_POSX, Const.PREVIOUS_SCORES_SIGN_POSY, Const.STATUS_WIDTH, Const.STATUS_HEIGHT);
        add(previousScoreSign);

        highScoresModel = new HighScoresModel(new DefaultListModel<String>(), this);
        highScoresList = new JList<>(highScoresModel);
        JScrollPane highScoresScrollPane = new JScrollPane(highScoresList);
        highScoresScrollPane.setBounds(Const.HIGHSCORES_POSX, Const.HIGHSCORES_POSY, Const.HIGHSCORES_WIDTH, Const.HIGHSCORES_HEIGHT);
        highScoresScrollPane.setVisible(false);
        add(highScoresScrollPane);

        // Lista för poäng från de tre senaste sessionerna
        previousScoresModel = new PreviousScoresModel(new DefaultListModel<String>(), this);
        previousScoresList = new JList<>(previousScoresModel);
        JScrollPane previousScoresScrollPane = new JScrollPane(previousScoresList);
        previousScoresScrollPane.setBounds(Const.PREVIOUS_SCORES_POSX, Const.PREVIOUS_SCORES_POSY, Const.PREVIOUS_SCORES_WIDTH, Const.PREVIOUS_SCORES_HEIGHT);
        previousScoresScrollPane.setVisible(false);
        add(previousScoresScrollPane);

        // Inmatning av initialer
        initialsField = new JTextField();
        initialsField.setBounds(Const.INITIAL_FIELD_POSX, Const.INITIAL_FIELD_POSY, Const.INITIAL_FIELD_WIDTH, Const.INITIAL_FIELD_HEIGHT);
        initialsField.setVisible(false);
        initialsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String initials = initialsField.getText().toUpperCase();
                if (initials.length() == Const.MAX_INITIAL_LENGTH && initials.matches("[A-Z]+")) {
                    nameEntered = true;
                    initialsField.setVisible(false);
                    highScoresModel.saveHighScoreToFile("highscores.txt", game.getScore(), initials);
                    previousScoresModel.savePreviousScoresToFile("previousruns.txt", game.getScore());
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR: Endast 3 bokstäver");
                }
            }
        });
        add(initialsField);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        Graphics2D graphics = (Graphics2D) arg0;
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        game.draw(graphics);

        // Visar en JField-ruta vid game over.
        if (game.isGameOver() && !nameEntered) {
            initialsField.setVisible(true);
            nameEntered = true;
        }

        // Visar upp JListorna och texten över dem när initialerna har matas in.
        if (nameEntered) {
            highScoresModel.displayHighScores();
            previousScoresModel.displayPreviousScores();
            displayGameOverScreen(graphics);
            highscoreSign.setVisible(true);
            previousScoreSign.setVisible(true);
        }
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED)
            keyboard.processKeyEvent(e.getKeyCode(), true);
        else if (e.getID() == KeyEvent.KEY_RELEASED)
            keyboard.processKeyEvent(e.getKeyCode(), false);
    }

    public void start() {
        while (true) {
            game.update(keyboard);
            try {
                Thread.sleep(Const.THROTTLE_THREAD / FPS); //Throttle thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }

    // Ritar upp en lämplig text och poäng vid game over.
    private void displayGameOverScreen(Graphics graphics) {
        if (game.isGameOver()) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.BOLD, Const.GAME_OVER_FONTSIZE));
            graphics.drawString("Game Over", Const.GAME_OVER_POS, Const.GAME_OVER_POS);
            graphics.drawString("Your score: " + game.getScore(), Const.GAME_OVER_POS, Const.GAME_OVER_SCORE_POS);
        }
    }

    // Ritar upp och uppdaterar texten beroende på status
    public void updateStatus(int score, int lives) {
        scoreSign.setText("Score: " + score);
        livesSign.setText("Lives " + lives);
        highscoreSign.setText("High scores:");
        previousScoreSign.setText("Previous rounds:");
    }

    public JList<String> getHighScoresList() {
        return highScoresList;
    }

    public JList<String> getPreviousScoresList() {
        return previousScoresList;
    }
}

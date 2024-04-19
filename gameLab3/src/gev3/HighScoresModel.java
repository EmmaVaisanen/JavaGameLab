package gev3;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HighScoresModel extends DefaultListModel<String> {
    private DefaultListModel<String> highScoresModel;
    private JList<String> highScoresList;
    private GameBoard gameBoard;
    public HighScoresModel(DefaultListModel<String> highScoresModel, GameBoard gameBoard){
        this.highScoresModel = highScoresModel;
        this.gameBoard = gameBoard;
        this.highScoresList = gameBoard.getHighScoresList();
        readHighScoresFromFile("highscores.txt");

    }
    public void saveHighScoreToFile(String filename, int score, String initials) {
        List<String> highScores = readHighScoresFromFile(filename);
        highScores.add(initials + ": " + score);
        Collections.sort(highScores, (s1, s2) -> {
            int score1 = Integer.parseInt(s1.split(":")[1].trim());
            int score2 = Integer.parseInt(s2.split(":")[1].trim());
            return Integer.compare(score2, score1);
        });
        if (highScores.size() > Const.MAX_HIGHSCORES) {
            highScores = highScores.subList(0, Const.MAX_HIGHSCORES);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String s : highScores) {
                writer.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metod för att rita upp högsta poängen från txt-filen
    public void displayHighScores() {
        List<String> highScores = readHighScoresFromFile("highscores.txt");
        if (highScores != null && !highScores.isEmpty()) {
            Collections.sort(highScores, (s1, s2) -> {
                int score1 = Integer.parseInt(s1.split(":")[1].trim());
                int score2 = Integer.parseInt(s2.split(":")[1].trim());
                return Integer.compare(score2, score1);
            });
            String[] highScoresArray = new String[Math.min(highScores.size(), Const.MAX_HIGHSCORES)];
            for (int i = 0; i < highScoresArray.length; i++) {
                highScoresArray[i] = highScores.get(i);
            }
            while (highScoresArray.length < Const.MAX_HIGHSCORES) {
                highScoresArray = Arrays.copyOf(highScoresArray, highScoresArray.length + 1);
                highScoresArray[highScoresArray.length - 1] = "...";
            }
            highScoresModel.clear();
            for (String score : highScoresArray) {
                highScoresModel.addElement(score);
            }
            JScrollPane scrollPane = (JScrollPane) gameBoard.getHighScoresList().getParent().getParent();
            scrollPane.setVisible(true);

            gameBoard.getHighScoresList().setModel(highScoresModel);
        }
    }

    // Läser av "highscores.txt". Skapar en ny fil om filen saknas.
    public List<String> readHighScoresFromFile(String filename) {
        List<String> highScores = new ArrayList<>();
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("...")) {
                    highScores.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }
}

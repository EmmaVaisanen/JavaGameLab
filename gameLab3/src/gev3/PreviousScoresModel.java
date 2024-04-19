package gev3;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PreviousScoresModel extends DefaultListModel<String> {
    private DefaultListModel<String> previousScoresModel;
    private JList<String> previousScoresList;
    private GameBoard gameBoard;
    public PreviousScoresModel(DefaultListModel<String> previousScoresModel, GameBoard gameBoard){
        this.previousScoresModel = previousScoresModel;
        this.gameBoard = gameBoard;
        this.previousScoresList = gameBoard.getPreviousScoresList();
        readPreviousScoresFromFile("previousruns.txt");
    }
    public void savePreviousScoresToFile(String filename, int score) {
        List<Integer> scores = readPreviousScoresFromFile(filename);
        scores.add(score);
        while (scores.size() > Const.MAX_PREVIOUS_SCORES) {
            scores.remove(0);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int s : scores) {
                writer.println(s);
            }

            if (scores.size() < Const.MAX_PREVIOUS_SCORES) {
                for (int i = 0; i < Const.MAX_PREVIOUS_SCORES - scores.size(); i++) {
                    writer.println("...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Läser av "previousruns.txt" och skapar en ny txt fil om filen saknas.
    private List<Integer> readPreviousScoresFromFile(String filename) {
        List<Integer> scores = new ArrayList<>();
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("...")) {
                    scores.add(Integer.parseInt(line));
                }
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return scores;
    }

    // Metod för att rita upp poängen från de senaste tre sessionerna utifrån txt-filen
    public void displayPreviousScores() {
        List<Integer> previousScores = readPreviousScoresFromFile("previousruns.txt");
        if (previousScores != null && !previousScores.isEmpty()) {
            Collections.reverse(previousScores);
            String[] previousScoresArray = new String[Math.min(previousScores.size(), Const.MAX_PREVIOUS_SCORES)];
            for (int i = 0; i < previousScoresArray.length; i++) {
                previousScoresArray[i] = String.valueOf(previousScores.get(i));
            }
            while (previousScoresArray.length < Const.MAX_PREVIOUS_SCORES) {
                previousScoresArray = Arrays.copyOf(previousScoresArray, previousScoresArray.length + 1);
                previousScoresArray[previousScoresArray.length - 1] = "...";
            }
            previousScoresModel.clear();
            for (String score : previousScoresArray) {
                previousScoresModel.addElement(score);
            }
            JScrollPane scrollPane = (JScrollPane) gameBoard.getPreviousScoresList().getParent().getParent();
            scrollPane.setVisible(true);

            gameBoard.getPreviousScoresList().setModel(previousScoresModel);
        }
    }
}

package com.sphy.game.manager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {

    private static final String SCORES_FILE = "scores.dat";

    public static class Score implements Serializable {
        private String name;
        private int score;
        private LocalDate localDate;

        public Score(String playerName, int score, LocalDate localDate) {
            this.name = playerName;
            this.score = score;
            this.localDate = localDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }
    }

    public static List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCORES_FILE))) {
            scores = (List<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public static void saveScore(Score score) {
        List<Score> scores = loadScores();
        scores.add(score);
        Collections.sort(scores, Comparator.comparingInt(Score::getScore).reversed());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


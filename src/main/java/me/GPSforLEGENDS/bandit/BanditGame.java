package me.GPSforLEGENDS.bandit;

import java.util.concurrent.ThreadLocalRandom;

public class BanditGame {

    private final int BANDIT_AMOUNT = BanditUtil.BANDIT_AMOUNT;
    private final int MAX_ROLL_TRIES = BanditUtil.MAX_ROLL_TRIES;
    private int tries = 0;
    private double totalScore = 0;
    private final Bandit[] bandits = new Bandit[BANDIT_AMOUNT];
    private final int perfectPlace = ThreadLocalRandom.current().nextInt(10);
    //private final int perfectPlace = 5;

    public BanditGame(){
        for(int i = 0; i < BanditUtil.BANDIT_AMOUNT; i++){
            bandits[i] = new Bandit(0);
        }
        bandits[perfectPlace] = new Bandit(1);
    }

    public boolean isGameOver(){
        return tries >= MAX_ROLL_TRIES;
    }

    public double rollBandit(int nr){
        tries++;
        double score = bandits[nr].reward();
        totalScore += score;
        return score;
    }

    public GameState resetGame(){
        tries = 0;
        totalScore = 0;

        for(int i = 0; i < BanditUtil.BANDIT_AMOUNT; i++){
            bandits[i] = new Bandit(0);
        }
        bandits[perfectPlace] = new Bandit(1);

        return buildStateObservation();
    }

    public double getAverageScore(){
        return totalScore/tries;
    }

    public int getTries(){
        return tries;
    }

    public GameState buildStateObservation(){
        double[] scores = new double[BanditUtil.BANDIT_AMOUNT*2];

        for(int i = 0; i < BanditUtil.BANDIT_AMOUNT; i++){
            scores[i] = bandits[i].getAverageScore();
        }

        for(int i = BanditUtil.BANDIT_AMOUNT; i < BanditUtil.BANDIT_AMOUNT*2; i++){
            scores[i] = bandits[i - BANDIT_AMOUNT].getTotalRolls();
        }

        return new GameState(scores);
    }

    public double getTotalScore() {
        return totalScore;
    }
}

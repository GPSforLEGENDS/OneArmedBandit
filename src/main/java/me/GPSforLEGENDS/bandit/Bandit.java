package me.GPSforLEGENDS.bandit;

import java.util.concurrent.ThreadLocalRandom;

public class Bandit {

    private final double mult;
    private double totalScore = 0;
    private int totalRolls = 0;

    public Bandit(){
        mult = ThreadLocalRandom.current().nextDouble();
    }

    public Bandit(double mult){
        this.mult = mult;
    }

    public double reward(){
        double score = (ThreadLocalRandom.current().nextInt(100) + 1) * mult;
        totalScore += score;
        totalRolls++;
        return score;
    }

    public double getAverageScore(){
        if(totalRolls == 0){
            return 0;
        }
        return totalScore / totalRolls;
    }
}

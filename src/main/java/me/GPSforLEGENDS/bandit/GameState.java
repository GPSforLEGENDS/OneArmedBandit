package me.GPSforLEGENDS.bandit;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class GameState implements Encodable {

    private final double[] scoreInputs;

    public GameState(double[] scoreInputs) {
        this.scoreInputs = scoreInputs;
    }

    @Override
    public double[] toArray() {
        return scoreInputs;
    }

    @Override
    public boolean isSkipped() {
        return false;
    }

    @Override
    public INDArray getData() {
        return Nd4j.create(scoreInputs);
    }

    public INDArray getMatrix() {
        return Nd4j.create(new double[][] {
                scoreInputs
        });
    }

    @Override
    public Encodable dup() {
        return null;
    }
}

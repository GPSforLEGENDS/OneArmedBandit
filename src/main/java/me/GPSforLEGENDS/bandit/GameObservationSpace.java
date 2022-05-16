package me.GPSforLEGENDS.bandit;

import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class GameObservationSpace implements ObservationSpace<GameState> {

    private static final double[] LOWS = GameObservationSpace.createValueArray(0);
    private static final double[] HIGHS = GameObservationSpace.createValueArray(100);

    @Override
    public String getName() {
        return "GameObservationSpace";
    }

    @Override
    public int[] getShape() {
        return new int[] {
                1, BanditUtil.BANDIT_AMOUNT
        };
    }

    @Override
    public INDArray getLow() {
        return Nd4j.create(LOWS);
    }

    @Override
    public INDArray getHigh() {
        return Nd4j.create(HIGHS);
    }

    private static double[] createValueArray(final double value) {
        final double[] values = new double[BanditUtil.BANDIT_AMOUNT];
        for (int i = 0; i < BanditUtil.BANDIT_AMOUNT; i++) {
            values[i] = value;
        }

        return values;
    }
}

package me.GPSforLEGENDS.bandit;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.learning.configuration.QLearningConfiguration;
import org.deeplearning4j.rl4j.network.configuration.DQNDenseNetworkConfiguration;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.nd4j.linalg.learning.config.Nadam;
import org.nd4j.linalg.learning.config.RmsProp;

import java.io.File;
import java.io.IOException;

public class NetworkUtil {

    public static QLearningConfiguration buildConfig() {
        return QLearningConfiguration.builder()
                .seed(123L)
                .maxEpochStep(200)
                .maxStep(15000)
                .expRepMaxSize(150000)
                .targetDqnUpdateFreq(200)
                .batchSize(128)
                .updateStart(0)
                .rewardFactor(1)
                .gamma(0.999)
                .errorClamp(1.0)
                .minEpsilon(0.001f)
                .epsilonNbStep(1000)
                .doubleDQN(true)
                .build();
    }

    public static DQNFactoryStdDense buildDQNFactory() {
        final DQNDenseNetworkConfiguration build = DQNDenseNetworkConfiguration.builder()
                .updater(new RmsProp(0.00025))
                .numHiddenNodes(500)
                .numLayers(1)
                .build();

        return new DQNFactoryStdDense(build);
    }

    public static MultiLayerNetwork loadNetwork(final String networkName) {
        try {
            return MultiLayerNetwork.load(new File(networkName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

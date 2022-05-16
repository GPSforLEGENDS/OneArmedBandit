package me.GPSforLEGENDS.bandit;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class BanditRL4J {


    public static void main(String[] args) {
        BanditGame game = new BanditGame();
        final String randomNetworkName = "network-" + System.currentTimeMillis() + ".zip";

        train(game, randomNetworkName);

        // Evaluate just trained network
        evaluateNetwork(game, randomNetworkName);
    }

    private static void train(BanditGame game, String randomNetworkName){

        final Environment mdp = new Environment(game);
        final QLearningDiscreteDense<GameState> dql = new QLearningDiscreteDense<>(
                mdp,
                NetworkUtil.buildDQNFactory(),
                NetworkUtil.buildConfig()
        );

        dql.train();
        mdp.close();

        // Save network
        try {
            dql.getNeuralNet().save(randomNetworkName);
        } catch (IOException e) {
        }
        // Reset the game
        game.resetGame();
    }

    private static void evaluateNetwork(BanditGame game, String randomNetworkName) {
        final MultiLayerNetwork multiLayerNetwork = NetworkUtil.loadNetwork(randomNetworkName);
        double highscore = 0;
        for (int i = 0; i < 1000; i++) {
            double score = 0;
            while (!game.isGameOver()) {
                try {
                    final GameState state = game.buildStateObservation();
                    final INDArray output = multiLayerNetwork.output(state.getMatrix(), false);
                    double[] data = output.data().asDouble();
                    int maxValueIndex = getMaxValueIndex(data);

                    game.rollBandit(maxValueIndex);
                    score = game.getTotalScore();
                } catch (final Exception e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Score of iteration " + i + " was " + score);
            System.out.println("Average Score " + game.getAverageScore());
            if (score > highscore) {
                highscore = score;
            }

            // Reset the game
            game.resetGame();
        }
        System.out.println("Finished. Highest Score was " + highscore);
    }

    private static int getMaxValueIndex(final double[] values) {
        int maxAt = 0;

        for (int i = 0; i < values.length; i++) {
            maxAt = values[i] > values[maxAt] ? i : maxAt;
        }

        return maxAt;
    }

    private void testRandom(){
        BanditGame game = new BanditGame();

        while(!game.isGameOver()){
            game.rollBandit(ThreadLocalRandom.current().nextInt(BanditUtil.BANDIT_AMOUNT));
        }

        System.out.println("Durchschnitt: " + game.getAverageScore());
        System.out.println("Versuche: " + game.getTries());
    }
}

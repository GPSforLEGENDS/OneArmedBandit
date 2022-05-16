package me.GPSforLEGENDS.bandit;

import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

public class Environment implements MDP<GameState, Integer, DiscreteSpace> {

    //Bandits to choose from
    private final DiscreteSpace actionSpace = new DiscreteSpace(BanditUtil.BANDIT_AMOUNT);
    private final BanditGame game;

    public Environment(BanditGame game){
        this.game = game;
    }

    @Override
    public ObservationSpace<GameState> getObservationSpace() {
        return new GameObservationSpace();
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public GameState reset() {
        return game.resetGame();
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<GameState> step(Integer integer) {

        double reward = game.rollBandit(integer);

        GameState observation = game.buildStateObservation();

        return new StepReply<>(
                observation,
                reward,
                isDone(),
                "BanditDl4j"
        );
    }

    @Override
    public boolean isDone() {
        return game.isGameOver();
    }

    @Override
    public MDP<GameState, Integer, DiscreteSpace> newInstance() {
        game.resetGame();
        return new Environment(game);
    }
}

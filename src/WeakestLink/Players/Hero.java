package WeakestLink.Players;

import WeakestLink.Game.Game;
import WeakestLink.Game.Vote;

import java.util.*;

/**
 * Created by thenumberone on 12/2/15.
 * @author thenumberone
 */
public class Hero extends Player{

    @Override
    public int vote(Set<Integer> currentOpponents) {
        int me = getSmartness();
        Set<Vote> history = getVotingHistory();
        history.removeIf(vote -> !currentOpponents.contains(vote.getVoter()) || vote.getVoter() == me);
        int[] evilnessLevel = new int[Game.NUMBER_PLAYERS_PER_ROUND+1];
        for (Vote vote : history){
            evilnessLevel[vote.getVoter()] += vote.getVoted() == me ? 1_000_000 : Game.NUMBER_PLAYERS_PER_ROUND - vote.getVoted();
        }
        int mostEvilOpponent = -1;
        for (int opponent : currentOpponents){
            if (mostEvilOpponent == -1 || evilnessLevel[opponent] > evilnessLevel[mostEvilOpponent]){
                mostEvilOpponent = opponent;
            }
        }
        return mostEvilOpponent;
    }
}
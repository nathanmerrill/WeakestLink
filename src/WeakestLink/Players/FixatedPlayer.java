package WeakestLink.Players;

import WeakestLink.Game.Vote;

import java.util.*;

public class FixatedPlayer extends Player{
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int self = getSmartness();
        Vote previous_vote = getLastVote();
        if (previous_vote == null || !currentOpponents.contains(previous_vote.getVoted())){
            return (int) currentOpponents.toArray()[getRandom().nextInt(currentOpponents.size())];
        }
        else {
            return previous_vote.getVoted();
        }
    }
}
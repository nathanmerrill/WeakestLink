package WeakestLink.Players;

import WeakestLink.Game.Vote;
import java.util.Map;
import java.util.Set;

/**
 * Votes for the currently most voted bot in the game. Or the lowest one.
 */
public class Bandwagon
        extends Player {

    @Override
    public int vote(Set<Integer> currentOpponents) {
        int self = getSmartness(), vote = -1;
        java.util.Map<Integer, Integer> votes = new java.util.TreeMap<>();
        getVotingHistory().stream().map((Vote v)-> v.getVoted()).filter((Integer i)-> !i.equals(self)).forEach((Integer tgt)-> {
            if(!votes.containsKey(tgt)) {
                votes.put(tgt, 1);
            } else {
                votes.put(tgt, votes.get(tgt) + 1);
            }
        });

        do {
            if(votes.entrySet().isEmpty()) {
                vote = currentOpponents.stream().filter((Integer i)-> !i.equals(self)).sorted().findFirst().get();
            } else {
                if(votes.containsKey(vote)) {
                    votes.remove(vote);
                    vote = -1;
                }

                for(Map.Entry<Integer, Integer> vv: votes.entrySet()) {
                    Integer key = vv.getKey();
                    Integer value = vv.getValue();

                    if((vote == -1) || (value > votes.get(vote))) {
                        vote = key;
                    }
                }
            }
        } while(!currentOpponents.contains(vote));

        return vote;
    }
}
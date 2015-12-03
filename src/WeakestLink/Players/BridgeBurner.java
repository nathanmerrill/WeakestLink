package WeakestLink.Players;

import WeakestLink.Game.Vote;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BridgeBurner extends Player{
    @Override
    public int vote(Set<Integer> currentOpponents) {
        List<Integer> votes_against = Stream.generate(() -> 0).limit(9).collect(Collectors.toList());
        List<Integer> last_voted_against = Stream.generate(() -> 0).limit(9).collect(Collectors.toList());
        Iterator<Vote> votes_against_me = getVotesForSelf().iterator();

        for (int c = 0; c < 9; c++){
            if (!currentOpponents.contains(c)){
                votes_against.set(c,-1);
                last_voted_against.set(c,-1);
            }
        }

        while(votes_against_me.hasNext()){
            Vote vote = votes_against_me.next();

            int voter = vote.getVoter();
            int round = vote.getRound();

            if (currentOpponents.contains(voter)){
                votes_against.set(voter, votes_against.get(voter)+1);
                last_voted_against.set(voter, Math.max(round, last_voted_against.get(voter)));
            } else {
                votes_against.set(voter, -1);
                last_voted_against.set(voter, -1);
            }
        }

        int min_tally = Collections.max(votes_against);
        for (int c = 0; c < 9; c++){
            int current_tally = votes_against.get(c);
            if (current_tally != -1 && current_tally < min_tally){
                min_tally = current_tally;
            }
        }

        if (Collections.frequency(votes_against, min_tally) == 1){
            return votes_against.indexOf(min_tally);
        } else {
            List<Integer> temp_last_against = new ArrayList<>();
            for (int c = 0; c < 9; c++){
                if (votes_against.get(c) == min_tally){
                    temp_last_against.add(last_voted_against.get(c));
                }
            }
            return last_voted_against.lastIndexOf(Collections.min(temp_last_against));
        }
    }
}
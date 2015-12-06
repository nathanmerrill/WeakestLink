package WeakestLink.Players;
import WeakestLink.Game.Vote;
import java.util.Iterator;
import java.util.Set;

public class Coward extends Player {
    @Override
    public int vote(Set<Integer> currentOpponents) {

        boolean[] currentOpponent = new boolean[10];

        Iterator<Integer> opps = currentOpponents.iterator();
        while(opps.hasNext()){
            currentOpponent[opps.next().intValue()] = true;
        }

        int[] voteCounts = new int[10];
        for(int i=1; i<10; i++) {
            voteCounts[i] = 0;
        }

        Iterator<Vote> votes = getRecentVotes().iterator();

        while(votes.hasNext()){
            Vote opp_vote = votes.next();
            if(currentOpponent[opp_vote.getVoter()])
                voteCounts[opp_vote.getVoted()] += 1;
            else
                voteCounts[opp_vote.getVoter()] += 100;
        }

        int previous_weakest = -1;
        int max_votes_gotten = 0;
        for(int i=0;i<9;i++){
            if (voteCounts[i] > max_votes_gotten) {
                max_votes_gotten = voteCounts[i];
                previous_weakest = i;
            }
        }
        int min_closeness = 10;
        int to_vote = -1;
        int opp;
        int closeness;
        opps = currentOpponents.iterator();
        while(opps.hasNext()){
            opp = opps.next();
            closeness = Math.abs(opp - previous_weakest);
            if(closeness <= min_closeness) {
                to_vote = opp;
                min_closeness = closeness;
            }
        }

        return to_vote;

    }
}
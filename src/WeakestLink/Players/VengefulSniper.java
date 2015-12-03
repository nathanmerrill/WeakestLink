package WeakestLink.Players;

import java.util.*;

import WeakestLink.Game.Vote;

public class VengefulSniper extends Player{
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int me = getSmartness();
        int smartOpp = Collections.max(currentOpponents);
        int dumbOpp = Collections.min(currentOpponents);
        int votesAgainstSmart=0, votesAgainstDumb=0;
        Boolean targetedBySmart = false, targetedByDumb = false;

        Set<Vote> votesForMe = getRecentVotes();
        Iterator<Vote> votes = votesForMe.iterator();
        while(votes.hasNext()){
            Vote vote = votes.next();
            int voter = vote.getVoter();
            int voted = vote.getVoted();

            if(voted == me){
                if(voter == smartOpp){
                    targetedBySmart = true;
                }
                if(voter == dumbOpp){
                    targetedByDumb = true;
                }
            } else if (voted == smartOpp){
                votesAgainstSmart++;
            } else if (voted == dumbOpp){
                votesAgainstDumb++;
            }
        }

        // If being targeted by smartest or dumbest, take them out
        // Try to go with the rest of the crowd if they both targeted me
        if(targetedBySmart ^ targetedByDumb){
            return targetedBySmart ? smartOpp : dumbOpp;
        } else if (targetedBySmart && targetedByDumb){
            if (votesAgainstSmart > votesAgainstDumb){
                return smartOpp;
            } else if (votesAgainstDumb > votesAgainstSmart){
                return dumbOpp;
            }
        }

        Iterator<Integer> opps = currentOpponents.iterator();
        int cnt_stpd=0;
        while(opps.hasNext()){
            int opp_smrt = opps.next().intValue();
            if(opp_smrt < me){
                cnt_stpd++;
            }
        }

        if (cnt_stpd < 2 || (currentOpponents.size() < 4)){ //buffer is small, protect myself
            return smartOpp;
        } else {
            return dumbOpp;
        }
    }
}
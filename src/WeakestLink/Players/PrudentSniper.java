package WeakestLink.Players;
import WeakestLink.Game.Vote;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
public class PrudentSniper extends Player {
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int smrt = getSmartness();

        //count number of players smarter/stupider than me, find max/min
        Iterator<Integer> opps = currentOpponents.iterator();
        int cnt_smrt=0, cnt_stpd=0, opp_smrt, min_stpd=10, max_smrt=0;

        while(opps.hasNext()){
            opp_smrt = opps.next().intValue();
            if(opp_smrt > max_smrt) max_smrt = opp_smrt;
            if(opp_smrt < min_stpd) min_stpd = opp_smrt;
            if(opp_smrt > smrt){
                cnt_smrt++;
            }
            else if(opp_smrt < smrt){
                cnt_stpd++;
            }
        }

        //identify enemies
        Iterator<Vote> votes = getRecentVotes().iterator();
        boolean[] voted_for_me = new boolean[9];

        while(votes.hasNext()) {
            Vote opp_vote = votes.next();
            voted_for_me[opp_vote.getVoter()] = (opp_vote.getVoted() == getSmartness() ||
                    (opp_vote.getVoted() < getSmartness() && cnt_stpd < 1) ||
                    (opp_vote.getVoted() > getSmartness() && cnt_smrt < 1));
        }

        if (currentOpponents.size() < 3 || cnt_stpd < 2 || (voted_for_me[max_smrt] && !voted_for_me[min_stpd] && cnt_smrt > 0) )
            return max_smrt;
        else
            return min_stpd;
    }
}
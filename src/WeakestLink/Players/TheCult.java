package WeakestLink.Players;
import WeakestLink.Game.Game;
import WeakestLink.Game.Vote;
import java.util.Iterator;
import java.util.Set;

public class TheCult extends Player {
    //The cult has a secret code 2-8.
    private static final int secret_code = Game.random.nextInt(7)+2;
    private int cult_vote;
    private boolean[] isMember = null;
    @Override
    public int vote(Set<Integer> currentOpponents) {
        //on first turn, vote the code
        if(isMember == null){
            isMember = new boolean[10];
            for(int i=9; i--!=0;) isMember[i]=true;
            cult_vote = secret_code;
            return cult_vote;
        }
        //on all other turn, assess who is not voting with the cult
        Vote opp_vote;
        Iterator<Vote> votes = getRecentVotes().iterator();
        while(votes.hasNext()){
            opp_vote = votes.next();
            if(opp_vote.getVoted() != cult_vote)
                isMember[opp_vote.getVoter()] = false;
        }
        //vote off the weakest non-member, then weakest members
        Iterator<Integer> opps = currentOpponents.iterator();
        int opp_smrt, min_mem=10, min_non=10;
        while(opps.hasNext()){
            opp_smrt = opps.next().intValue();
            if(isMember[opp_smrt]){
                if(opp_smrt < min_mem) min_mem = opp_smrt;
            }else{
                if(opp_smrt < min_non) min_non = opp_smrt;
            }
        }
        if(min_non!=10) cult_vote = min_non;
        else            cult_vote = min_mem;
        return cult_vote;
    }
}
package WeakestLink.Players;
import WeakestLink.Game.Vote;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class SniperKiller extends Player {
    boolean[] sniperish;
    int[] sniperwouldvote;

    @Override
    public int vote(Set<Integer> currentOpponents) {
        int smrt = getSmartness();
        currentOpponents.add(smrt);
        if(sniperish==null){
            sniperish = new boolean[10];
            sniperwouldvote = new int[10];
            for(int i=10;--i!=0;){
                sniperish[i] = true;
                sniperwouldvote[i] = 1;
            }
            sniperish[smrt]=false; //knows we are not the sniper
            return 1;
        }
        //figure out who isn't a sniper
        Vote opp_vote;
        int opp_smrt;
        Iterator<Vote> votes = getRecentVotes().iterator();
        while(votes.hasNext()){
            opp_vote = votes.next();
            opp_smrt = opp_vote.getVoter();
            if(opp_vote.getVoted() != sniperwouldvote[opp_smrt])
                sniperish[opp_smrt] = false;
        }
        //figure out how snipers would vote this round
        Iterator<Integer> opps = currentOpponents.iterator();
        int cnt_opp=0, min_opp=10, max_opp=0;
        int[] snpr_votes = new int[10];
        while(opps.hasNext()){
            opp_smrt = opps.next().intValue();
            if(smrt == opp_smrt) continue;
            sniperwouldvote[opp_smrt] = hypothetically(opp_smrt, currentOpponents);
            cnt_opp++;
            if(sniperish[opp_smrt]){
                snpr_votes[sniperwouldvote[opp_smrt]]++;
            }
            if(opp_smrt<min_opp) min_opp=opp_smrt;
            if(opp_smrt>max_opp) max_opp=opp_smrt;
        }
        for(int i = 1;i<10;i++){//hit the weakest sniper.
            if(sniperish[i] && currentOpponents.contains(i))
                return i;
        }
        return hypothetically(smrt, currentOpponents);
    }

    private int hypothetically(int smrt, Set<Integer> currentOpponents) {
        Iterator<Integer> opps = currentOpponents.iterator();
        int cnt_smrt=0, cnt_stpd=0, opp_smrt, min_stpd=10, max_smrt=0;
        while(opps.hasNext()){
            opp_smrt = opps.next().intValue();
            if(opp_smrt > smrt){
                cnt_smrt++;
                if(opp_smrt > max_smrt) max_smrt = opp_smrt;
            }
            else if(opp_smrt < smrt){
                cnt_stpd++;
                if(opp_smrt < min_stpd) min_stpd = opp_smrt;
            }
        }
        if(cnt_stpd>1) return min_stpd;
        return max_smrt;
    }
}
package WeakestLink.Players;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
public class Sniper extends Player {
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int smrt = getSmartness();

        //count number of players smarter/stupider than me
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

        //remove low-value, then dangerous players
        if(cnt_stpd>1)
            return min_stpd;
        else
            return max_smrt;
    }
}
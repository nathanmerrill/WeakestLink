package WeakestLink.Players;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class MiddleMan extends Player {
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int smrt = getSmartness();

        //count number of players smarter/stupider than me
        Iterator<Integer> opps = currentOpponents.iterator();
        int cnt_smrt=0, cnt_stpd=0, opp_smrt, min_stpd=9, min_smrt=9;

        while(opps.hasNext()){
            opp_smrt = opps.next().intValue();
            if(opp_smrt > smrt){
                cnt_smrt++;
                if(opp_smrt < min_smrt) min_smrt = opp_smrt;
            }
            else if(opp_smrt < smrt){
                cnt_stpd++;
                if(opp_smrt < min_stpd) min_stpd = opp_smrt;
            }
        }

        //Keep myself in the middle of the pack, favoring the point earners
        if(cnt_stpd>cnt_smrt)
            return min_stpd;
        else
            return min_smrt;
    }
}
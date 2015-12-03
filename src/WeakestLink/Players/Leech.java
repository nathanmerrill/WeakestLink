package WeakestLink.Players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Leech extends Player {
    /**
     * Copyrighted (not really, use this however you want friends) by Sweerpotato :~)!
     */
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int mySmartness = getSmartness();

        ArrayList<Integer> opponentSmartness = new ArrayList<Integer>();
        opponentSmartness.addAll(currentOpponents);
        opponentSmartness.add(mySmartness);
        Collections.sort(opponentSmartness);

        if(mySmartness > 4 && mySmartness > Collections.min(opponentSmartness)) {
            //There's somebody dumber than me, vote that dude off
            return opponentSmartness.get(opponentSmartness.indexOf(mySmartness) - 1);
        }
        else {
            //Vote off the smartest guy, so we have a better chance to win
            if(mySmartness == Collections.max(opponentSmartness)) {
                //Apparently, we're the smartest guy
                return opponentSmartness.get(opponentSmartness.indexOf(mySmartness) - 1);
            }
            else {
                return Collections.max(opponentSmartness);
            }
        }
    }
}
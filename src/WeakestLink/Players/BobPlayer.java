package WeakestLink.Players;

import java.util.Collections;
import java.util.Set;

import WeakestLink.Game.Vote;

public class BobPlayer extends Player {


    @Override
    public int vote(Set<Integer> currentOpponents) {
        int smartness;

        // Bob sometimes thinks he is smarter than he really is
        if (getRandom().nextInt(10) == 0) {
            smartness = 10;
        } else {
            smartness = getSmartness();
        }

        // If there is still some competition
        if (currentOpponents.size() > 3) {
            // And Bob is the dumbest
            if (smartness < Collections.min(currentOpponents)) {
                // Go for the smartest one
                return Collections.max(currentOpponents);
                // But if he is the smartest
            } else if (smartness > Collections.max(currentOpponents)) {
                // Go for the weak link
                return Collections.min(currentOpponents);
            } else {
                // Else revenge!
                for (Vote v : getRecentVotes()) {
                    if (v.getVoted() == smartness && currentOpponents.contains(v.getVoter())) {
                        return v.getVoter();
                    }
                }
            }
            return Collections.min(currentOpponents);
        } else {
            //If there are few opponents just revenge!
            for (Vote v : getRecentVotes()) {
                if (v.getVoted() == smartness && currentOpponents.contains(v.getVoter())) {
                    return v.getVoter();
                }
            }
            return Collections.max(currentOpponents);
        }
    }



}
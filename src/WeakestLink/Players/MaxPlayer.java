package WeakestLink.Players;

import java.util.Collections;
import java.util.Set;

public class MaxPlayer extends Player{

    @Override
    public int vote(Set<Integer> currentOpponents) {
        return Collections.max(currentOpponents);
    }
}

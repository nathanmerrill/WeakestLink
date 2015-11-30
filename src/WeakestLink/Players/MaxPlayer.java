package WeakestLink.Players;

import java.util.Collections;
import java.util.List;

public class MaxPlayer extends Player{
    @Override
    public int vote(List<Integer> currentOpponents) {
        return Collections.max(currentOpponents);
    }
}

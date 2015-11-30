package WeakestLink.Players;

import java.util.Collections;
import java.util.List;

public class MinPlayer extends Player {

    @Override
    public int vote(List<Integer> currentOpponents) {
        return Collections.min(currentOpponents);
    }
}

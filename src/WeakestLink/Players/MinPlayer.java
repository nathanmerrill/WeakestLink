package WeakestLink.Players;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MinPlayer extends Player {

    @Override
    public int vote(List<Integer> currentPlayers, Map<Integer, Integer> historicalVotes) {
        return Collections.min(currentPlayers);
    }

    @Override
    public String getName() {
        return "Minimum Player";
    }
}

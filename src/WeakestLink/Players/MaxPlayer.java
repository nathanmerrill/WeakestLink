package WeakestLink.Players;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MaxPlayer extends Player{
    @Override
    public int vote(List<Integer> currentPlayers, Map<Integer, Integer> historicalVotes) {
        return Collections.max(currentPlayers);
    }
}

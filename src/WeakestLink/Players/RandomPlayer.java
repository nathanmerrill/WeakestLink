package WeakestLink.Players;

import java.util.List;
import java.util.Map;

public class RandomPlayer extends Player{

    @Override
    public int vote(List<Integer> currentPlayers, Map<Integer, Integer> historicalVotes) {
        return currentPlayers.get(getRandom().nextInt(currentPlayers.size()));
    }

    @Override
    public String getName() {
        return "Random Player";
    }
}

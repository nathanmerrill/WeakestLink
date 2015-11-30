package WeakestLink.Players;

import java.util.List;

public class RandomPlayer extends Player{

    @Override
    public int vote(List<Integer> currentOpponents) {
        return currentOpponents.get(getRandom().nextInt(currentOpponents.size()));
    }
}

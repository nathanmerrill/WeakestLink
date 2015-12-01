package WeakestLink.Players;

import java.util.Set;

public class RandomPlayer extends Player{

    @Override
    public int vote(Set<Integer> currentOpponents) {
        return (int) currentOpponents.toArray()[getRandom().nextInt(currentOpponents.size())];
    }
}

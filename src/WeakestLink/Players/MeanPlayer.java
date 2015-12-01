package WeakestLink.Players;

import java.util.Arrays;
import java.util.Set;

public class MeanPlayer extends Player{

    @Override
    public int vote(Set<Integer> currentOpponents) {
        int mid = currentOpponents.size() / 2;
        Object[] opponents = currentOpponents.toArray();
        Arrays.sort(opponents);
        return (int) opponents[mid];
    }
}

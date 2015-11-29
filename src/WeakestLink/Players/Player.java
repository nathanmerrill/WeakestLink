package WeakestLink.Players;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Player {
    private boolean initialized;
    private Random random;
    public Player(){
        initialized = false;
    }

    protected Random getRandom(){
        return random;
    }

    public void initialize(Random random){
        this.random = random;
    }

    public abstract int vote(List<Integer> currentPlayers, Map<Integer, Integer> historicalVotes);
}

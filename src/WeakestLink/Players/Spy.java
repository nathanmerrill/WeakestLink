package WeakestLink.Players;

import java.util.Iterator;
import java.util.Set;

public class Spy extends Player{
    @Override
    public int vote(Set<Integer> currentOpponents) {
        int selfIntel = getSmartness();
        int closestIntel = 100; // default
        // get closest player
        Iterator<Integer> enemies = currentOpponents.iterator();
        while(enemies.hasNext()){
            int enemyIntel = enemies.next().intValue();
            if(Math.abs(enemyIntel - selfIntel) < closestIntel) closestIntel = enemyIntel;
        }
        return closestIntel;
    }
}
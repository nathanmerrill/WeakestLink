package WeakestLink.Players;

import java.util.Collections;
import java.util.Set;

public class HighOrLowNotSelf extends Player{
    @Override
    public int vote(Set<Integer> ops) {
        int b= (int) Math.round(Math.random()*1);
        return (b==1)? Collections.max(ops) : Collections.min(ops);
    }
}
package WeakestLink.Players;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import WeakestLink.Game.Vote;
public class RevengePlayer extends Player{

    @Override
    public int vote(Set<Integer> opponents) {
        int[] A;
        A = new int[10];
        for(int i = 1;i < 9;i++)
            A[i] = opponents.contains(i)? i+1 : 0;
        Set<Vote> H = getVotingHistory();
        Iterator<Vote> I = H.iterator();
        while(I.hasNext()){
            Vote v = I.next();
            if(v.getVoted() == getSmartness())
                A[v.getVoter()] += A[v.getVoter()] != 0?10:0;
        }
        int maxI = 0;
        for(int i = 1;i < 10;i++)
            if(A[i] > A[maxI])
                maxI = i;
        return maxI;
    }
}
package WeakestLink.Players;

import WeakestLink.Game.Vote;

import java.util.LinkedList;
import java.util.Set;

public class Anarchist extends Player {

    LinkedList<Integer> opponents;

    @Override
    public int vote(Set<Integer> currentOpponents) {
        opponents = new LinkedList();
        opponents.addAll(currentOpponents);
        opponents.sort(Integer::compare);

        int me = getSmartness();

        if (getPresident() != me) {
            return getPresident();
        } else {
            // treason ?
            Vote voteForMe = getRecentVotes().stream().filter(v -> v.getVoted() == me).findFirst().orElse(null);
            if (voteForMe == null) {
                // No treason ! Hurray. Kill the peagants.
                return getPeagant();
            } else {
                // TREASON!
                return opponents.get(opponents.indexOf(voteForMe.getVoter()));
            }
        }
    }

    private int getPresident() {
        return opponents.getLast();
    }

    private int getPeagant() {
        return opponents.getFirst();
    }

}

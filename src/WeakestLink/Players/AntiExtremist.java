package WeakestLink.Players;
import java.util.Arrays;
import java.util.Set;

public class AntiExtremist extends Player {

    Object[] currentPlayers;

    @Override
    public int vote(Set<Integer> currentOpponents) {

        currentPlayers = (Object[]) currentOpponents.toArray();
        Arrays.sort(currentPlayers);

        int smartness = getSmartness();
        int turns = getTurnNumber();

        //// Lets get an idea of who's smart and who's dumb ////

        int smarter = 0, dumber = 0;

        int max_smart = 0, min_smart = 10;

        currentOpponents.toArray();

        for (int i = 0; i < currentPlayers.length; i++) {
            int osmart = (int)currentPlayers[i];

            if (osmart == smartness)
                continue;

            if (osmart > smartness) {
                smarter++;

                if (osmart > max_smart)
                    max_smart = osmart;
            }
            else if (osmart < smartness) {
                dumber++;

                if (osmart < min_smart)
                    min_smart = osmart;
            }

        }

        // int total = smarter+dumber;

        double smarter_ratio = smarter > 0 ? (max_smart-smartness)/4.5 : 0;
        double dumber_ratio = dumber > 0 ? (smartness-min_smart)/3.0 : 0;//Favor dumber

        smarter_ratio*=.25+(turns/9.0*.75);
        dumber_ratio*=1-(turns/8.0*.75);

        return smarter_ratio > dumber_ratio ? max_smart : min_smart;

    }

}
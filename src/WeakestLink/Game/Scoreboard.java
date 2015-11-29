package WeakestLink.Game;

import WeakestLink.Players.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard {
    private final Map<Class<? extends Player>, Integer> scores;

    public Scoreboard(){
        scores = new HashMap<>();
    }

    public void addScore(Class<? extends Player> player, int score){
        scores.merge(player, score, (a, b) -> a+b);
    }

    public List<Class<? extends Player>> topPlayers(){
        return scores.entrySet().stream()
                .sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    public int scoreFor(Class<? extends Player> player){
        return scores.get(player);
    }
}

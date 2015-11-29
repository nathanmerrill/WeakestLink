package WeakestLink.Game;

import WeakestLink.Players.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard {
    private final Map<String, Integer> scores;
    private final Map<String, Player> playerNames;

    public Scoreboard(){
        scores = new HashMap<>();
        playerNames = new HashMap<>();
    }

    public void addScore(Player player, int score){
        playerNames.put(player.getName(), player);
        scores.merge(player.getName(), score, (a, b) -> a+b);
    }

    public List<Player> topPlayers(){
        return scores.entrySet().stream()
                .sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue()))
                .map(Map.Entry::getKey)
                .map(playerNames::get)
                .collect(Collectors.toList());

    }

    public int scoreFor(Player player){
        return scores.get(player.getName());
    }
}

package WeakestLink.Game;

import WeakestLink.Players.Player;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Round {
    public final static int FACE_OFF_THRESHOLD = 2;
    private final Map<Player, Integer> playerToSmartness;
    private final Map<Integer, Player> smartnessToPlayer;
    private final Set<Player> currentPlayers;
    private final Map<Player, Map<Player, Integer>> votes;
    private final Game parent;

    private boolean ran;
    private int pot;
    public Round(List<Player> players, Game parent){
        pot = 0;
        currentPlayers = new HashSet<>(players);
        playerToSmartness =
                IntStream.range(0, currentPlayers.size())
                .boxed()
                .collect(Collectors.toMap(players::get, Function.identity()));
        smartnessToPlayer =
                IntStream.range(0, currentPlayers.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), players::get));
        votes = new HashMap<>();
        players.forEach(player -> votes.put(player, new HashMap<>()));
        ran = false;
        this.parent = parent;
    }
    public Player findWinner(){
        if (ran){
            throw new RuntimeException("Round already ran");
        } else {
            ran = true;
        }
        while (currentPlayers.size() > FACE_OFF_THRESHOLD){
            addToPot();
            voteOff();
        }
        return faceOff();
    }

    public int getPot(){
        return pot;
    }

    private void addToPot(){
        pot += currentPlayers.stream().mapToInt(playerToSmartness::get).sum();
    }

    private Player faceOff(){
        int totalIntelligence =  currentPlayers.stream().mapToInt(playerToSmartness::get).sum();
        int remainingIntelligence = parent.getRandom().nextInt(totalIntelligence);
        for (Player player: currentPlayers){
            remainingIntelligence -= playerToSmartness.get(player);
            if (remainingIntelligence <= 0){
                return player;
            }
        }
        throw new AssertionError("Player not found");

    }

    private void voteOff(){
        Map<Player, Player> currentVotes =
                currentPlayers.stream()
                        .collect(Collectors.toMap(Function.identity(), this::vote));

        for (Map.Entry<Player, Player> vote: currentVotes.entrySet()){
            votes.get(vote.getKey()).merge(vote.getValue(), 1, (a, b) -> a+b);
        }

        Player votedOff =  currentVotes.values().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(this::compareVoteCounts).get().getKey();

        currentPlayers.remove(votedOff);

    }

    private int compareVoteCounts(Map.Entry<Player, Long> a, Map.Entry<Player, Long> b){
        if (a.getValue().equals(b.getValue())){
            return playerToSmartness.get(a.getKey()).compareTo(playerToSmartness.get(b.getKey()));
        }
        return a.getValue().compareTo(b.getValue());
    }



    private Player vote(Player player){
        Map<Integer, Integer> historicalVotes =
                votes.get(player).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> playerToSmartness.get(e.getKey()),
                        Map.Entry::getValue
                ));
        List<Integer> players = currentPlayers.stream().map(playerToSmartness::get).collect(Collectors.toList());
        return smartnessToPlayer.get(player.vote(players, historicalVotes));
    }

}

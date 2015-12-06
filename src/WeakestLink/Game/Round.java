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
    private final Set<Vote> votes;
    private final Game parent;

    private boolean ran;
    private int pot;
    private int currentTurn;

    public Round(List<Player> players, Game parent){
        pot = 0;
        currentPlayers = new HashSet<>(players);
        playerToSmartness =
                IntStream.range(0, currentPlayers.size())
                .boxed()
                .collect(Collectors.toMap(players::get, i->i+1));
        smartnessToPlayer =
                IntStream.range(0, currentPlayers.size())
                .boxed()
                .collect(Collectors.toMap(i->i+1, players::get));
        currentPlayers.forEach(player -> player.setSmartness(playerToSmartness.get(player)));
        votes = new HashSet<>();
        ran = false;
        currentTurn = 0;
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
            currentTurn++;
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
        Set<Vote> currentVotes =
                currentPlayers.stream()
                        .map(this::vote)
                        .collect(Collectors.toSet());
        votes.addAll(currentVotes);

        Integer votedOff =  currentVotes.stream()
                .collect(Collectors.groupingBy(Vote::getVoted, Collectors.counting()))
                .entrySet().stream()
                .max(this::compareVoteCounts).get().getKey();

        currentPlayers.remove(smartnessToPlayer.get(votedOff));
    }

    private int compareVoteCounts(Map.Entry<Integer, Long> a, Map.Entry<Integer, Long> b){
        if (a.getValue().equals(b.getValue())){
            return a.getKey().compareTo(b.getKey());
        }
        return a.getValue().compareTo(b.getValue());
    }



    private Vote vote(Player player){
        player.setVotingHistory(new HashSet<>(votes));
        player.setTurnNumber(currentTurn);
        player.setPot(pot);
        Set<Integer> players = currentPlayers.stream()
                .filter(p -> p != player)
                .map(playerToSmartness::get)
                .collect(Collectors.toSet());
        int vote = player.vote(players);
        if (!currentPlayers.contains(smartnessToPlayer.get(vote))){
            throw new RuntimeException(player.getClass().getSimpleName()+" voted off non-existent player");
        }
        return new Vote(playerToSmartness.get(player), vote, currentTurn);
    }

}

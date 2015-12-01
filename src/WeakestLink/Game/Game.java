package WeakestLink.Game;

import WeakestLink.Players.Player;

import java.util.*;

public class Game {
    public final static int MINIMUM_NUMBER_OF_ROUNDS = 10000;
    public final static int NUMBER_PLAYERS_PER_ROUND = 9;
    public final static Random random = new Random();

    private final List<Class<? extends Player>> unusedPlayers;
    private final Set<Class<? extends Player>> allPlayers;
    private final int numRounds;
    private final Scoreboard scoreboard;

    private boolean finished;
    public Game(){
        allPlayers = new HashSet<>(PlayerFactory.getAllPlayerTypes());
        numRounds = Math.max(MINIMUM_NUMBER_OF_ROUNDS*allPlayers.size()/NUMBER_PLAYERS_PER_ROUND + 1,
                MINIMUM_NUMBER_OF_ROUNDS);
        unusedPlayers = new ArrayList<>(allPlayers);
        scoreboard = new Scoreboard();
        finished = false;
    }

    public Random getRandom(){
        return random;
    }


    public void run(){
        if (finished) {
            throw new RuntimeException("Game already ran");
        }
        for (int i = 0; i < numRounds; i++){
            List<Player> players = generateNextPlayers();
            Collections.shuffle(players, random);
            Round round = new Round(players, this);
            Player winner = round.findWinner();
            int score = round.getPot();
            scoreboard.addScore(winner.getClass(), score);
        }
        finished = true;
    }

    public void printScores(){
        if (!finished){
            throw new RuntimeException("Game not run yet");
        }
        for (Class<? extends Player> player: scoreboard.topPlayers()){
            System.out.println(scoreboard.scoreFor(player)+"\t"+player.getName());
        }
    }



    private List<Player> generateNextPlayers(){
        List<Player> players = new ArrayList<>();
        while (players.size() < NUMBER_PLAYERS_PER_ROUND){
            int playerDeficient = NUMBER_PLAYERS_PER_ROUND - players.size();
            List<Class<? extends Player>> toUse;
            if (unusedPlayers.size() <= playerDeficient){
                toUse = unusedPlayers;
            } else {
                Collections.shuffle(unusedPlayers, random);
                toUse = unusedPlayers.subList(0, playerDeficient);
            }
            players.addAll(PlayerFactory.createAndInitialize(toUse, random));
            unusedPlayers.removeAll(toUse);
            if (unusedPlayers.size() == 0){
                unusedPlayers.addAll(allPlayers);
            }
        }
        Collections.shuffle(players, random);
        return players;
    }


}

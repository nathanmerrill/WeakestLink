package WeakestLink.Game;

import WeakestLink.Players.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class PlayerFactory {
    public static HashMap<Class<? extends Player>, Supplier<? extends Player>> playerCreators = new HashMap<>();
    static {
        playerCreators.put(RandomPlayer.class, RandomPlayer::new);
        playerCreators.put(MaxPlayer.class, MaxPlayer::new);
        playerCreators.put(MinPlayer.class, MinPlayer::new);

    }

    public static Collection<Class<? extends Player>> getAllPlayerTypes(){
        return playerCreators.keySet();
    }

    public static Player createPlayer(Class<? extends Player> playerType){
        return playerCreators.get(playerType).get();
    }

    public static List<Player> createPlayers(Collection<Class<? extends Player>> playerTypes){
        return playerTypes.stream()
                .map(PlayerFactory::createPlayer)
                .collect(Collectors.toList());
    }

    public static List<Player> createAndInitialize(Collection<Class<? extends Player>> playerTypes, Random random){
        List<Player> players = createPlayers(playerTypes);
        players.forEach(player -> player.initialize(random));
        return players;
    }

}

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
        playerCreators.put(MeanPlayer.class, MeanPlayer::new);
        playerCreators.put(Sniper.class, Sniper::new);
        playerCreators.put(MedianPlayer.class, MedianPlayer::new);
        playerCreators.put(TheCult.class, TheCult::new);
        playerCreators.put(AntiExtremist.class, AntiExtremist::new);
        //playerCreators.put(ApproximatePosition.class, ApproximatePosition::new);
        playerCreators.put(Coward.class, Coward::new);
        //playerCreators.put(BridgeBurner.class, BridgeBurner::new);
        playerCreators.put(FixatedPlayer.class, FixatedPlayer::new);
        playerCreators.put(Guard.class, Guard::new);
        playerCreators.put(Hero.class, Hero::new);
        playerCreators.put(Leech.class, Leech::new);
        playerCreators.put(PrudentSniper.class, PrudentSniper::new);
        playerCreators.put(RevengePlayer.class, RevengePlayer::new);
        playerCreators.put(SniperAide.class, SniperAide::new);
        playerCreators.put(SniperKiller.class, SniperKiller::new);
        playerCreators.put(BobPlayer.class, BobPlayer::new);
        playerCreators.put(Bandwagon.class, Bandwagon::new);
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
        players.forEach(player -> player.setRandom(random));
        return players;
    }

}

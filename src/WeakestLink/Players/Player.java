package WeakestLink.Players;

import WeakestLink.Game.Vote;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Player {
    private Random random;
    private Set<Vote> votingHistory;
    private int turnNumber;
    private int smartness;

    private boolean recentVoteFilter(Vote vote){
        return vote.getRound() == turnNumber - 1;
    }

    private boolean votedForSelfFilter(Vote vote){
        return vote.getVoted() == smartness;
    }

    private boolean selfVotedFilter(Vote vote){
        return vote.getVoter() == smartness;
    }

    protected Random getRandom(){
        return random;
    }

    public void setRandom(Random random){
        this.random = random;
    }

    public void setVotingHistory(Set<Vote> votingHistory){
        this.votingHistory = votingHistory;
    }

    protected Set<Vote> getVotingHistory() {
        return votingHistory;
    }

    public void setTurnNumber(int turnNumber){
        this.turnNumber = turnNumber;
    }

    public void setSmartness(int smartness){
        this.smartness = smartness;
    }

    protected int getSmartness(){
        return smartness;
    }

    protected int getTurnNumber(){
        return turnNumber;
    }

    protected Set<Vote> getRecentVotes(){
        return votingHistory.stream().filter(this::recentVoteFilter).collect(Collectors.toSet());
    }

    protected Set<Vote> getVotesForSelf(){
        return votingHistory.stream().filter(this::votedForSelfFilter).collect(Collectors.toSet());
    }

    protected Set<Vote> getRecentVotesForSelf(){
        return votingHistory.stream()
                .filter(this::votedForSelfFilter)
                .filter(this::recentVoteFilter)
                .collect(Collectors.toSet());
    }

    protected List<Vote> getSelfVotingHistory(){
        return votingHistory.stream()
                .filter(this::selfVotedFilter)
                .sorted((v1, v2) -> v1.getRound() - v2.getRound())
                .collect(Collectors.toList());
    }

    protected Vote getLastVote(){
        return votingHistory.stream()
                .filter(this::selfVotedFilter)
                .filter(this::recentVoteFilter)
                .collect(Collectors.toList()).get(0);
    }

    public abstract int vote(List<Integer> currentOpponents);
}

package WeakestLink.Game;

public class Vote {
    private int voter;
    private int voted;
    private int round;
    public Vote(int voter, int voted, int round){
        this.voter = voter;
        this.voted = voted;
        this.round = round;
    }

    public int getVoted() {
        return voted;
    }

    public int getRound() {
        return round;
    }

    public int getVoter() {
        return voter;
    }
}

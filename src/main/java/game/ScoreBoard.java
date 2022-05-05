package game;

public class ScoreBoard {

    PlayerInterface[] players;

    public ScoreBoard(PlayerInterface[] players) {
        this.players = players;
    }

    public void updateScoreBoard(int score, int trickWinner) {

        players[trickWinner].setScore(players[trickWinner].getScore()+score);
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < 4; i++) {
            ret += "Player " + i + "has " + (players[i].getScore()) + "\n";
        }
        return ret;
    }
}

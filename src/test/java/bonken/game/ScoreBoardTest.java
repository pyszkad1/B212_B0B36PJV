package bonken.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    private final PlayerInterface[] players = {new PlayerBot(0, Position.North), new PlayerBot(1, Position.East),
                                                new PlayerBot(2, Position.South), new PlayerBot(3, Position.West)};
    private final int trickWinner = 1;
    private final int score = 10;
    private final ScoreBoard scoreBoard = new ScoreBoard(players);

    @Test
    void updateScoreBoardTest() {
        players[1].setScore(30);
        scoreBoard.updateScoreBoard(score, trickWinner);
        assertEquals(40, players[trickWinner].getScore());
    }
}

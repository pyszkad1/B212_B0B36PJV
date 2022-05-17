package bonken.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class RoundTest {

    private final Game game = null;
    private final Deck deck = new Deck();
    private final ArrayList<Integer> minigames = new ArrayList<>(List.of(8, 9));
    private final PlayerInterface[] players = {new PlayerBot(0, Position.North), new PlayerBot(1, Position.East), new PlayerBot(2, Position.South), new PlayerBot(3, Position.West)};
    private final Round round = new Round(game, deck, minigames, players);

    @Test
    void getStartingPlayerTest() {
        players[2].setHisTurn(true);
        round.getStartingPlayer();
        int roundStarter = round.startingPlayer;
        assertEquals(2, roundStarter);
    }

    @Test
    void getNextPlayerTest() {
        players[1].setHisTurn(true);
        round.getNextPlayer();
        int nextPlayer = 0;
        for (PlayerInterface player : players) {
            if (player.isHisTurn()) nextPlayer = player.getId();
        }
        assertEquals(2, nextPlayer);
    }

}

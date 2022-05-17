package bonken.net;

/**
 * Enum class for Protocol codes used in net communication.
 */
public enum Protocol {
    // server origin
    SUBMIT,
    ACCEPTED,
    REJECTED,
    GAME_STARTED,
    POSSIBLE_MINIGAMES,
    TRICK_AND_HAND,
    TRICK_END,
    ROUND,
    SCORE,
    GAME_ENDED,

    // client origin
    USERNAME,
    MYPOS,
    MINIGAME,
    CARD,
    QUIT
}

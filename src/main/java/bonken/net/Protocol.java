package bonken.net;

public enum Protocol {
    // server origin
    SUBMIT,
    ACCEPTED,
    REJECTED,
    GAME_STARTED,
    POSSIBLE_MINIGAMES,
    TRICK_AND_HAND,
    CARD_HAND,

    // client origin
    USERNAME,
    MYPOS,
    MINIGAME,
    CARD,
    QUIT
}

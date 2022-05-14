package bonken.net;

public enum Protocol {
    // server origin
    SUBMIT,
    ACCEPTED,
    REJECTED,
    GAME_STARTED,
    POSSIBLE_MINIGAMES,
    TRICK,
    CARD_HAND,
    PLAYABLE_CARDS,

    // client origin
    USERNAME,
    MYPOS,
    MINIGAME,
    CARD,
    QUIT
}

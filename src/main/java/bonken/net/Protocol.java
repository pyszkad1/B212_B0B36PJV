package bonken.net;

public enum Protocol {
    // server origin
    SUBMIT,
    ACCEPTED,
    REJECTED,
    POSSIBLE_MINIGAMES,
    TRICK,
    CARD_HAND,
    PLAYABLE_CARDS,

    // client origin
    USERNAME,
    MINIGAME,
    CARD,
    QUIT
}

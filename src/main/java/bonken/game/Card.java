package bonken.game;

/**
 *
 * General class for Card object.
 *
 */
public class Card {
    private int suit;
    private int rank;
    private String image;

    //suit definitions
    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    //ranks definitions
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    public static final String[] png = new String[]
            {"2c.png", "3c.png", "4c.png", "5c.png", "6c.png", "7c.png", "8c.png", "9c.png", "10c.png", "jc.png", "qc.png", "kc.png", "ac.png",
                    "2d.png", "3d.png","4d.png","5d.png","6d.png","7d.png","8d.png","9d.png","10d.png","jd.png","qd.png","kd.png","ad.png",
                    "2h.png","3h.png","4h.png","5h.png","6h.png","7h.png","8h.png","9h.png","10h.png","jh.png","qh.png","kh.png","ah.png",
                    "2s.png","3s.png","4s.png","5s.png","6s.png","7s.png","8s.png","9s.png","10s.png","js.png","qs.png","ks.png","as.png"};

    public final String[] suitNames = new String[]
            {"Clubs", "Diamonds", "Hearts", "Spades"};

    public final String[] faceNames = new String[]
            {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    /**
     *
     * @param rank number from 2 to 14
     * @param suit number from 0 to 3 (-1 is used when lowest card possible needed)
     */
    public Card(int rank, int suit) {
        if (suit == -1) {
        } else if ((suit < 0) || (suit > 3) || (rank < 2) || (rank > 14)) {
            throw new IllegalArgumentException();
        }
        this.suit = suit;
        this.rank = rank;
        if (suit != -1) {
            image = png[suit * 13 + rank - 2];
        }
    }

    public String getImage() {
        return image;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return faceNames[rank - 2] + " of " + suitNames[suit];
    }

}


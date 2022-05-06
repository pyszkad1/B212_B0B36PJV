package game;

public class MiniGameTrumps {
    int trumps;
    int minigameNum;
    public final String[] trumpNames = new String[]
            {"Clubs ♣", "Diamonds ♦", "Hearts ♥", "Spades ♠", "No Trumps NT"};

    public MiniGameTrumps(int minigameNum) {
        this.minigameNum = minigameNum;
        setTrumps();
    }

    private void setTrumps(){
        switch (minigameNum) {
            case 11:
                trumps = 4;
                break;
            case 10:
                trumps = 3;
                break;
            case 9:
                trumps = 2;
                break;
            case 8:
                trumps = 1;
                break;
            case 7:
                trumps = 0;
                break;
            default:
                trumps = -1;
                break;
        }
    }


}


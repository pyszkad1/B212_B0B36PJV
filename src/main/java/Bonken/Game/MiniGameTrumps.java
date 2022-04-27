package Bonken.Game;

public class MiniGameTrumps {
    int trumps;
    int minigameNum;
    public final String[] trumpNames = new String[]
            {"Clubs", "Diamonds", "Hearts", "Spades", "No Trumps"};

    public MiniGameTrumps(int minigameNum) {
        this.minigameNum = minigameNum;
        setTrumps();
    }

    private void setTrumps(){
        switch (minigameNum) {
            case 11 -> trumps = 4;
            case 10 -> trumps = 3;
            case 9 -> trumps = 2;
            case 8 -> trumps = 1;
            case 7 -> trumps = 0;
            default -> trumps = -1;
        }
    }


}


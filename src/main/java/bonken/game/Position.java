package bonken.game;

import javafx.geometry.Pos;

public enum Position {

    North (0),
    East (1),
    South (2),
    West (3);

    public final int index;
    private static int positionCount = 4;

    Position (int index) {
        this.index = index;
    }

    public Position next() {

        switch (this)   {
            case North: return East;
            case East: return South;
            case South: return West;
            case West: return North;
            default: return null;
        }
    }

    public Position previous() {
        return next(positionCount - 1);
    }

    public Position next(int count) {
        Position pos = this;
        for (int i = 0; i < count; i ++) {
            pos = pos.next();
        }
        return pos;
    }

}

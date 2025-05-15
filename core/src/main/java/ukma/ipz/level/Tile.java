package ukma.ipz.level;

import ukma.ipz.Action;

public class Tile {
    int x, y;
    boolean occupied;
    Action action;
    Action interaction;
    public Tile(int x, int y, boolean occupied) {
        this.x = x;
        this.y = y;
        this.occupied = occupied;
    }

    public Tile(int x, int y, boolean occupied, Action action, Action interaction) {
        this(x, y, occupied);
        this.action = action;
        this.interaction = interaction;
    }
}

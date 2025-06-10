package ukma.ipz.fight;

import ukma.ipz.Action;

public class StatusEffect {
    int movesLeft;
    boolean recent=true;
    Action entryAction;
    Action exitAction;
    StatusEffect(int movesLeft, Action entryAction, Action exitAction) {
        this.movesLeft = movesLeft;
        this.entryAction = entryAction;
        this.exitAction = exitAction;
    }
    StatusEffect(Action entryAction, Action exitAction) {
        movesLeft = 10000;
        this.entryAction = entryAction;
        this.exitAction = exitAction;
    }
}

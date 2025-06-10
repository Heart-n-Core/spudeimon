package ukma.ipz.fight;

import java.util.ArrayList;
import java.util.List;

public class Fighter {
    String name;
    Fighter enemy;
    int hp, atck;
    Types type;
    List<StatusEffect>effects;
    Move[]moveSet;
    int OP = 4;
    int nextMove=4;

    @Deprecated
    public Fighter(int hp, int atck, Types type) {
        effects = new ArrayList<>();
        this.hp = hp;
        this.atck = atck;
        this.type = type;
        moveSet = Move.getMoveset(this);
    }
    public Fighter(String name, int lvl, Types type) {
        effects = new ArrayList<>();
        this.hp = 46+4*lvl;
        this.atck = 7+3*lvl;
        this.type = type;
        moveSet = Move.getMoveset(this);
        this.name = name;
    }

    void processEffects(){
        ArrayList<StatusEffect> toRemove = new ArrayList<>();
        for (StatusEffect effect : effects) {
            if (effect.recent){effect.entryAction.execute();}
            if (effect.movesLeft>0){effect.movesLeft--;}
            if(effect.movesLeft<=0){
                effect.exitAction.execute();
                toRemove.add(effect);
            }
        }
        effects.removeAll(toRemove);
    }
}

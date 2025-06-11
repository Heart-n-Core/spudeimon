package ukma.ipz.fight;

import ukma.ipz.Action;

public class Move {
    Fighter fighter;
    String name;
    String description="";
    int OPCost;
//    List<String> messages = new ArrayList<>();
    String[]messages = new String[4];
    int animationDelayMs =1;

    static void dealDamage(float moveCoof, Fighter fighter) {
//        fighter.enemy.hp-= (int) (moveCoof*fighter.atck*Types.attackEfficiency(fighter.type, fighter.enemy.type));
        fighter.enemy.hp-=(moveCoof*fighter.atck*Types.attackEfficiency(fighter.type, fighter.enemy.type));

    }
    Action moveAction;

    Move(Fighter fighter, String name, int OPCost) {
        this.fighter = fighter;
        this.name = name;
        this.OPCost = OPCost;
//        this.moveAction = moveAction;
    }

    static Move[] getMoveset(Fighter fighter) {
        float litleDamage = 0.75f;
        float mediumDamage = 1.25f;
        float bigDamage = 1.75f;
        int disableMove = -1;
        int freeToChooseMove = 4;
        Move[] typeMoves = new Move[3];
        switch (fighter.type){
            case FI:{
                Move sqlInjection = new Move(fighter, "SQL ін'єкція", 1);
                sqlInjection.description="Швидка і підступна атака, що\r\nзавдає невеликої, але гаранто-\r\nваної шкоди, обходячи базові\r\nзахисні механізми супротивника.";
//                sqlInjection.messages.add("S");sqlInjection.messages.add("Q");sqlInjection.messages.add("L");
                sqlInjection.messages = new String[]{"S","Q","L",null};
                sqlInjection.moveAction = () -> {
//                    dealDamage(litleDamage, fighter);
                    fighter.enemy.hp-= (int) (0.75*fighter.enemy.atck);
                };
                Move DDDosAttack = new Move(fighter, "DDoS атака", 3);

                DDDosAttack.moveAction = () -> {
                    dealDamage(mediumDamage, fighter);
                    fighter.enemy.nextMove=-1;
//                    fighter.enemy.effects.add(new StatusEffect(1, () -> {fighter.enemy.nextMove=disableMove;}, () -> {fighter.enemy.nextMove=freeToChooseMove;}));
                };
                Move RootAcess = new Move(fighter, "Root доступ", 5);
                RootAcess.moveAction = () -> {
                    dealDamage(bigDamage, fighter);
                };
                typeMoves[0] = sqlInjection;typeMoves[1]=DDDosAttack;typeMoves[2]=RootAcess;
                break;
            }
            case FHN:{

                break;
            }
        }
        Move rest = new Move(fighter, "Відпочинок", 0);
        rest.moveAction = () -> {fighter.OP+=2;};
        return new Move[]{typeMoves[0], typeMoves[1], typeMoves[2], rest};
    }

    static Move getSkipMove(Fighter fighter) {
        Move skip = new Move(fighter, "Пропуск", 0);
        skip.moveAction = () -> {fighter.nextMove=4;};
        return skip;
    }
}

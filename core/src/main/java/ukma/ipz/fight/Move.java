package ukma.ipz.fight;

import ukma.ipz.Action;

import java.util.ArrayList;

public class Move {
    Fighter fighter;
    String name;
    String description="";
    int OPCost;
//    List<String> messages = new ArrayList<>();
    String[]messages = new String[3];
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
                sqlInjection.description="Швидка і підступна атака, що\r\nзавдає невеликої, але гаранто-\r\nваної шкоди, обходячи базові\r\nзахисні механізми супротивника.\r\n1 ОП";
//                sqlInjection.messages.add("S");sqlInjection.messages.add("Q");sqlInjection.messages.add("L");
                sqlInjection.messages = new String[]{"S","Q","L"};
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
                DDDosAttack.description="Інформатик перевантажує\r\nсистему супротивника\r\nзапитами, завдаючи\r\nсередньої шкоди і\r\nзмушуючи його\r\nпропустити свій\r\nнаступний хід для «відновлення».\r\n3 ОП";
                Move RootAcess = new Move(fighter, "Root доступ", 5);
                RootAcess.moveAction = () -> {
                    dealDamage(bigDamage, fighter);
                };
                RootAcess.description="Абсолютна атака.\r\nІнформатик отримує повний\r\nконтроль над системою\r\nопонента, завдаючи\r\nвеличезної шкоди та\r\nотримуючи контроль над\r\nнаступним ходом ворога.\r\n5 OП";
                typeMoves[0] = sqlInjection;typeMoves[1]=DDDosAttack;typeMoves[2]=RootAcess;
                break;
            }
            case FHN:{
                Move ryth = new Move(fighter, "Риторичне питання", 2);
                ryth.description = "Атака, що завдає\r\nмінімальної шкоди, але\r\nзмушує опонента\r\nсумніватися у своїх\r\nсилах, тимчасово\r\nзнижуючи його/її показник\r\nатаки на 25%\r\n2 ОП";
                ryth.moveAction = () -> {
                    dealDamage(litleDamage, fighter);
                    fighter.enemy.atck*=0.75f;
                    fighter.enemy.effects.add(new StatusEffect(3, ()->{}, ()->{fighter.enemy.atck*=1.33f;}));
                };
                Move deco = new Move(fighter, "Деконструкція", 3);
                deco.description = "Гуманітарій розбирає\r\nаргументи супротивника на\r\nчастини, завдаючи\r\nсередньої шкоди та\r\nзнімаючи з нього всі\r\nпозитивні ефекти.\r\n3 ОП";
                deco.moveAction = () -> {
                    dealDamage(mediumDamage, fighter);
                    ArrayList<StatusEffect> toRemove = new ArrayList<>();
                    for (StatusEffect ef :fighter.enemy.effects ){
                        if (ef.good)toRemove.add(ef);
                    }
                    fighter.enemy.effects.removeAll(toRemove);
                };
                Move cult = new Move(fighter, "Культурний reset", 5);
                cult.description = "Потужна атака, що\r\nзмінює сам «наратив»\r\nбитви. Завдає великої\r\nшкоди і робить опонента\r\nвразливим до атак будь-якого\r\nтипу на наступні два ходи\r\n(отримує на 20% більше шкоди).\r\n5 ОП";
                cult.moveAction = () -> {
                    dealDamage(bigDamage, fighter);
                    fighter.atck*=1.2f;
                    fighter.effects.add(new StatusEffect(2, () -> {}, () -> {fighter.atck*=0.83f;}));
                };
                typeMoves[0]=ryth;typeMoves[1]=deco;typeMoves[2]=cult;
                break;
            }
        }
        Move rest = new Move(fighter, "Відпочинок", 0);
        rest.description="Пропустити хід,\r\nщоб відновити 2 ОП";
        rest.moveAction = () -> {fighter.OP+=2;};
        return new Move[]{typeMoves[0], typeMoves[1], typeMoves[2], rest};
    }

    static Move getSkipMove(Fighter fighter) {
        Move skip = new Move(fighter, "Пропуск", 0);
        skip.moveAction = () -> {fighter.nextMove=4;};
        return skip;
    }
}

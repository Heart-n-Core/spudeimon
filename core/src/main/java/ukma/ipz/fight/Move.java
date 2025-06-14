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
            case FPVN:{
                Move obj = new Move(fighter, "Objection!", 1);
                obj.description="Швидка реакція,\r\nщо блокує наступну атаку\r\nсупротивника, якщо\r\nїї вартість становить 1 ОП.\r\nЯкщо атака сильніша,\r\nзменшує її шкоду на 50%.\r\n1 ОП";
                obj.moveAction = ()->{dealDamage(litleDamage, fighter);};
                Move jud = new Move(fighter, "Судовий позов", 3);
                jud.description="Атака середньої сили,\r\nщо змушує супротивника\r\n'витрачатись на адвокатів'.\r\nЗавдає шкоди і\r\nдодатково віднімає\r\n1 ОП у опонента.\r\n3 ОП";
                jud.messages = new String[]{"Ціль втрачає 1 ОП!",null,null};
                jud.moveAction=()->{dealDamage(mediumDamage, fighter);if (fighter.enemy.OP>0)fighter.enemy.OP--;};
                Move verd = new Move(fighter, "Остаточний вердикт",7);
                verd.description="Нищівна атака, від\r\nякої неможливо ухилитися\r\nчи заблокувати (при\r\nумові що у опонента\r\nменше ніж 50% ОП).\r\nСимволізує остаточне рішення \r\nсуду, що не підлягає оскарженню.\r\n7 ОП";
                verd.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=obj;typeMoves[1]=jud;typeMoves[2]=verd;
                break;
            }
            case FSNST: {
                Move poll = new Move(fighter, "CоцІаЛьНе ОпитУвАннЯ", 1);
                poll.description = "Завдає дуже малої шкоди, але\nдозволяє дізнатися,яку\nатаку супротивник планує\nвикористати в наступному ході.\n1 ОП";
                poll.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move meeting = new Move(fighter, "Скликаю міт", 3);
                meeting.description = "Соціолог мобілізує громадську\nдумку.Несподівана атака,\nщо завдає середньої шкоди\r\nі тимчасово знижує захист\nсупротивника на 50%.\n3 ОП";
                meeting.moveAction = ()->{dealDamage(mediumDamage, fighter); fighter.atck*=0.5f;};
                meeting.messages = new String[]{"Ціль втратила половину сили атаки",null,null};

                Move socialRevolution = new Move(fighter, "РЕВОЛЮЦІЯ!", 5);
                socialRevolution.description = "Масштабна атака, що змінює\nправила гри.Завдає\nвеликої шкоди всім на полі\nбою (включно з собою, але 50%)\nі знімає всі статусні\nефекти.\n5 ОП";
                socialRevolution.moveAction = ()->{dealDamage(bigDamage, fighter);};

                typeMoves[0]=poll;typeMoves[1]=meeting;typeMoves[2]=socialRevolution;
                break;
            }
            case FPRN: {
                Move takeGuess = new Move(fighter, "Висуваю гіпотезу", 1);
                takeGuess.description = "Базова атака з невеликою\nшкодою, але має підвищений\nшанс на критичний удар,\nякщо \"гіпотеза виявиться\nправильною\".\n1 ОП";
                takeGuess.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move expertOpinion = new Move(fighter, "Експертна оцінка", 2);
                expertOpinion.description = "Атака завдає середньої шкоди та\nанулює будь-які спроби опонента\nпідвищити свої характеристики \nабо захист(атаки що також \nдають баф, не дають баф проте \nвсе ще роблять шкоду).\n2 ОП";
                expertOpinion.moveAction = ()->{dealDamage(mediumDamage, fighter);};

                Move natureGift = new Move(fighter, "Закон природи", 5);
                natureGift.description = "Атака, що базується на \nнепорушних законах Всесвіту.\r\nЗавдає величезної шкоди,\nігноруючи захист супротивника.\n5 ОП";
                natureGift.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=takeGuess;typeMoves[1]=expertOpinion;typeMoves[2]=natureGift;
                break;
            }
            case FOZ: {
                Move diagnosis =new Move(fighter, "Діагноз", 2);
                diagnosis.description = "Завдає незначної шкоди, але\nвиявляє вразливість опонента, \r\nроблячи наступну атаку\nгарантованим критом.\n2 ОП";
                diagnosis.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move injection = new Move(fighter, "Термінове втручання", 3);
                injection.description="Гравець може обрати — або\nзавдати опоненту середньої\nшкоди, або відновити собі\nневелику кількість здоров'я.\n3 ОП";
                injection.moveAction = ()->{dealDamage(mediumDamage, fighter);};

                Move recover = new Move(fighter, "Курс реабілітації", 5);
                recover.description= "Найсильніша атака, що завдає\nсередньої шкоди одразу,\nале накладає на супротивника\nефект \"виснаження\",\r\nякий віднімає здоров'я протягом\nтрьох наступних ходів.\n5 ОП";
                recover.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=diagnosis; typeMoves[1]=injection;typeMoves[2]=recover;
                break;
            }
            case KMBS: {
                Move present = new Move(fighter, "Презентація", 1);
                present.description = "Швидка атака з малою шкодою, \r\nщо має 30% шанс \"зачарувати\"\nопонента, змусивши його\nпропустити хід через \nзахоплення ідеєю.\n1 ОП";
                present.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move takeover = new Move(fighter, "Вороже поглинання", 2);
                takeover.description="Атака малої сили, що не лише\nзавдає шкоди, а й \"поглинає\"\nчастину ресурсів опонента,\nзабираючи у ворога і\nвідновлюючи собі 1 ОП.\n2 ОП";
                takeover.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move unicorn = new Move(fighter, "Запуск єдинорога", 6);
                unicorn.description="Ризикована, але надзвичайно \nпотужна атака. Вимагає всіх \nресурсів,завдає колосальної\nшкоди, представляючи успішний\nвихід на ринок.\n6 ОП";
                unicorn.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=present; typeMoves[1]=takeover; typeMoves[2]=unicorn;
                break;
            }
            case FEN: {
                Move question = new Move(fighter, "Риторичне питання", 2);
                question.description="Атака, що завдає мінімальної\nшкоди, але змушує супротивника\nсумніватися у своїх силах, \r\nтимчасово знижуючи його\nпоказник атаки на 25%.\n2 ОП";
                question.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move deconstruction = new Move(fighter, "Деконструкція", 3);
                deconstruction.description = "Гуманітарій розбирає аргументи\nсупротивника на частини,\nзавдаючи середньої шкоди\nта знімаючи з нього\nвсі позитивні ефекти.\n3 ОП";
                deconstruction.moveAction = ()->{dealDamage(mediumDamage, fighter);};

                Move reset = new Move(fighter, "Культурний ресет", 5);
                reset.description = "Потужна атака, що змінює сам\n«наратив» битви. Завдає великої\nшкоди і робить опонента\nвразливим до атак будь-якого\nтипу на наступні два ходи\r\n(отримує на 20% більше шкоди).\n5 ОП";
                reset.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=question; typeMoves[1]=deconstruction;typeMoves[2]=reset;
                break;
            }
            case OTHER: {
                Move deadline = new Move(fighter, "Дедлайн горить", 2);
                deadline.description = "Студент у паніці робить\nнезграбну, але потужну\nатаку. Атака завдає значно\nбільшої шкоди, ніж\nаналоги за це ж ОП,але\nмає 35% шанс \"забагувати\" і\nне спрацювати.\n2 ОП";
                deadline.moveAction = ()->{dealDamage(litleDamage, fighter);};

                Move work = new Move(fighter, "Паяльник в руки", 4);
                work.description = "Пряма апаратна атака. Студент\nімпровізує рішення \"на колінці\",\r\nзавдаючи дуже високої шкоди.\nОднак він отримує невелику\nшкоду у відповідь(віддача),\nбо \"обпікся, поки паяв\".\n4 ОП";
                work.moveAction = ()->{dealDamage(mediumDamage, fighter);};

                Move Kursach = new Move(fighter, "Курсач «на відмінно»", 5);
                Kursach.description = "Ультимативна атака. Студент\nне спить 48 годин і здає\nгеніальний проєкт. Атака завдає \nруйнівної шкоди, ігноруючи\nбудь-який захист. Після\nцього ходу студент отримує\nстатус \"Вигорів\" і змушений\nвикористати наступний хід на\n\"Відпочинок\".\n5 ОП";
                Kursach.moveAction = ()->{dealDamage(bigDamage, fighter);};
                typeMoves[0]=deadline; typeMoves[1]=work;typeMoves[2]=Kursach;
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

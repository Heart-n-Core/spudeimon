package ukma.ipz.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import ukma.ipz.Action;
import ukma.ipz.Dialog;
import ukma.ipz.Direction;
import ukma.ipz.GameEntry;
import ukma.ipz.fight.Fight;
import ukma.ipz.fight.FightScreen;
import ukma.ipz.fight.RefreshScreen;
import ukma.ipz.fight.Types;

public class LevelManager {
    LevelScreen levelScreen;
    GameEntry game;
    Player player = new Player();

    Level initial;
    Level firstPlatz;
    Level secondPlatz;

    Level firstBuildFloor1;
    Level firstBuildFloor2;
    Level thirdBuild;
    Level biblio;

    Level fido;
    Level fourthBuild;
    Level seventhBuild;

    Level eighthBuild;
    Level kmz;

//    int playerLvl=1;

    public LevelManager() {
//        loadFirst();
    }

    public void setup(GameEntry game) {
        this.game = game;
        loadFirst();
        levelScreen = new LevelScreen(game, initial, player);
        if (!game.chooseType){
            loadLevel(initial);
        }else {
            game.setScreen(new RefreshScreen(game, ()->{loadLevel(initial);}));
        }
    }

    private void loadFirst() {
        initial = new Level(new Texture("isometric\\levels\\1_entrance.jpg"), 10, 7, 1, 0, 8, 5);
        firstPlatz = new Level(new Texture("isometric\\levels\\first_platz.png"), 33, 21, 3, 5);
        secondPlatz = new Level(new Texture("isometric\\levels\\second_platz.png"), 35, 26, 3, 5);
        firstBuildFloor1 = new Level(new Texture("isometric\\levels\\firstBuildFloor1.png"), 33, 17, 1, 1, 11, 7);
        firstBuildFloor2 = new Level(new Texture("isometric\\levels\\firstBuildFloor2.png"), 25, 14, 1, 1, 11, 7);
        kmz = new Level(new Texture("isometric\\levels\\kmz.png"), 40, 20, 3, 5);
        thirdBuild = new Level(new Texture("isometric\\levels\\3build.png"), 22, 33, 3, 5, 11, 7);
        biblio = new Level(new Texture("isometric\\levels\\biblio.png"), 25, 11, 3, 5, 11, 7);
        fido = new Level(new Texture("isometric\\levels\\fido.png"), 15, 11, 3, 5, 11, 7);
        fourthBuild = new Level(new Texture("isometric\\levels\\4.png"), 12, 12, 3, 5, 11, 7);
        seventhBuild = new Level(new Texture("isometric\\levels\\7.png"), 12, 8, 3, 5, 11, 7);
        eighthBuild = new Level(new Texture("isometric\\levels\\8.png"), 34, 22, 3, 5, 11, 7);


        setupTiles();

        String beatriceTexture = "intro_girl.png";
        Texture hint = new Texture("isometric\\npc\\hint.png");
        //Hints
        generateHint(firstPlatz, new LevelTexture(hint, 7, 10), false, false, 90);
        generateHint(firstPlatz, new LevelTexture(hint, 9, 5), false, true, 0);
        generateHint(firstPlatz, new LevelTexture(hint, 17, 16), false, false, 0);
        generateHint(firstPlatz, new LevelTexture(hint, 31, 9), false, false, 270);
        generateHint(secondPlatz, new LevelTexture(hint, 22, 18), false, false, 0);
        generateHint(secondPlatz, new LevelTexture(hint, 23, 8), false, true, 0);

        generateHint(secondPlatz, new LevelTexture(hint, 29, 8), false, true, 0);

        Action fEtofC = () -> {
            firstBuildFloor1.X = 26;
            firstBuildFloor1.Y = 1;
//            loadLevel(firstPlatz);
            loadLevel(firstBuildFloor1);
        };
        initial.tiles[4][4].action = fEtofC;
        initial.tiles[5][4].action = fEtofC;
        initial.tiles[5][3].occupied = true;
        initial.otherTextures.add(new LevelTexture(beatriceTexture, 5, 3));
        String[] dialogIntro = {"Привіт, рефрешику :)", "Мене звати Беатріче.\nА ти, либонь, та сама \"темна конячка\",\nпро яку всі так балакають.", "Тут розпочнеться твій шлях спудея.\nПопереду буде багато викликів.\nХоча, якщо ти тут, то наснаги тобі не бракує.", "Але годі балакати.\nШлях до істини лежить у дебатах!\nОтож en garde, monsieur!"};
        Action initialFightAction = () -> {
            initial.X = 5;
            initial.Y = 2;
            loadLevel(initial);
        };
        Action initialFightWinAction = () -> {
            initial.otherTextures.clear();
            initial.tiles[5][3].interaction = null;
            initial.tiles[5][3].occupied = false;
            game.level++;
            initial.dialog = new Dialog(new String[]{game.playerName + " підвищився до " + game.level + " рівня!"}, () -> {
            });
        };

        Dialog statDial = new Dialog(dialogIntro, () -> {
            Fight initialFight = new Fight(new Texture("isometric\\fights\\initialFight.jpg"), game.type, game.level, "Беатріче", new Texture("isometric\\npc\\" + beatriceTexture), game.type, 1, initialFightAction, initialFightWinAction);
            System.out.println("Dialog end");
            loadFight(initialFight);
        });

        Action dial1 = () -> {
            initial.dialog = statDial;
        };
        initial.tiles[5][3].interaction = dial1;
        Action firstBtoInitial = () -> {
            initial.X = 4;
            initial.Y = 3;
            loadLevel(initial);
        };
        firstBuildFloor1.tiles[26][0].action = firstBtoInitial;
        firstBuildFloor1.tiles[25][0].action = firstBtoInitial;
        firstBuildFloor1.tiles[24][0].action = firstBtoInitial;
        firstBuildFloor1.tiles[27][0].action = firstBtoInitial;
        firstBuildFloor1.tiles[28][0].action = firstBtoInitial;

        LevelTexture firstBcorridorBlocking = new LevelTexture("firstBcorridorBlocking.png", 4, 5);
        firstBuildFloor1.otherTextures.add(firstBcorridorBlocking);
        firstBuildFloor1.tiles[4][5].occupied = true;
//        firstBuildFloor1.tiles[3][4].occupied=true;
        Action dialB1BlockCorridor = () -> {
            String[] lines = new String[]{"О, привіт, друже, а ти заходив на кафедру інформатики на 2 поверсі?\r\nТам роздають автомати з ІНФОПОШУКУ!!!\r\nНу ж бо, поспіши, доки маєш таку змогу!"};
            firstBuildFloor1.dialog = new Dialog(lines, () -> {
                levelScreen.externalDirection = Direction.UP;
            });
//            statDial.regenerate(lines, () -> {levelScreen.externalDirection= Direction.UP;});
//            firstBuildFloor1.dialog = statDial;
        };
        firstBuildFloor1.tiles[4][5].interaction = dialB1BlockCorridor;
        firstBuildFloor1.tiles[3][5].action = dialB1BlockCorridor;
        Action removeDialB1BlockCorridor = () -> {
            firstBuildFloor1.tiles[4][5].occupied = false;
            firstBuildFloor1.otherTextures.remove(firstBcorridorBlocking);
            firstBuildFloor1.tiles[3][5].action = null;
            firstBuildFloor1.tiles[4][5].interaction = null;
//            firstBuildFloor1.tiles[3][4].occupied=false;

        };


        Action firstBtoFirstPlatz = () -> {
            firstPlatz.X = 9;
            firstPlatz.Y = 5;
            loadLevel(firstPlatz);
        };
        firstBuildFloor1.tiles[3][0].action = firstBtoFirstPlatz;
        firstBuildFloor1.tiles[4][0].action = firstBtoFirstPlatz;
        firstPlatz.tiles[9][4].action = () -> {
            firstBuildFloor1.X = 4;
            firstBuildFloor1.Y = 1;
            loadLevel(firstBuildFloor1);
        };
        Action firstBfirstFtosndF = () -> {
            firstBuildFloor2.X = 14;
            firstBuildFloor2.Y = 8;
            loadLevel(firstBuildFloor2);
        };
        firstBuildFloor1.tiles[29][12].action = firstBfirstFtosndF;
        firstBuildFloor1.tiles[30][12].action = firstBfirstFtosndF;

        //Second floor
        Action firstBsndFtofirstF = () -> {
            firstBuildFloor1.X = 29;
            firstBuildFloor1.Y = 11;
            loadLevel(firstBuildFloor1);
        };
        firstBuildFloor2.tiles[14][9].action = firstBsndFtofirstF;
        firstBuildFloor2.tiles[15][9].action = firstBsndFtofirstF;

        //content
        generateStaticNPC(firstBuildFloor2, new LevelTexture("1Bhint1.png", 17, 4), new String[]{"Якщо ти зазнав поразки, завжди можна спробувати стати сильнішим\r\nу боротьбі з опонентом, якого здолав у дебатах раніше.", "Просто не  здавайся ;)"}, () -> {
        });
        LevelTexture coffee1 = new LevelTexture("coffee1.png", 19, 3);
        firstBuildFloor2.otherTextures.add(coffee1);
        firstBuildFloor2.tiles[19][3].interaction = () -> {
            game.level += 1;
            firstBuildFloor2.dialog = new Dialog(new String[]{game.playerName + " знайшов філіжанку кави та підвищився до " + game.level + " рівня!"}, () -> {
                firstBuildFloor2.tiles[19][3].interaction = null;
                firstBuildFloor2.otherTextures.remove(coffee1);
            });
        };

        //Boss
        LevelTexture boss1 = new LevelTexture("boss1.png", 4, 10);
        firstBuildFloor2.otherTextures.add(boss1);
        firstBuildFloor2.tiles[4][10].occupied = true;
        firstBuildFloor2.tiles[4][10].interaction = () -> {
            String[] lines = {"Що вершить долю людства у цьому світі?\r\nЯкась незрима істота чи закон, подібно до Длані Господньої,\r\nщо ширяє над світом?", "Принаймні істинно те, що людина не має навіть своєї волі,\r\nне кажучи вже про автомат з інфопошуку.\r\nПодивимося, як першокурсники готуються до заліку!"};
            Action afterAction = () -> {
                firstBuildFloor2.X = 4;
                firstBuildFloor2.Y = 9;
                loadLevel(firstBuildFloor2);
            };
            Action winAction = () -> {
                game.level += 2;
                removeDialB1BlockCorridor.execute();
//                firstBuildFloor2.dialog = new Dialog(new String[]{"Ти підкорив логіку нулів та одиниць, структуру, що не має почуттів.\r\nАле хто пише код, і для кого?\r\nНайскладніший алгоритм — це людська душа.\r\nШукай справжні відповіді у Гуманітаріїв...", "...",game.playerName+" підвищився до "+game.level+" рівня!"}, () -> {});
                firstBuildFloor2.dialog = new Dialog(new String[]{"Ти підкорив логіку нулів та одиниць, структуру, що не має почуттів.\r\nАле хто пише код, і для кого?\r\nНайскладніший алгоритм — це людська душа.\r\nШукай справжні відповіді у Гуманітаріїв..."}, () -> {
                    levelScreen.blockAction(500, () -> {
                        firstBuildFloor2.dialog = new Dialog(new String[]{game.playerName + " підвищився до " + game.level + " рівня!"}, () -> {
                        });
                    });
                });

            };
            Fight boss1Fight = new Fight(new Texture("isometric\\fights\\boss1Loc.png"), game.type, game.level, "Бос ФІ", new Texture("isometric\\npc\\boss1.png"), Types.FI, 2, afterAction, winAction);
            firstBuildFloor2.dialog = new Dialog(lines, () -> {
                loadFight(boss1Fight);
            });
        };

        //First platz
        LevelTexture lemons = new LevelTexture("lemons.png", 15, 12);
        firstPlatz.otherTextures.add(lemons);
        firstPlatz.tiles[13][12].action = () -> {
            firstPlatz.dialog = new Dialog(new String[]{"О ні, це ж ті самі лимони!", "Треба накивати звідси п'ятами до 3 корпусу, доки по них не прибув власник!"}, () -> {
                levelScreen.externalDirection = Direction.LEFT;
            });
        };
        generateStaticNPC(firstPlatz, new LevelTexture("firstPlatzMem.png", 6, 8),new String[]{"Пощастило ж тобі записатися на мемологічні студії.\r\nЯкби ж я тільки не зловив бан на САЗі за ддос((("}, () -> {});


        LevelTexture boss3LT = new LevelTexture("boss3.png", 17, 13);
        LevelTexture boss3LTRE = new LevelTexture("boss3.png", 16, 16);
        Fight fight3;
        {
            Texture bossFight3Bgr = new Texture("isometric\\fights\\boss3Loc.png");
            Texture boss3Texture = new Texture("isometric\\npc\\boss3.png");
            Action afterAction = () -> {
                firstPlatz.X = 16;
                firstPlatz.Y = 12;
                loadLevel(firstPlatz);
            };
            Action reward = ()->{
                game.level+=3;
                firstPlatz.dialog = new Dialog(new String[]{"Визнаю, звинувачення були хибними.","Ви вивчили правила гри та ціну порядку.\r\nТа чиїм коштом їх написано?\r\nЗа кожним параграфом закону стоїть економічний інтерес.\r\nЗапитайте в Економістів, хто насправді платить за все..."}, () -> {levelScreen.blockAction(500, ()->{firstPlatz.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Action winAction = () -> {
                firstPlatz.tiles[17][12].action = null;
                firstPlatz.otherTextures.remove(boss3LT);
                firstPlatz.otherTextures.add(boss3LTRE);
//                boss3LT.x=16;boss3LT.y=16;
//                firstPlatz.otherTextures.add(boss3LT);
                firstPlatz.tiles[16][16].occupied = true;
                firstPlatz.tiles[16][16].interaction = () -> {
                    firstPlatz.dialog = new Dialog(new String[]{"Так-с... Заявка на викрадення лимонів, форма 37-Б.\r\nЗаповнена не за зразком.\r\nПункт 4.2, відсутній підпис свідка.\r\nЗгідно з постановою від третього квазі-юніуса, це є підставою для відмови.", "...", "О, це Ви! Правду кажуть, злочинці таки повертаються на місце злочину.\r\nХіба що Ви зможете довести власну непричетність, хехе!"}, ()->{
                        Fight fight3Re = new Fight(bossFight3Bgr, game.type, game.level, "Бос ФПВН", boss3Texture, Types.FPVN, 7, ()->{firstPlatz.X=16;firstPlatz.Y=15;loadLevel(firstPlatz);}, reward);
                        loadFight(fight3Re);
                    });
                };
                reward.execute();
            };
            fight3 = new Fight(bossFight3Bgr, game.type, game.level, "Бос ФПВН", boss3Texture, Types.FPVN, 7, afterAction, winAction);
        }
        Action bossFight3 = () -> {
            firstPlatz.dialog = new Dialog(new String[]{"Так-с... Заявка на викрадення лимонів, форма 37-Б.\r\nЗаповнена не за зразком.\r\nПункт 4.2, відсутній підпис свідка.\r\nЗгідно з постановою від третього квазі-юніуса, це є підставою для відмови.", "...", "О, це Ви! Правду кажуть, злочинці таки повертаються на місце злочину.\r\nХіба що Ви зможете довести власну непричетність, хехе!"}, ()->{
                fight3.playerLvl=game.level;
                fight3.playerType=game.type;
                loadFight(fight3);
            });
        };
        Action removeLemons = () -> {firstPlatz.otherTextures.remove(lemons); firstPlatz.tiles[13][12].action = null;
//            firstPlatz.tiles[18][12].interaction = bossFight3;
            firstPlatz.otherTextures.add(boss3LT);
            firstPlatz.tiles[17][12].action = bossFight3;
        };

        firstPlatz.tiles[17][17].occupied = false;
        Action biblioToFirstPl = ()->{
            firstPlatz.X=17;
            firstPlatz.Y=16;
            loadLevel(firstPlatz);
        };
        biblio.tiles[3][0].action = biblioToFirstPl;biblio.tiles[4][0].action = biblioToFirstPl;biblio.tiles[5][0].action = biblioToFirstPl;biblio.tiles[6][0].action = biblioToFirstPl;biblio.tiles[7][0].action = biblioToFirstPl;
        firstPlatz.tiles[17][17].action = ()->{
            biblio.X=5;
            biblio.Y=1;
            loadLevel(biblio);
        };

        //Biblio
        biblio.tiles[23][5].occupied = true;
        biblio.otherTextures.add(new LevelTexture("refresher.png", 23, 5));
        biblio.tiles[23][5].interaction = () -> {
            biblio.dialog = new Dialog(new String[]{"Час рефрешнутися!"}, ()->{
                loadRefresh(()->{biblio.X=22; biblio.Y=5; loadLevel(biblio);});
            });
        };

        //Third building
        firstPlatz.tiles[6][10].action = () -> {
            thirdBuild.X = 13;
            thirdBuild.Y = 16;
            loadLevel(thirdBuild);
        };
        Action thirdBToFirstPlatz = ()->{
            firstPlatz.X=7;
            firstPlatz.Y=10;
            loadLevel(firstPlatz);
        };
        thirdBuild.tiles[14][16].action = thirdBToFirstPlatz;thirdBuild.tiles[14][15].action = thirdBToFirstPlatz;
        LevelTexture cig = new LevelTexture("cig.png", 16, 2);
        thirdBuild.otherTextures.add(cig);
        thirdBuild.tiles[16][2].interaction = ()->{
            thirdBuild.dialog = new Dialog(new String[]{game.playerName+" отримує рівень від філіжанки кави,\r\nале миттєво втрачає його через пачку дзиґарів!"}, ()->{thirdBuild.tiles[14][16].action=null; thirdBuild.otherTextures.remove(cig);});
        };

        LevelTexture boss2 = new LevelTexture("boss2.png", 18, 28);
        thirdBuild.otherTextures.add(boss2);
        thirdBuild.tiles[18][28].occupied=true;
        thirdBuild.tiles[18][28].interaction = () -> {
            Action afterAction = () -> {
                thirdBuild.X = 18;
                thirdBuild.Y = 27;
                loadLevel(thirdBuild);
            };
            Action winAction = () -> {
                game.level+=3;
                removeLemons.execute();
                thirdBuild.dialog = new Dialog(new String[]{"Ти осягнув глибину наративів та ідей.\r\nТа чи варті вони чогось, якщо не змінюють світ?\r\nСлово без дії — лише вітер.\r\n" +
//                    "Соціологи знають, як ідеї стають рухами..."
                    "Правники знають, як втілити ідею в законі ..."
                }, () -> {levelScreen.blockAction(500, ()->{thirdBuild.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Fight fight2 = new Fight(new Texture("isometric\\fights\\boss1Loc.png"), game.type, game.level, "Бос ФГН", new Texture("isometric\\npc\\boss2.png"), Types.FHN, 1, afterAction, winAction);
            thirdBuild.dialog = new Dialog(new String[]{"Страх. Біль. Самотність.\r\nЦе три кити, на яких тримається людське розуміння.\r\nТи думаєш, що знання можна отримати з книг?\r\n - Ні. По-справжньому зрозуміти іншу людину можна лише через біль.", "Я - Маленія, стилос ФГНу.\r\nЧас тобі отримати це пізнання!"}, ()->{
            loadFight(fight2);
            });
        };

        //Second platz
        Action secondPlatzToFirstPlatz = () -> {
            firstPlatz.X=31;
            firstPlatz.Y=9;
            loadLevel(firstPlatz);
        };
        secondPlatz.tiles[22][7].action = secondPlatzToFirstPlatz; secondPlatz.tiles[23][7].action = secondPlatzToFirstPlatz;

        Texture fidoEntr = new Texture("isometric\\npc\\fidoEntr.png");
        Action enableFido = ()->{generateHint(secondPlatz, new LevelTexture(hint, 13, 18), false, false, 0);
            generateStaticNPC(secondPlatz, new LevelTexture(fidoEntr, 14, 18), new String[]{"В підвалі Фідо відбуваються дивні речі.\r\nНавіть ходять чутки, що вони вирішили переписати САЗ.\r\nАле ось вже тиждень ніхто звідти не виходить.","Двері відчинені, але от мені спускатися туди лячно.\r\nМожеш зазирнути досередини пересвідчитися,\r\nщо там все гаразд?"}, ()->{});
            secondPlatz.tiles[13][19].occupied=false;
            secondPlatz.tiles[13][19].action = ()->{fido.X=2;fido.Y=2;loadLevel(fido);};};

        {Action fidoToSecondPlatz = () -> {secondPlatz.X=13; secondPlatz.Y=18; loadLevel(secondPlatz);}; fido.tiles[1][1].action=fidoToSecondPlatz; fido.tiles[2][1].action=fidoToSecondPlatz;}
        LevelTexture janitor = new LevelTexture("janitor.png", 22, 18);
        secondPlatz.otherTextures.add(janitor);secondPlatz.tiles[22][18].occupied=true;
        secondPlatz.tiles[22][18].interaction = () -> {secondPlatz.dialog =new Dialog(new String[]{"Куди по щойно митій підлозі!\r\nНе пущу в КМЦ, доки підлога не висохне!"}, ()->{});};
        Action removeJanitor = ()->{secondPlatz.otherTextures.remove(janitor); secondPlatz.tiles[22][18].occupied=false; secondPlatz.tiles[22][18].interaction=null;};
        generateStaticNPC(secondPlatz, new LevelTexture("fenJoke.png", 30, 10), new String[]{"Натхненний цитатою кличка 'Нам не потрібні гроші... не забирайте наші гроші',\r\nБос ФЕНу розробив нову сертифікатну програму про накопичення і збереження капіталу.\r\nХіба не чудово?"},()->{} );
        secondPlatz.tiles[29][7].occupied=false;secondPlatz.tiles[29][7].action=()->{fourthBuild.X=4; fourthBuild.Y=9; loadLevel(fourthBuild);};
        {Action fourthBToSndP = ()->{secondPlatz.X=29; secondPlatz.Y=8; loadLevel(secondPlatz);};fourthBuild.tiles[3][10].action=fourthBToSndP;fourthBuild.tiles[4][10].action=fourthBToSndP;
            Action afterAction = ()->{fourthBuild.X=8; fourthBuild.Y=5; loadLevel(fourthBuild);};
            Action winAction = ()->{game.level+=3; enableFido.execute();
                fourthBuild.dialog = new Dialog(new String[]{"Ти зрозумів невидимі потоки ресурсів, що керують світом.\r\nАле моделі лише описують їх.\r\nА хто ж створює реальну вартість з ідеї та ризику?\r\nЗа цим іди до Могилянської Бізнес-Школи..."}, () -> {levelScreen.blockAction(500, ()->{fourthBuild.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            generateStaticNPC(fourthBuild, new LevelTexture("boss6.png",8,6),
                new String[]{"Питають дружину: 'Де ти береш гроші?'. Вона каже: 'У тумбочці'.\r\nЇй кажуть: 'А хто кладе в тумбочку гроші?'.\r\nВона каже: 'Не знаю'. - Так де ти береш гроші?"},
                ()->{Fight f4 = new Fight(new Texture("isometric\\fights\\boss1Loc.png"), game.type, game.level, "Бос ФЕН", new Texture("isometric\\npc\\boss6.png"), Types.FEN, 11, afterAction, winAction);loadFight(f4);
            });
        };
        Action enable7 = ()->{
            generateHint(secondPlatz, new LevelTexture(hint, 13, 7), false, true, 0);
            secondPlatz.tiles[13][6].occupied=false;
            secondPlatz.tiles[13][6].action=()->{seventhBuild.X=7;seventhBuild.Y=5;loadLevel(seventhBuild);};
        };
        Texture boss6 = new Texture("isometric\\npc\\boss4.png");
        Action enable6 = ()->{
            //TODO after floppy picked
            Action afterAction = ()->{
                secondPlatz.X=17;
                secondPlatz.Y=12;
                loadLevel(secondPlatz);
            };
            Action winAction = ()->{
                game.level+=3;
                enable7.execute();
                secondPlatz.dialog = new Dialog(new String[]{"Ти побачив, як рухаються маси, немов єдиний організм.\r\nАле на якому фундаменті?\r\nБудь-яка стратегія безсила проти фундаментального відкриття.\r\nПриродничники відкривають нові світи для людства..."}, () -> {levelScreen.blockAction(500, ()->{secondPlatz.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Fight boss6Fight = new Fight(new Texture("isometric\\fights\\boss6Loc.png"), game.type, game.level, "Бос ФСНСТ", new Texture("isometric\\npc\\boss4.png"), Types.FSNST, 16, afterAction, winAction);

            generateStaticNPC(secondPlatz, new LevelTexture(boss6, 17, 13), new String[]{"Дякую, що приніс диск.\r\nТи бачиш ці правила? Ці регламенти?\r\nЦе все частина системи, яка обмежує нас.", "На цьому наша дружня розмова добігатиме кінця.\r\nАдже перше правило клубу — не говорити про ремастер САЗу.\r\nА друге правило — НІКОМУ не говорити ремастер САЗу!"}, ()->{
                loadFight(boss6Fight);
            });
        };


        //Fido hub
        fido.tiles[9][4].occupied=true;
        LevelTexture floppy = new LevelTexture("floppy.png", 9, 4);
        fido.otherTextures.add(floppy);
        fido.tiles[9][4].interaction = ()->{fido.dialog=new Dialog(new String[]{"Гравець схвильовано бере загадкову безцінну дискету!"}, ()->{enable6.execute();fido.tiles[9][4].occupied=false; fido.otherTextures.remove(floppy);});};
        Fight fight5;
        {
            LevelTexture boss5LT = new LevelTexture("boss5.png", 5, 8);
            Texture bossFight5Bgr = new Texture("isometric\\fights\\boss1Loc.png");
            Texture boss5Texture = new Texture("isometric\\npc\\boss5.png");
            fido.tiles[5][8].occupied=true;
            fido.otherTextures.add(boss5LT);
            Action afterAction = () -> {
                fido.X = 4;
                fido.Y = 7;
                loadLevel(fido);
            };
            Action reward = ()->{
                game.level+=3;
                fido.dialog = new Dialog(new String[]{"Ти навчився будувати імперії та перетворювати мрії на гроші.\r\nАле на якому фундаменті?\nЛюдьми керують не лише гроші.", "Віднеси цей диск в центр 2 плацу.\r\nБос ФСНСТ мав розробити на основі даних з цієї БД політтехнологію,\r\nяка змінить всю спудейську спільноту..."}, () -> {levelScreen.blockAction(500, ()->{fido.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Action winAction = () -> {
                fido.X = 5;
                fido.tiles[5][7].action = null;
                fido.tiles[5][8].interaction = () -> {
                    fido.dialog = new Dialog(new String[]{"Знаєш, чому людство досі не вимерло?\r\nНе через мораль, не через закони.\r\nЧерез те, що завжди знаходились ті, хто був готовий робити важкий вибір.\r\nТі, хто розумів, що для великої мети можна пожертвувати малим.", "Мене звинувачують у радикальних методах. У цинізмі.\r\nАле порятунок має свою ціну.\r\nПорятунок у тій дискеті на пентаграмі!"}, ()->{
                        Fight fight5Re = new Fight(bossFight5Bgr, game.type, game.level, "Бос KMBS", boss5Texture, Types.KMBS, 13, ()->{ fido.X = 5;fido.Y = 7;loadLevel(fido);}, reward);
                        loadFight(fight5Re);
                    });
                };
                reward.execute();
            };
            fight5 = new Fight(bossFight5Bgr, game.type, game.level, "Бос KMBS", boss5Texture, Types.KMBS, 13, afterAction, winAction);
        }
        fido.tiles[5][7].action= () -> {
            fido.dialog = new Dialog(new String[]{"Знаєш, чому людство досі не вимерло?\r\nНе через мораль, не через закони.\r\nЧерез те, що завжди знаходились ті, хто був готовий робити важкий вибір.\r\nТі, хто розумів, що для великої мети можна пожертвувати малим.", "Мене звинувачують у радикальних методах. У цинізмі.\r\nАле порятунок має свою ціну.\r\nПорятунок у тій дискеті на пентаграмі!"}, ()->{
                fight5.playerLvl=game.level;
                fight5.playerType=game.type;
                loadFight(fight5);
            });
        };

        //Seventh build
        {Action sevBToSndPl = ()->{secondPlatz.X=13; secondPlatz.Y=7; loadLevel(secondPlatz);}; seventhBuild.tiles[7][6].action=sevBToSndPl;seventhBuild.tiles[8][6].action=sevBToSndPl;}
        {
            Action afterAction = ()->{
                seventhBuild.X=4;
                seventhBuild.Y=3;
                loadLevel(seventhBuild);
            };
            Action winAction = ()->{
                game.level+=3;
                removeJanitor.execute();
                seventhBuild.dialog = new Dialog(new String[]{"Ти пізнав непорушні закони матерії.\r\nАле для кого існують ці закони, як не для живих?\r\nНайскладніший механізм у Всесвіті — це життя.", "Його таємниці та вразливість бережуть на Факультеті Охорони Здоров'я,\r\nщо між 2 плацом та КМЦ."}, () -> {levelScreen.blockAction(500, ()->{seventhBuild.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Fight boss7Fight = new Fight(new Texture("isometric\\fights\\boss7Loc.png"), game.type, game.level, "Бос ФПРН", new Texture("isometric\\npc\\boss7.png"), Types.FPRN, 15, afterAction, winAction);

            generateStaticNPC(seventhBuild, new LevelTexture("boss7.png", 4, 4), new String[]{"Ти знаєш чому я гарний науковець?\r\nЯкби я був поганим науковцем ми б зараз не теревенили тут, чи не так?\r\n Кілька зайвих крапель нітрогліцерину чи помилка у формулі,\r\nі ти вже розмазаний по стіні!", "Ти думаєш що можеш відтворити цю формулу?\r\nНу що ж, вперед, я чекаю!"}, ()->{
                boss7Fight.playerType=game.type;
                boss7Fight.playerLvl=game.level;
                loadFight(boss7Fight);
            });
        }

        secondPlatz.tiles[22][19].occupied = false;
        secondPlatz.tiles[22][19].action = () -> {
            eighthBuild.X=16;
            eighthBuild.Y=1;
            loadLevel(eighthBuild);
        };

        //Eighth build
        {Action eightToKMZ = ()->{kmz.X=27;kmz.Y=3;loadLevel(kmz);};eighthBuild.tiles[16][21].action=eightToKMZ;eighthBuild.tiles[17][21].action=eightToKMZ;
            eighthBuild.tiles[17][17].occupied = true;
            LevelTexture chair = new LevelTexture("chair.png", 17, 17);
            eighthBuild.otherTextures.add(chair);
            Action afterAction = ()->{
                eighthBuild.X=17;
                eighthBuild.Y=17;
                loadLevel(eighthBuild);
            };
            Action winAction = ()->{
                game.level+=3;
                eighthBuild.otherTextures.remove(chair);eighthBuild.tiles[17][17].occupied = false;
                eighthBuild.dialog = new Dialog(new String[]{"Ти врятував людину.\r\nТи торкнувся найціннішого.\r\nАле чи можна зцілити всіх, не змінивши систему, в якій вони хворіють?", "Ти зробив повне коло і повернувся до початку. Тепер ти готовий..."}, () -> {levelScreen.blockAction(500, ()->{eighthBuild.dialog = new Dialog(new String[]{game.playerName+" підвищився до "+game.level+" рівня!"}, ()->{});});});
            };
            Fight boss8Fight = new Fight(new Texture("isometric\\fights\\boss1Loc.png"), game.type, game.level, "Бос ФОЗ", new Texture("isometric\\npc\\boss8.png"), Types.FOZ, 18, afterAction, winAction);

            generateStaticNPC(eighthBuild, new LevelTexture("boss8.png", 16, 17), new String[]{"Ти виглядаєш трохи... нездоровим.\r\nБлідий колір обличчя, прискорене серцебиття.\r\nСимптоми очевидні: страх перед неминучим.\r\nНе хвилюйся, я можу це виправити.", "Знаєш, коли я отримав диплом, я присягнув допомагати людям.\r\nАле знаєш, що виявилося?\r\nЗцілення... не таке вже й веселе, як заподіяння болю.\r\nНу що ж, давай застосуємо все на практиці!"}, ()->{
                boss8Fight.playerType=game.type;
                boss8Fight.playerLvl=game.level;
                loadFight(boss8Fight);
            });
        }

        //KMZ
        LevelTexture beatFinal = new LevelTexture(beatriceTexture, 27, 8);
        LevelTexture beatEpilog = new LevelTexture(beatriceTexture, 12, 9);
        kmz.otherTextures.add(beatFinal);
        kmz.tiles[26][3].occupied = true;kmz.tiles[28][3].occupied = true;
        generateStaticNPC(kmz, new LevelTexture("boss1.png", 26, 4), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss2.png", 28, 4), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss3.png", 26, 5), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss4.png", 28, 5), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss5.png", 26, 6), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss6.png", 28, 6), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss7.png", 26, 7), new String[]{"..."}, ()->{});generateStaticNPC(kmz, new LevelTexture("boss8.png", 28, 7), new String[]{"..."}, ()->{});
        kmz.tiles[27][8].occupied=true;
        kmz.tiles[27][8].interaction = () -> {
            Action afterAction = () -> {kmz.X=12; kmz.Y=8; loadLevel(kmz);};
            Action winAction = () -> {
                kmz.tiles[27][8].interaction=null;
                kmz.tiles[27][2].action=null;
                kmz.otherTextures.clear();
                kmz.tiles[28][3].occupied = false;kmz.tiles[26][3].occupied = false;kmz.tiles[27][2].occupied = true;kmz.tiles[27][8].occupied = false;kmz.tiles[26][4].occupied = false;kmz.tiles[28][4].occupied = false;kmz.tiles[26][5].occupied = false;kmz.tiles[28][5].occupied = false;kmz.tiles[26][6].occupied = false;kmz.tiles[28][6].occupied = false;kmz.tiles[26][7].occupied = false;kmz.tiles[28][7].occupied = false;
                generateStaticNPC(kmz, beatEpilog, new String[]{
                    "Агов!", "Агов, отямся!\r\nТи знову хильнув зайвого на останньому п'ятничному КМЦ і ночував на лаві.", "Що? Що тобі снилося?\r\nЯкі битви з босами, які напівбоги, яка Беатріче?\r\nМене ж звати Аня.\r\nПоїхали вже на Трою!", "THE END"
                },()->{
                    Gdx.app.exit();
                });
            };
            Fight finalFight = new Fight(new Texture("isometric\\fights\\finalLoc.png"), game.type, game.level, "Беатріче", new Texture("isometric\\npc\\" + beatriceTexture), game.type, (game.level-1)>0? game.level-1 : 1, afterAction, winAction);


            kmz.dialog = new Dialog(new String[]{"Ти зробив це. Ти пройшов вісім шляхів і побачив вісім істин.\r\nКожен із опонентів вважав свою дисципліну єдиною істиною.\r\nКожен з них бачив лише одну грань діаманта.", "Я розпочала твій шлях. Тепер я — твій останній поріг.\r\nТи думав, що шукаєш якусь таємницю, прихований артефакт?\r\n- Ні. Справжня Синтеза — це не фахове знання, яке можна отримати.\r\nЦе здатність до творення нового УНІВЕРСАЛЬНОГО знання. І це виклик, який треба витримати.", "Я — Беатріче. Колись я пізнала ту ж універсальність, що й ти.\r\nЯ — єдність усіх дисциплін. Ти переміг їхні окремі тіні, що звуться дисциплінами.\r\nТепер подивись в обличчя самому Світлу! Бийся зі мною!", "Покажи мені не силу однієї дисципліни, а мудрість оперувати ними усіма!\r\nДоведи, що ти не просто ще один відокремлений промінь,\r\nа той, хто здатен увібрати в себе все світло!"}, ()->{
                loadFight(finalFight);
            });
        };
        kmz.tiles[27][2].action = () -> {
            eighthBuild.X = 16;
            eighthBuild.Y = 20;
            loadLevel(eighthBuild);
        };

    }

    void generateStaticNPC(Level level, LevelTexture levelTexture, String[] lines, Action action) {
        level.tiles[levelTexture.x][levelTexture.y].occupied=true;
        level.tiles[levelTexture.x][levelTexture.y].interaction = () -> {
            level.dialog = new Dialog(lines, action);
        };
        level.otherTextures.add(levelTexture);
    }
    void generateHint(Level level, LevelTexture levelTexture, boolean flipX, boolean flipY, int rotateDeg) {
        levelTexture.sprite.flip(flipX, flipY);
        levelTexture.sprite.setOriginCenter();
        levelTexture.sprite.setRotation(rotateDeg);
        levelTexture.sprite.setAlpha(0.5f);
        level.otherTextures.add(levelTexture);
    }

    void loadLevel(Level level){
        levelScreen.canResize = false;
        levelScreen = new LevelScreen(game, level, player);
        game.setScreen(levelScreen);
        levelScreen.canResize = true;
    }

    void loadFight(Fight fight){
        levelScreen.canResize = false;
        Screen fightScreen = new FightScreen(game, fight);
        game.setScreen(fightScreen);
        levelScreen.canResize = true;
    }
    void loadRefresh(Action afterAction){
        levelScreen.canResize = false;
        Screen refresh = new RefreshScreen(game, afterAction);
        game.setScreen(refresh);
        levelScreen.canResize = true;
    }

    public Level getInitialLevel() {
        return initial;
    }
    public Player getPlayer() {
        return player;
    }

    private void setupTiles(){
        initial.tiles[0][2].occupied=true;
        initial.tiles[1][2].occupied=true;
        initial.tiles[2][2].occupied=true;
        initial.tiles[3][2].occupied=true;
        initial.tiles[3][3].occupied=true;
        initial.tiles[6][3].occupied=true;
        initial.tiles[6][2].occupied=true;
        initial.tiles[7][2].occupied=true;
        initial.tiles[8][2].occupied=true;
        initial.tiles[9][2].occupied=true;
        initial.tiles[9][3].occupied=true;

        {
            // стіна першого корпусу на вихід в плац
            for (int i = 3; i <= 16; i++) {
                if (i == 9)
                    continue;
                firstPlatz.tiles[i][4].occupied = true;
            }

            // ліва межа до виходу на Іллінській
            for (int i = 0; i <= 4; i++)
                firstPlatz.tiles[16][i].occupied = true;

            // права межа до виходу на Іллінській
            for (int i = 0; i <= 8; i++) {
                firstPlatz.tiles[18][i].occupied = true;
            }

            // ворота в перший плац
            firstPlatz.tiles[2][5].occupied = true;
            firstPlatz.tiles[2][6].occupied = true;

            // 3 корпус

            // стіна біля воріт
            firstPlatz.tiles[3][6].occupied = true;
            firstPlatz.tiles[4][6].occupied = true;


            // стіна перпендикулярна попередній (ліва стіна 3 плацу)
            firstPlatz.tiles[4][7].occupied = true;

            // стіна перпендикулярна попередній (нижня стіна впадини 3 плацу)
            firstPlatz.tiles[3][7].occupied = true;

            // стіна перпендикулярна попередній (ліва стіна впадини 3 плацу)
            firstPlatz.tiles[2][8].occupied = true;
            firstPlatz.tiles[2][9].occupied = true;

//         стіна перпендикулярна попередній (верхня стіна впадини 3 плацу)
//        firstPlatz.tiles[3][10].occupied=true;
//        firstPlatz.tiles[4][10].occupied=true;
//        firstPlatz.tiles[5][10].occupied=true;
//        firstPlatz.tiles[6][10].occupied=true;
            firstPlatz.tiles[3][9].occupied = true;
            firstPlatz.tiles[4][9].occupied = true;
            firstPlatz.tiles[5][9].occupied = true;
            firstPlatz.tiles[6][9].occupied = true;

            // ліва стіна 3 плацу, яка ближча жо горького
            firstPlatz.tiles[6][11].occupied = true;

            // нижня стіна верхньої впадини
            firstPlatz.tiles[5][11].occupied = true;
            firstPlatz.tiles[4][11].occupied = true;
            firstPlatz.tiles[3][11].occupied = true;

            //ліва стіна верхньої впадини
            firstPlatz.tiles[3][12].occupied = true;
            for (int i = 12; i <= 20; i++)
                firstPlatz.tiles[4][i].occupied = true;

            // прохід за 2 корпусом
            for (int i = 5; i <= 9; i++)
                firstPlatz.tiles[i][17].occupied = true;

            firstPlatz.tiles[10][17].occupied = true;
            firstPlatz.tiles[10][16].occupied = true;
            firstPlatz.tiles[10][15].occupied = true;
            firstPlatz.tiles[10][14].occupied = true;

            for (int i = 10; i <= 16; i++) {
                firstPlatz.tiles[i][14].occupied = true;
                firstPlatz.tiles[i][13].occupied = true;

            }

            // 2 корпус
            for (int i = 9; i <= 18; i++) {
                for (int j = 10; j < 12; j++) {
                    firstPlatz.tiles[i][j].occupied = true;
                }
            }

            // бібліо
            // ліва будівля
            for (int i = 15; i <= 20; i++) {
                firstPlatz.tiles[15][i].occupied = true;
            }
            //власне вхід бібліо + музей
            for (int i = 15; i <= 24; i++) {
                firstPlatz.tiles[i][17].occupied = true;
                firstPlatz.tiles[i][18].occupied = true;

            }

            //власне бібліо
            for (int i = 18; i <= 20; i++) {
                for (int j = 13; j <= 16; j++) {
                    firstPlatz.tiles[i][j].occupied = true;
                }
            }

            // церква
            for (int i = 22; i <= 26; i++) {
                for (int j = 14; j <= 16; j++) {
                    firstPlatz.tiles[i][j].occupied = true;
                }
            }

            // вся ділянка біля недобудованого корпусу + лікарні
            for (int i = 19; i <= 32; i++) {
                for (int j = 0; j <= 8; j++) {
                    firstPlatz.tiles[i][j].occupied = true;
                }
            }

            // будівля між плацами
            for (int i = 28; i <= 30; i++) {
                for (int j = 10; j <= 19; j++) {
                    firstPlatz.tiles[i][j].occupied = true;
                }
            }

            //Church exit
            firstPlatz.tiles[25][18].occupied = true;
            firstPlatz.tiles[26][18].occupied = true;
            firstPlatz.tiles[27][18].occupied = true;
            //Old right side
            firstPlatz.tiles[31][14].occupied = true;
            firstPlatz.tiles[32][14].occupied = true;
            firstPlatz.tiles[32][13].occupied = true;
            firstPlatz.tiles[32][12].occupied = true;
            firstPlatz.tiles[32][11].occupied = true;
            firstPlatz.tiles[32][10].occupied = true;
            firstPlatz.tiles[32][9].action = () -> {
                secondPlatz.X = 23;
                secondPlatz.Y = 8;
                loadLevel(secondPlatz);
            };
        }

        {
            // 2 плац
            // будівля праворуч
            for (int i = 24; i <= 30; i++) {
                secondPlatz.tiles[i][7].occupied = true;
            }

            //стіна праворуч на вулицю
            for (int i = 8; i <= 11; i++) {
                secondPlatz.tiles[31][i].occupied = true;
            }

            // верхня стіна
            for (int i = 26; i <= 31; i++) {
                secondPlatz.tiles[i][12].occupied = true;
            }

            // права стіна КМЦ
            for (int i = 12; i <= 19; i++) {
                secondPlatz.tiles[26][i].occupied = true;
            }

            // нижня стіна КМЦ
            for (int i = 6; i <= 25; i++) {
                secondPlatz.tiles[i][19].occupied = true;
            }

            // права стіна готелю
            for (int i = 15; i <= 19; i++) {
                secondPlatz.tiles[5][i].occupied = true;
            }

            // права стіна поруч з готелем
            for (int i = 6; i <= 15; i++) {
                secondPlatz.tiles[4][i].occupied = true;
            }

            // ліва стіна дальньої будівлі від входу
            for (int i = 4; i <= 14; i++) {
                secondPlatz.tiles[i][6].occupied = true;
            }

            // ліва стіна ближньої будівлі від входу
            for (int i = 15; i <= 22; i++) {
                secondPlatz.tiles[i][7].occupied = true;
            }
        }


        // перший поверх
        // ліва і права стіна від зеленого виходу
        for (int i = 0; i <= 6; i++) {
            firstBuildFloor1.tiles[2][i].occupied = true;
            firstBuildFloor1.tiles[5][i].occupied = true;
        }
        firstBuildFloor1.tiles[1][6].occupied = true;
        //найбільш ліва стіна
        for (int i = 6; i <= 9; i++)
            firstBuildFloor1.tiles[0][i].occupied = true;

        //верхня стіна
        for (int i = 0; i <= 14; i++)
            firstBuildFloor1.tiles[i][9].occupied = true;

        //нижня стіна
        for (int i = 6; i <= 10; i++)
            firstBuildFloor1.tiles[i][6].occupied = true;

        for (int i = 0; i <= 5; i++)
            firstBuildFloor1.tiles[9][i].occupied = true;

        for (int i = 9; i <= 18; i++)
            firstBuildFloor1.tiles[i][0].occupied = true;

        for (int i = 0; i <= 6; i++)
            firstBuildFloor1.tiles[18][i].occupied = true;

        for (int i = 13; i <= 20; i++)
            firstBuildFloor1.tiles[i][6].occupied = true;

        // меблі в кімнаті з дошкою
        firstBuildFloor1.tiles[10][4].occupied = true;
        firstBuildFloor1.tiles[10][5].occupied = true;

        for (int i = 11; i <= 16; i++)
            firstBuildFloor1.tiles[i][2].occupied = true;

        firstBuildFloor1.tiles[17][6].occupied = true;
        firstBuildFloor1.tiles[16][6].occupied = true;

        // upper room
        for (int i = 10; i <= 16; i++) {
            firstBuildFloor1.tiles[8][i].occupied = true;
            firstBuildFloor1.tiles[19][i].occupied = true;
        }

        for (int i = 8; i <= 19; i++)
            firstBuildFloor1.tiles[i][16].occupied = true;

        for (int i = 17; i <= 20; i++)
            firstBuildFloor1.tiles[i][9].occupied = true;

        for (int i = 3; i <= 6; i++)
            firstBuildFloor1.tiles[20][i].occupied = true;

        for (int i = 9; i <= 13; i++)
            firstBuildFloor1.tiles[20][i].occupied = true;

        for (int i = 21; i <= 28; i++)
            firstBuildFloor1.tiles[i][13].occupied = true;

        firstBuildFloor1.tiles[31][13].occupied = true;
        firstBuildFloor1.tiles[30][13].occupied = true;
        firstBuildFloor1.tiles[30][3].occupied = true;
        for (int i = 13; i >= 3; i--)
            firstBuildFloor1.tiles[32][i].occupied = true;

        for (int i = 29; i <= 32; i++)
            firstBuildFloor1.tiles[31][3].occupied = true;

        for (int i = 0; i <= 3; i++) {
            firstBuildFloor1.tiles[29][i].occupied = true;
            firstBuildFloor1.tiles[23][i].occupied = true;
        }
        firstBuildFloor1.tiles[21][3].occupied = true;
        firstBuildFloor1.tiles[22][3].occupied = true;

        //диван
        firstBuildFloor1.tiles[21][5].occupied = true;
        firstBuildFloor1.tiles[21][6].occupied = true;

        //дзеркало
        firstBuildFloor1.tiles[27][10].occupied = true;
        firstBuildFloor1.tiles[27][11].occupied = true;

        //парти
        firstBuildFloor1.tiles[29][5].occupied = true;

        firstBuildFloor1.tiles[16][14].occupied = true;


        firstBuildFloor1.tiles[13][14].occupied = true;


        firstBuildFloor1.tiles[17][11].occupied = true;
        firstBuildFloor1.tiles[17][12].occupied = true;

        firstBuildFloor1.tiles[10][10].occupied = true;

        firstBuildFloor1.tiles[10][15].occupied = true;
        firstBuildFloor1.tiles[10][16].occupied = true;

        firstBuildFloor1.tiles[18][14].occupied = true;

        firstBuildFloor2.tiles[12][9].occupied = true;
        firstBuildFloor2.tiles[13][9].occupied = true;

        for (int i = 16; i <= 24; i++) {
            firstBuildFloor2.tiles[i][9].occupied = true;
        }
        for (int i = 0; i <= 10; i++)
            firstBuildFloor2.tiles[24][i].occupied = true;

        for (int i = 12; i <= 24; i++) {
            firstBuildFloor2.tiles[i][0].occupied = true;
        }

        for (int i = 0; i <= 3; i++) {
            firstBuildFloor2.tiles[12][i].occupied = true;
        }

        for (int i = 6; i <= 9; i++) {
            firstBuildFloor2.tiles[12][i].occupied = true;
        }

        for (int i = 0; i <= 11; i++) {
            firstBuildFloor2.tiles[i][3].occupied = true;
        }

        for (int i = 5; i <= 11; i++) {
            firstBuildFloor2.tiles[i][7].occupied = true;
        }

        for (int i = 4; i <= 7; i++) {
            firstBuildFloor2.tiles[1][i].occupied = true;
        }

        firstBuildFloor2.tiles[2][6].occupied = true;
        firstBuildFloor2.tiles[2][7].occupied = true;

        for (int i = 7; i <= 13; i++) {
            firstBuildFloor2.tiles[0][i].occupied = true;
        }

        for (int i = 0; i <= 7; i++) {
            firstBuildFloor2.tiles[i][13].occupied = true;
        }

        for (int i = 7; i <= 13; i++) {
            firstBuildFloor2.tiles[7][i].occupied = true;
        }

        firstBuildFloor2.tiles[1][12].occupied = true;
        firstBuildFloor2.tiles[2][12].occupied = true;
        firstBuildFloor2.tiles[5][12].occupied = true;
        firstBuildFloor2.tiles[6][12].occupied = true;

        firstBuildFloor2.tiles[2][10].occupied = true;
        firstBuildFloor2.tiles[3][10].occupied = true;

        firstBuildFloor2.tiles[23][5].occupied = true;
        firstBuildFloor2.tiles[23][4].occupied = true;
        firstBuildFloor2.tiles[23][3].occupied = true;

        firstBuildFloor2.tiles[19][7].occupied = true;
        firstBuildFloor2.tiles[20][7].occupied = true;

        firstBuildFloor2.tiles[22][7].occupied = true;
        firstBuildFloor2.tiles[23][7].occupied = true;

        for (int i = 16; i <= 20; i++)
            for (int j = 1; j <= 3; j++)
                firstBuildFloor2.tiles[i][j].occupied = true;

        for (int i = 5; i <= 34; i++) {
            if (i == 27)
                continue;
            kmz.tiles[i][2].occupied = true;
        }

        for (int i = 0; i <= 2; i++) {
            kmz.tiles[34][i].occupied = true;
            kmz.tiles[5][i].occupied = true;
        }

        for (int i = 0; i <= 13; i++) {
            kmz.tiles[0][i].occupied = true;
            kmz.tiles[39][i].occupied = true;
        }

        for (int i = 28; i <=33; i++) {
            for (int j = 6; j <= 11; j++)
                kmz.tiles[i][j].occupied = true;
        }

        for (int i = 5; i <= 35; i++) {
            for (int j = 18; j <= 19; j++)
                kmz.tiles[i][j].occupied = true;
        }



        //@TODO Ivan's edits
        firstPlatz.tiles[18][9].occupied = true;

        for (int i = 8; i <= 14; i++) {
            thirdBuild.tiles[i][14].occupied = true;
            thirdBuild.tiles[i][18].occupied = true;
        }

        thirdBuild.tiles[8][13].occupied = true;
        thirdBuild.tiles[8][12].occupied = true;

        for (int i = 8; i <= 11; i++) {
            thirdBuild.tiles[9][i].occupied = true;
        }

        for (int i = 5; i <= 8; i++) {
            thirdBuild.tiles[10][i].occupied = true;
        }

        for (int i = 10; i <= 21; i++) {
            thirdBuild.tiles[i][5].occupied = true;
            thirdBuild.tiles[i][0].occupied = true;
        }

        thirdBuild.tiles[11][4].occupied = true;
        thirdBuild.tiles[12][4].occupied = true;

        for (int i = 0; i <= 6; i++) {
            thirdBuild.tiles[21][i].occupied = true;
        }

        for (int i = 7; i <= 12; i++) {
            thirdBuild.tiles[i][1].occupied = true;
        }

        thirdBuild.tiles[8][2].occupied = true;
        thirdBuild.tiles[7][2].occupied = true;
        thirdBuild.tiles[7][3].occupied = true;
        thirdBuild.tiles[7][4].occupied = true;

        for (int i = 4; i <= 7; i++) {
            thirdBuild.tiles[6][i].occupied = true;
        }

        for (int i = 7; i <= 10; i++) {
            thirdBuild.tiles[5][i].occupied = true;
        }

        for (int i = 10; i <= 13; i++) {
            thirdBuild.tiles[4][i].occupied = true;
        }

        for (int i = 0; i <= 4; i++) {
            thirdBuild.tiles[i][13].occupied = true;
            thirdBuild.tiles[i][19].occupied = true;
        }

        for (int i = 13; i <= 19; i++) {
            thirdBuild.tiles[0][i].occupied = true;
        }

        thirdBuild.tiles[4][21].occupied = true;
        thirdBuild.tiles[4][20].occupied = true;
        thirdBuild.tiles[5][22].occupied = true;
        thirdBuild.tiles[5][23].occupied = true;
        thirdBuild.tiles[5][24].occupied = true;

        thirdBuild.tiles[6][25].occupied = true;
        thirdBuild.tiles[6][26].occupied = true;
        thirdBuild.tiles[6][27].occupied = true;

        thirdBuild.tiles[7][27].occupied = true;
        thirdBuild.tiles[7][28].occupied = true;
        thirdBuild.tiles[7][29].occupied = true;
        thirdBuild.tiles[8][30].occupied = true;

        for (int i = 8; i <= 12; i++) {
            thirdBuild.tiles[i][31].occupied = true;
        }

        for (int i = 13; i <= 21; i++) {
            thirdBuild.tiles[i][32].occupied = true;
            thirdBuild.tiles[i][26].occupied = true;
        }

        for (int i = 26; i <= 32; i++) {
            thirdBuild.tiles[21][i].occupied = true;
        }

        thirdBuild.tiles[12][27].occupied = true;
        thirdBuild.tiles[11][27].occupied = true;
        thirdBuild.tiles[10][26].occupied = true;
        thirdBuild.tiles[10][25].occupied = true;
        thirdBuild.tiles[10][24].occupied = true;

        thirdBuild.tiles[9][23].occupied = true;
        thirdBuild.tiles[9][22].occupied = true;
        thirdBuild.tiles[9][21].occupied = true;

        thirdBuild.tiles[8][20].occupied = true;
        thirdBuild.tiles[8][19].occupied = true;

        // utils
        thirdBuild.tiles[7][5].occupied = true;

        for (int i = 1; i <= 4; i++) {
            thirdBuild.tiles[20][i].occupied = true;
        }

        for (int i = 14; i <= 18; i++) {
            thirdBuild.tiles[i][1].occupied = true;
        }

        thirdBuild.tiles[2][15].occupied = true;
        thirdBuild.tiles[3][15].occupied = true;
        thirdBuild.tiles[3][16].occupied = true;
        thirdBuild.tiles[2][16].occupied = true;

        thirdBuild.tiles[8][22].occupied = true;

        thirdBuild.tiles[7][26].occupied = true;

        thirdBuild.tiles[17][29].occupied = true;

        thirdBuild.tiles[20][31].occupied = true;

        thirdBuild.tiles[20][27].occupied = true;

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++)
                biblio.tiles[i][j].occupied = true;
        }

        biblio.tiles[8][2].occupied = true;
        biblio.tiles[9][2].occupied = true;
        for (int i = 8; i <= 24; i++) {
            biblio.tiles[i][1].occupied = true;
            biblio.tiles[i][10].occupied = true;
        }
        for (int i = 2; i <= 8; i++) biblio.tiles[0][i].occupied = true;
        biblio.tiles[1][8].occupied = true;
        biblio.tiles[2][8].occupied = true;
        for (int i = 2; i <= 10; i++) biblio.tiles[24][i].occupied = true;

        for (int i = 8; i <= 9; i++) {
            for (int j = 7; j <= 10; j++)
                biblio.tiles[i][j].occupied = true;
        }

        for (int i = 2; i <= 8; i++) biblio.tiles[i][9].occupied = true;

        for (int i = 2; i <= 6; i++) {
            for (int j = 7; j <= 9; j++)
                biblio.tiles[i][j].occupied = true;
        }

        biblio.tiles[1][6].occupied = true;
        biblio.tiles[1][3].occupied = true;

        biblio.tiles[10][9].occupied = true;
        biblio.tiles[11][9].occupied = true;
        biblio.tiles[12][9].occupied = true;
        biblio.tiles[13][9].occupied = true;
        biblio.tiles[15][9].occupied = true;
        biblio.tiles[16][9].occupied = true;
        biblio.tiles[17][9].occupied = true;
        biblio.tiles[18][9].occupied = true;
        biblio.tiles[20][9].occupied = true;
        biblio.tiles[21][9].occupied = true;
        biblio.tiles[22][9].occupied = true;
        biblio.tiles[23][9].occupied = true;


        for (int i = 11; i <= 20; i+=3) {
            biblio.tiles[i][3].occupied = true;
            biblio.tiles[i][7].occupied = true;
        }

        for (int i = 0; i <= 9; i++) fido.tiles[0][i].occupied = true;
        for (int i = 0; i <= 6; i++) {
            fido.tiles[3][i].occupied = true;
            fido.tiles[4][i].occupied = true;
        }
        for (int i = 0; i <= 14; i++) {
            fido.tiles[i][9].occupied = true;
            fido.tiles[i][0].occupied = true;
        }
        for (int i = 0; i <= 9; i++) fido.tiles[14][i].occupied = true;

        fido.tiles[5][1].occupied = true;
        fido.tiles[5][2].occupied = true;

        fido.tiles[12][5].occupied = true;
        fido.tiles[12][3].occupied = true;
        fido.tiles[10][2].occupied = true;
        fido.tiles[8][2].occupied = true;
        fido.tiles[6][3].occupied = true;
        fido.tiles[6][5].occupied = true;
        fido.tiles[8][6].occupied = true;
        fido.tiles[10][6].occupied = true;

        for (int i = 8; i <= 11; i++) {
            fourthBuild.tiles[2][i].occupied = true;
            fourthBuild.tiles[5][i].occupied = true;
        }

        for (int i = 5; i <= 11; i++)  {
            fourthBuild.tiles[i][8].occupied = true;
            fourthBuild.tiles[i][0].occupied = true;
        }

        for (int i = 0; i <=8;i++) fourthBuild.tiles[11][i].occupied = true;

        for (int i = 2; i <= 4; i++)
            for (int j = 0; j <= 2; j++)
                fourthBuild.tiles[i][j].occupied = true;

        for (int i = 0; i <= 2; i++)
            for (int j = 3; j <= 4; j++)
                fourthBuild.tiles[i][j].occupied = true;


        for (int i = 4; i <=8;i++) fourthBuild.tiles[0][i].occupied = true;

        fourthBuild.tiles[1][8].occupied = true;

        fourthBuild.tiles[5][1].occupied = true;
        fourthBuild.tiles[10][1].occupied = true;
        fourthBuild.tiles[3][4].occupied = true;
        fourthBuild.tiles[1][6].occupied = true;
        fourthBuild.tiles[1][7].occupied = true;
        fourthBuild.tiles[2][7].occupied = true;

        for (int i = 3; i <= 5; i++)  fourthBuild.tiles[10][i].occupied = true;

    } //Method end
}

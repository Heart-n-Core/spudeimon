package ukma.ipz.level;

import com.badlogic.gdx.graphics.Texture;
import ukma.ipz.Action;
import ukma.ipz.Dialog;
import ukma.ipz.GameEntry;

public class LevelManager {
    LevelScreen levelScreen;
    GameEntry game;
    Player player = new Player();

    Level initial;
    Level firstPlatz;
    Level secondPlatz;

    Level firstBuildFloor1;
    Level firstBuildFloor2;

    public LevelManager() {
        loadFirst();
    }

    public void setup(GameEntry game) {
        this.game = game;
        levelScreen = new LevelScreen(game, initial, player);
        loadLevel(initial);
    }

    private void loadFirst(){
        initial = new Level(new Texture("isometric\\levels\\1_entrance.jpg"), 10, 7, 1, 0, 8, 5);
        firstPlatz = new Level(new Texture("isometric\\levels\\first_platz.png"),33, 21, 3, 5);
        secondPlatz = new Level(new Texture("isometric\\levels\\second_platz.png"),35, 26, 3, 5);
        firstBuildFloor1 = new Level(new Texture("isometric\\levels\\firstBuildFloor1.png"), 33, 17, 1, 1, 11, 7);
        firstBuildFloor2 = new Level(new Texture("isometric\\levels\\firstBuildFloor2.png"), 25, 14, 1, 1, 8, 5);


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
        Action fEtofC = () -> {
            firstBuildFloor1.X=26;
            firstBuildFloor1.Y=1;
//            loadLevel(firstPlatz);
            loadLevel(firstBuildFloor1);
        };
        initial.tiles[4][4].action=fEtofC;
//        initial.tiles[5][4].action=fEtofC;
        initial.tiles[5][3].occupied=true;
        initial.otherTextures.add(new LevelTexture("intro_girl.png", 5, 3 ));
        String[] dialogIntro = {"Привіт, рефрешику :)", "Мене звати Беатріче.\nА ти, либонь, та сама \"темна конячка\",\nпро яку всі так балакають.", "Тут розпочнеться твій шлях спудея.\nПопереду буде багато викликів.\nХоча, якщо ти тут, то наснаги тобі не бракує.", "Але годі балакати.\nШлях до істини лежить у дебатах!\nОтож en garde, monsieur!"};

        Action dial1 = () -> {
            initial.dialog = new Dialog(dialogIntro, () -> {
                System.out.println("Dialog end");
                //@TODO first fight starts here


                //One time dialog
//                initial.tiles[5][3].interaction = null;
            });
        };
        initial.tiles[5][3].interaction = dial1;
        Action firstBtoInitial = () -> {
            initial.X=4;
            initial.Y=3;
            loadLevel(initial);
        };
        firstBuildFloor1.tiles[26][0].action=firstBtoInitial;firstBuildFloor1.tiles[25][0].action=firstBtoInitial;firstBuildFloor1.tiles[24][0].action=firstBtoInitial;firstBuildFloor1.tiles[27][0].action=firstBtoInitial;firstBuildFloor1.tiles[28][0].action=firstBtoInitial;

        Action firstBtoFirstPlatz = () -> {
            firstPlatz.X=9;
            firstPlatz.Y=6;
            loadLevel(firstPlatz);
        };
        firstBuildFloor1.tiles[3][0].action=firstBtoFirstPlatz;firstBuildFloor1.tiles[4][0].action=firstBtoFirstPlatz;
        firstPlatz.tiles[9][4].action = () -> {
            firstBuildFloor1.X = 4;
            firstBuildFloor1.Y = 1;
            loadLevel(firstBuildFloor1);
        };

        firstBuildFloor1.tiles[29][12].action = () -> {
            firstBuildFloor2.X = 14;
            firstBuildFloor2.Y = 9;
            loadLevel(firstBuildFloor2);
        };

        firstBuildFloor2.tiles[14][9].action = () -> {
            firstBuildFloor1.X = 29;
            firstBuildFloor1.Y = 12;
            loadLevel(firstBuildFloor1);
        };
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
        Action secondPlatzToFirstPlatz = () -> {
            firstPlatz.X=31;
            firstPlatz.Y=9;
            loadLevel(firstPlatz);
        };
        secondPlatz.tiles[22][7].action = secondPlatzToFirstPlatz; secondPlatz.tiles[23][7].action = secondPlatzToFirstPlatz;

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
    }

    void loadLevel(Level level){
        levelScreen.canResize = false;
        levelScreen = new LevelScreen(game, level, player);
        game.setScreen(levelScreen);
        levelScreen.canResize = true;
    }

    public Level getInitialLevel() {
        return initial;
    }
    public Player getPlayer() {
        return player;
    }
}

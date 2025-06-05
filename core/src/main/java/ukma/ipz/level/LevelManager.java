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
            firstPlatz.X=9;
            firstPlatz.Y=5;
            loadLevel(firstPlatz);};
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
        firstPlatz.tiles[9][4].action=() -> {
            initial.X=4;
            initial.Y=3;
            loadLevel(initial);};

        // стіна першого корпусу на вихід в плац
        for (int i = 3; i <= 16; i++) {
            if (i == 9)
                continue;
            firstPlatz.tiles[i][4].occupied=true;
        }

        // ліва межа до виходу на Іллінській
        for (int i = 0; i <= 4; i++)
            firstPlatz.tiles[16][i].occupied=true;

        // права межа до виходу на Іллінській
        for (int i = 0; i <= 8; i++) {
            firstPlatz.tiles[18][i].occupied=true;
        }

        // ворота в перший плац
        firstPlatz.tiles[2][5].occupied=true;
        firstPlatz.tiles[2][6].occupied=true;

        // 3 корпус

        // стіна біля воріт
        firstPlatz.tiles[3][6].occupied=true;
        firstPlatz.tiles[4][6].occupied=true;


        // стіна перпендикулярна попередній (ліва стіна 3 плацу)
        firstPlatz.tiles[4][7].occupied=true;

        // стіна перпендикулярна попередній (нижня стіна впадини 3 плацу)
        firstPlatz.tiles[3][7].occupied=true;

        // стіна перпендикулярна попередній (ліва стіна впадини 3 плацу)
        firstPlatz.tiles[2][8].occupied=true;
        firstPlatz.tiles[2][9].occupied=true;

//         стіна перпендикулярна попередній (верхня стіна впадини 3 плацу)
        firstPlatz.tiles[3][10].occupied=true;
        firstPlatz.tiles[4][10].occupied=true;
        firstPlatz.tiles[5][10].occupied=true;
        firstPlatz.tiles[6][10].occupied=true;

        // ліва стіна 3 плацу, яка ближча жо горького
        firstPlatz.tiles[6][11].occupied=true;

        // нижня стіна верхньої впадини
        firstPlatz.tiles[5][11].occupied=true;
        firstPlatz.tiles[4][11].occupied=true;
        firstPlatz.tiles[3][11].occupied=true;

        //ліва стіна верхньої впадини
        firstPlatz.tiles[3][12].occupied=true;
        for (int i = 12; i <= 20; i++)
            firstPlatz.tiles[4][i].occupied=true;

        // прохід за 2 корпусом
        for (int i = 5; i <= 9; i++)
            firstPlatz.tiles[i][17].occupied=true;

        firstPlatz.tiles[10][17].occupied=true;
        firstPlatz.tiles[10][16].occupied=true;
        firstPlatz.tiles[10][15].occupied=true;
        firstPlatz.tiles[10][14].occupied=true;

        for (int i = 10; i <= 16; i++)
            firstPlatz.tiles[i][14].occupied=true;


        // 2 корпус
        for (int i = 9; i <= 18; i++) {
            for (int j = 11; j < 12; j++) {
                firstPlatz.tiles[i][j].occupied=true;
            }
        }

        // бібліо
        // ліва будівля
        for (int i = 15; i <= 20; i++) {
            firstPlatz.tiles[15][i].occupied=true;
        }
        //власне вхід бібліо + музей
        for (int i = 15; i <= 24; i++) {
            firstPlatz.tiles[i][17].occupied=true;
            firstPlatz.tiles[i][18].occupied=true;

        }

        //власне бібліо
        for (int i = 18; i <= 20; i++) {
            for (int j = 13; j <= 16; j++) {
                firstPlatz.tiles[i][j].occupied=true;
            }
        }

        // церква
        for (int i = 22; i <= 25; i++) {
            for (int j = 14; j <= 16; j++) {
                firstPlatz.tiles[i][j].occupied=true;
            }
        }

        // вся ділянка біля недобудованого корпусу + лікарні
        for (int i = 19; i <= 32; i++) {
            for (int j = 0; j <= 8; j++) {
                firstPlatz.tiles[i][j].occupied=true;
            }
        }

        // будівля між плацами
        for (int i = 28; i <= 30; i++) {
            for (int j = 11; j <= 19; j++) {
                firstPlatz.tiles[i][j].occupied=true;
            }
        }
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

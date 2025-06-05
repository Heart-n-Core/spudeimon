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

        // стіна перпендикулярна попередній
//        firstPlatz.tiles.

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

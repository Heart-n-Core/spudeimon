package ukma.ipz.level;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import ukma.ipz.Action;
import ukma.ipz.GameEntry;

public class LevelManager {
    LevelScreen levelScreen;
    GameEntry game;
    Player player = new Player();

    Level initial;
    Level firstCorridor;

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
            firstCorridor.X=4;
            firstCorridor.Y=3;
            loadLevel(firstCorridor);};
        initial.tiles[4][4].action=fEtofC;
        initial.tiles[5][4].action=fEtofC;

        firstCorridor = new Level(new Texture("isometric\\real_bgr.png"),40, 26, 4, 3);
        firstCorridor.tiles[3][3].action=() -> {
            initial.X=5;
            initial.Y=3;
            loadLevel(initial);};
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

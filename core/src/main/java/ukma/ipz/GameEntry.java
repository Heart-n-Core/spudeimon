package ukma.ipz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ukma.ipz.fight.Types;
import ukma.ipz.level.LevelManager;
import ukma.ipz.level.LevelScreen;

public class GameEntry extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    public OrthographicCamera cam;

    public int level = 1;
    public String playerName = "Гравець";
    public Types type = Types.FI;
//public Types type = Types.FHN;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont();
//        viewport = new FitViewport(8, 5);
        viewport = new FitViewport(16, 9);
        font.setUseIntegerPositions(false);

        LevelManager levelManager = new LevelManager();
        levelManager.setup(this);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}


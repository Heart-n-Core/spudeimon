package ukma.ipz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ukma.ipz.level.LevelManager;
import ukma.ipz.level.LevelScreen;

public class GameEntry extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    public OrthographicCamera cam;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);
        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);

        LevelManager manager = new LevelManager();
//        LevelScreen screen = new LevelScreen(this, manager.getInitialLevel(), manager.getPlayer());
//        manager.setup(this, screen);
//        this.setScreen(screen);
        manager.setup(this);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}


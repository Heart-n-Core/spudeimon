package ukma.ipz.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import ukma.ipz.Action;
import ukma.ipz.Direction;
import ukma.ipz.GameEntry;
import ukma.ipz.ProlongedAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LevelScreen implements Screen {

    final GameEntry game;
    final Level level;

    Player player = new Player();


    public LevelScreen(final GameEntry game, Level level) {
        this.game = game;
        this.level = level;
        player.sprite.setPosition(level.X, level.Y);
//        game.cam = new OrthographicCamera(8, 5);
        game.cam = new OrthographicCamera(17, 11);
        game.cam.position.set(level.X +0.5f, level.Y +0.5f, 0);
        game.cam.update();

        level.tiles[1][1].occupied=true;
        level.tiles[2][1].occupied=true;
        level.tiles[3][1].occupied=true;
        level.tiles[5][0].action =() -> {
            System.out.println("Tile action triggered");
            level.tiles[6][0].occupied=!level.tiles[6][0].occupied;
        };
        level.tiles[0][1].action =() -> {
            System.out.println("Tile action 2 triggered");
            level.tiles[0][2].occupied=true;
        };
        level.tiles[0][25].occupied=true;
        level.tiles[0][25].interaction=() -> {
            System.out.println("Interaction triggered");
            blockAction(500);
        };
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private boolean noAction = true;
    int currentKey = 0;

    private void input(){
        if (noAction) {
            if (Gdx.input.isKeyPressed(currentKey)) {
                noAction = false;
                move(currentKey);
            }else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                currentKey=Input.Keys.UP;
                move(currentKey);
            }else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                currentKey=Input.Keys.DOWN;
                move(currentKey);
            }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                currentKey=Input.Keys.LEFT;
                move(currentKey);
            }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                currentKey=Input.Keys.RIGHT;
                move(currentKey);
            }else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                interact();
            }
        }
    }

    List<ProlongedAction> actions = new ArrayList<ProlongedAction>();

    private void logic(){
        float delta = Gdx.graphics.getDeltaTime();
        List<ProlongedAction> toRemove = new ArrayList<>();
        for (ProlongedAction action : actions) {
            if (!action.execute(delta)) {
                toRemove.add(action);
            }
        }
        actions.removeAll(toRemove);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FPS: "+Gdx.graphics.getFramesPerSecond()+"\n");
//        stringBuilder.append("Player  X:"+player.sprite.getX()+" Y:"+player.sprite.getY()+"\n");
        stringBuilder.append("Player  X:"+level.X+" Y:"+level.Y+"\n");
        stringBuilder.append("Player current texture: "+player.sprite.getTexture().toString()+"\n");
        stringBuilder.append("Camera X:"+game.cam.position.x+" Y:"+game.cam.position.y+"\n");
        telemetry = stringBuilder.toString();


    }

    private void blockAction(long delay){
        noAction = false;
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                noAction = true;
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, delay);
    }
    private void blockAction(long delay, Action action){
        noAction = false;
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                noAction = true;
                action.execute();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, delay);
    }

    float camSpeed=10f;
    float camDuration = 1/camSpeed;

    private void move(int currentKey){
        //Calculate move
        float movCamX = 0;
        float movCamY = 0;
        int newX=0;
        int newY=0;
        switch (currentKey) {
            case Input.Keys.RIGHT:{movCamX=camSpeed;newX=1; player.move(Direction.RIGHT); break;}
            case Input.Keys.LEFT:{movCamX=-camSpeed;newX=-1; player.move(Direction.LEFT); break;}
            case Input.Keys.UP:{movCamY=camSpeed;newY=1; player.move(Direction.UP); break;}
            case Input.Keys.DOWN:{movCamY=-camSpeed;newY=-1; player.move(Direction.DOWN); break;}
        }
        //Move logic
        newX+=level.X;
        newY+=level.Y;
        if (!isInRange(newX, newY)){
            player.finalizeMove();
            blockAction(50);
            return;
        }
        Tile tile = level.tiles[newX][newY];
        if (tile.occupied){
            player.finalizeMove();
            blockAction(50);
            return;
        }
        level.X=newX;
        level.Y=newY;
        blockAction((long) (camDuration*1000), () -> {
            player.finalizeMove();
            game.cam.position.set(level.X+0.5f, level.Y+0.5f, 0);
            executeAction(tile.action);
        });
        //Move render
        float finalMovCamX = movCamX;
        float finalMovCamY = movCamY;
        ProlongedAction camMove = new ProlongedAction(camDuration, delta1 -> {
            game.cam.position.add(finalMovCamX *delta1, finalMovCamY *delta1, 0);
            player.sprite.setPosition(game.cam.position.x-0.5f,game.cam.position.y-0.5f);
        }, actions);
        actions.add(camMove);
    }

    private void interact(){
        int newX=0;
        int newY=0;
        switch (player.direction){
            case RIGHT:{newX=1; break;}
            case LEFT:{newX=-1; break;}
            case UP:{newY=1; break;}
            case DOWN:{newY=-1; break;}
        }
        newX+=level.X;
        newY+=level.Y;
        if (!isInRange(newX, newY)){
            return;
        }
        executeInteraction(level.tiles[newX][newY].interaction);
    }

    private boolean isInRange(int newX, int newY){
        return !(newX<0||newX>=level.sizeX||newY<0||newY>=level.sizeY);
    }

    private void executeAction(Action action){
        if (action!=null)action.execute();
    }
    private void executeInteraction(Action interaction){
        if (interaction!=null)interaction.execute();
    }

    private String telemetry="";

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        game.cam.update();
        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();

        level.sprite.draw(game.batch);
        player.sprite.draw(game.batch);

        game.font.draw(game.batch, telemetry, game.cam.position.x-8, game.cam.position.y+5.5f);
//        game.font.draw(game.batch, telemetry, game.cam.position.x-4, game.cam.position.y+2.5f);


        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

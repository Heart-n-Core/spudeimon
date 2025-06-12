package ukma.ipz.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    final Player player;
    static BitmapFont font  = new BitmapFont();




    public LevelScreen(final GameEntry game, Level level, Player player) {
        this.game = game;
        this.level = level;
        this.player = player;
        font.setUseIntegerPositions(false);
        player.move(Direction.DOWN);
        player.finalizeMove();
        player.sprite.setPosition(level.X, level.Y);
        game.cam = new OrthographicCamera(level.scaleX, level.scaleY);
        game.cam.position.set(level.X +0.5f, level.Y +0.5f, 0);
        game.cam.update();
        blockAction(250);
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

    public boolean noAction = true;
    public int currentKey = 0;
    boolean dialogNow = false;

    private void input(){
        if (noAction) {
            if (dialogNow) {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                   if (level.dialog.hasNext()){
                       level.dialog.nextLine();
                   }
                   else {
                       dialogNow=false;
                        if (level.dialog.afterAction!=null){level.dialog.afterAction.execute();}
                       level.dialog=null;
                   }
                    blockAction(500);
                }
                return;
            }
//            else if (Gdx.input.isKeyPressed(currentKey)) {
//                noAction = false;
//                move(currentKey);}
            if (externalDirection!=null){
                int key = 0;
                switch (externalDirection){
                    case LEFT: key=Input.Keys.LEFT;break;
                    case RIGHT: key=Input.Keys.RIGHT;break;
                    case UP: key=Input.Keys.UP;break;
                    case DOWN: key=Input.Keys.DOWN;break;
                }
                externalDirection=null;
                move(key);
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

             if (Gdx.input.isKeyPressed(Input.Keys.F3)) {
                displayTelemetry = !displayTelemetry;
                blockAction(100);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F2)) {
                noClip = !noClip;
                blockAction(100);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
                game.level+=1;
                blockAction(1000);
            }
        }
    }

    List<ProlongedAction> actions = new ArrayList<>();

    //Debug vars

    boolean noClip = false;
    boolean displayTelemetry = true;

    public Direction externalDirection;
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
        stringBuilder.append("Player  X:"+level.X+" Y:"+level.Y+"\n");
//        stringBuilder.append("Player current texture: "+player.sprite.getTexture().toString()+"\n");
        stringBuilder.append("Camera X:"+game.cam.position.x+" Y:"+game.cam.position.y+"\n");
        stringBuilder.append("Player level: "+game.level+"\n");
        stringBuilder.append("noClip "+(noClip?"ENABLED":"DISABLED")+"\n");
        telemetry = stringBuilder.toString();


    }

    public void blockAction(long delay){
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
    public void blockAction(long delay, Action action){
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

    public void move(int currentKey){
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
        if (tile.occupied&&!noClip){
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
        blockAction(100);
        if(level.dialog!=null){
            dialogNow=true;
        }
    }

    private boolean isInRange(int newX, int newY){
        return !(newX<0||newX>=level.sizeX||newY<0||newY>=level.sizeY);
    }

    private void executeAction(Action action){
        if (action!=null&&!noClip)action.execute();
        if(level.dialog!=null){
            dialogNow=true;
        }
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
        for (LevelTexture levelTexture : level.otherTextures){
            if (levelTexture!=null&&levelTexture.sprite!=null){
                levelTexture.sprite.draw(game.batch);
            }
        }

        font.getData().setScale((float) level.scaleY /Gdx.graphics.getHeight());
        if (displayTelemetry){
            font.draw(game.batch, telemetry, game.cam.position.x- (float) level.scaleX /2, game.cam.position.y+ (float) level.scaleY /2-0.5f);
        }

        float lvlScaleCoof = level.scaleX*0.075f;
        player.lvlBar.setSize(lvlScaleCoof, lvlScaleCoof);
        float camCordx = game.cam.position.x- (float) level.scaleX /2;
        float camCordy = game.cam.position.y- (float) level.scaleY /2;
        player.lvlBar.setPosition(camCordx, camCordy );
        player.lvlBar.draw(game.batch);
        float XNumShift = level.scaleX*0.01f;
        player.n1.setSize(3*XNumShift, 3*XNumShift);player.n2.setSize(3*XNumShift, 3*XNumShift);player.n3.setSize(3*XNumShift, 3*XNumShift);
        player.n1.setPosition(8*XNumShift+camCordx, camCordy);player.n2.setPosition(11*XNumShift+camCordx, camCordy);player.n3.setPosition(14*XNumShift+camCordx, camCordy);
        player.n1.setTexture(player.numbers[game.level/100]);player.n2.setTexture(player.numbers[(game.level/10)%10]);player.n3.setTexture(player.numbers[game.level%10]);
        player.n1.draw(game.batch);player.n2.draw(game.batch);player.n3.draw(game.batch);
        if (dialogNow){
            level.dialog.render(game.batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), level.scaleX, level.scaleY, game.cam.position.x, game.cam.position.y);
//            level.dialog.render(game.batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 8, 5, game.cam.position.x, game.cam.position.y);
        }

        game.batch.end();
    }

    boolean canResize=false;

    @Override
    public void resize(int width, int height) {
        if (canResize){
            game.viewport.update(width, height, true);
        }
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

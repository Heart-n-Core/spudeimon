package ukma.ipz.fight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import ukma.ipz.Action;
import ukma.ipz.Dialog;
import ukma.ipz.GameEntry;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class RefreshScreen implements Screen {

    final GameEntry game;
    static BitmapFont font = new BitmapFont();
    States state = States.ACTION_CHOOSE;

    Fighter player;
    //    Fighter opponent;
    Action afterAction;
    float width = 16;
    float height = 9;
    Sprite playerS = new Sprite(new Texture("isometric\\fights\\mcFront.png"));
    Sprite actionSelector = new Sprite(new Texture("isometric\\dialog_bar.png"));
    Sprite actionDescriptor = new Sprite(new Texture("isometric\\dialog_bar.png"));
    Sprite selectorFocus = new Sprite(new Texture("isometric\\fights\\selector_focus.png"));
    Sprite bgr = new Sprite(new Texture("isometric\\fights\\black.png"));

    public RefreshScreen(GameEntry game, Action afterAction) {
        this.game = game;
        this.afterAction = afterAction;
//        this.fight = fight;
//        fight.bgr.setPosition(0, 0);
//        fight.bgr.setSize(16, 9);
        bgr.setPosition(0, 0);
        bgr.setSize(16, 9);
        font.setUseIntegerPositions(false);

        float charsSize = 4.5f;
        playerS.setSize(charsSize, charsSize);
        playerS.setPosition(2, 2);
        playerS.setSize(charsSize, charsSize);
        playerS.setPosition(9, 4);
        playerS.setPosition(5, 3);


        actionSelector.setSize(12, 8);
        actionSelector.setPosition(-0.5f, 0);
//        actionDescriptor.setSize(6, 18);
//        actionDescriptor.setPosition(10.2f, -0.5f);
        actionDescriptor.setSize(6, 21);
        actionDescriptor.setPosition(10.2f, -1.35f);
        float selectorFwidth = 5;
        selectorFocus.setSize(selectorFwidth, (float) (selectorFwidth / 7.4));
        selectorFocus.setPosition(0.55f, 1.28f);

//        player = new Fighter()
        player = new Fighter("Гравець", 1, Types.FI);
        HashMap<Move, Types> factions = new HashMap<>();
        Move FI = new Move(player, "ФІ", 0);
        FI.description="Інформатик — це майстер логіки,\r\nданих та алгоритмів. Він бачить\r\nсвіт як гігантську систему, яку\r\nможна оптимізувати,\r\nавтоматизувати та 'зламати'.\r\nЙого сила у швидкості обчислень\r\nта здатності знаходити патерни\r\nтам, де інші бачать хаос.";
        Move FHN = new Move(player, "ФГН", 0);
        factions.put(FI, Types.FI);
        factions.put(FHN, Types.FHN);
        Move FPVN = new Move(player, "ФПВН", 0);
        factions.put(FPVN, Types.FPVN);

        Move FPRN = new Move(player, "ФПРН", 0);
        factions.put(FPRN, Types.FPRN);

        Move KMBS = new Move(player, "КМБС", 0);
        factions.put(KMBS, Types.KMBS);

        Move FOZ = new Move(player, "ФОЗ", 0);
        factions.put(FOZ, Types.FOZ);

        Move FSNST = new Move(player, "ФСНСТ", 0);
        factions.put(FSNST, Types.FSNST);

        Move FEN = new Move(player, "ФЕН", 0);
        factions.put(FEN, Types.FEN);

        for (Move move : factions.keySet()){
            move.moveAction = ()->{game.type=factions.get(move);exitFight();};
        }
        Move prev = new Move(player, "<- Попередні факультети",0);
        prev.moveAction = ()->{
            if (set<3){set++;}else set=0;
            switch (set){
                case 0: {player.moveSet[2]=FI; player.moveSet[3]=FHN;break;}
                case 1:{player.moveSet[2]=FPRN; player.moveSet[3]=FPVN;break;}
                case 2:{player.moveSet[2]=FSNST; player.moveSet[3]=FOZ;break;}
                case 3:{player.moveSet[2]=KMBS; player.moveSet[3]=FEN;break;}
            }
        };

        Move next = new Move(player, "Наступні факультети ->", 0);
        next.moveAction = ()->{
            if (set>0){set--;}else set=3;
            switch (set){
                case 0: {player.moveSet[2]=FI; player.moveSet[3]=FHN;break;}
                case 1:{player.moveSet[2]=FPRN; player.moveSet[3]=FPVN;break;}
                case 2:{player.moveSet[2]=FSNST; player.moveSet[3]=FOZ;break;}
                case 3:{player.moveSet[2]=KMBS; player.moveSet[3]=FEN;break;}
            }
        };
        player.moveSet=new Move[]{prev, next, FI, FHN};
//        opponent =new Fighter(fight.opponentName, fight.opponentLvl, fight.opponentType);
//        player.enemy = opponent;
//        opponent.enemy = player;
        blockAction(500);
    }
    int set=0;

    private void exitFight() {
//        if (fight.fightResult){fight.winAction.execute();}
        afterAction.execute();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    private boolean noAction = true;

    private int selectedAction = 1;
    private boolean firstX = true, firstY = true;

    private void input() {
        Sprite currentMovable = selectorFocus;
        currentMovable = null;
        if (noAction) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                //Breakpoint
                blockAction(100);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
                game.type = Types.FI;
                exitFight();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//                fight.fightResult = true;
//                exitFight();
                if (currentMovable != null) {
                    System.out.println(currentMovable.getX() + "   " + currentMovable.getY());
                }
                if (player.moveSet[currentSelectedMoveIndex].OPCost <= player.OP) {
                    performMove(player);
                }
            }
            float moveCoof = 0.01f;
            if (currentMovable != null) {
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    currentMovable.translateX(moveCoof);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    currentMovable.translateX(-1 * moveCoof);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    currentMovable.translateY(moveCoof);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    currentMovable.translateY(-1 * moveCoof);
                }
            }

            int selectSwitchDelayMs = 200;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                firstX = !firstX;
                blockAction(selectSwitchDelayMs);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                firstX = !firstX;
                blockAction(selectSwitchDelayMs);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                firstY = !firstY;
                blockAction(selectSwitchDelayMs);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                firstY = !firstY;
                blockAction(selectSwitchDelayMs);
            }

        }
    }

    private void performMove(Fighter fighter) {
        state = States.ACTION_CHOOSE;
//        Move move = Move.getSkipMove(fighter);
//        displayString=fighter.name+" пропускає хід";
//        if (fighter.nextMove==4){
        Move move = fighter.moveSet[currentSelectedMoveIndex];
//        fighter.OP -= move.OPCost;
//        displayString = fighter.name + " застосовує " + move.name;


        int perLineDelay = 750;
        Move finalMove = move;

        blockAction(300, () -> {
            //TODO animation could begin there
            if (validateMessage(finalMove.messages[0])) displayString = finalMove.messages[0];
            blockAction(validateMessage(finalMove.messages[0]) ? perLineDelay : 1, () -> {
                if (validateMessage(finalMove.messages[1])) displayString = finalMove.messages[1];
                blockAction(validateMessage(finalMove.messages[1]) ? perLineDelay : 1, () -> {
                    if (validateMessage(finalMove.messages[2])) displayString = finalMove.messages[2];
                    blockAction(validateMessage(finalMove.messages[2]) ? perLineDelay : 1, () -> {
//                        if (validateMessage(finalMove.messages[3]))displayString = finalMove.messages[3];
//                        blockAction(validateMessage(finalMove.messages[3])?perLineDelay:1, () -> {
                        if (currentSelectedMoveIndex != 0&& currentSelectedMoveIndex != 1) {
                            state = States.OPPONENT_CAST;
                            displayString = "Гравець обирає " + move.name + "!";
                        }
                        blockAction(currentSelectedMoveIndex != 0&& currentSelectedMoveIndex != 1 ? 3000 : 1, () -> {
                            blockAction(finalMove.animationDelayMs, () -> {
                                //TODO animation after messages runs here
                                finalMove.moveAction.execute();



                            });
                        });
                    });
                });
            });


        });
    }

private boolean validateMessage(String message) {
    if (message != null && !message.isEmpty()) {
        return true;
    }
    return false;
}

int currentSelectedMoveIndex;

private void logic() {
    selectorFocus.setPosition(firstX ? 0.55f : 5.45f, firstY ? 1.28f : 0.68f);
    currentSelectedMoveIndex = 0;
    currentSelectedMoveIndex += !firstX ? 1 : 0;
    currentSelectedMoveIndex += !firstY ? 2 : 0;
}

String displayString = "";

private void draw() {
    ScreenUtils.clear(Color.BLACK);
    game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
    game.batch.begin();
    bgr.draw(game.batch);

    playerS.draw(game.batch);
//    fight.opponent.draw(game.batch);

    actionSelector.draw(game.batch);
    actionDescriptor.draw(game.batch);

    float hpFontSize = 0.07f;
    float statFontHeight = 1f;
//    Dialog.renderTransparent("HP: " + Math.max(player.hp, 0), 0.5f, 6, hpFontSize, Color.RED, game.batch);
//    Dialog.renderTransparent("ATK: " + Math.max(player.atck, 0), 0.5f, 6 - statFontHeight, hpFontSize, Color.BLUE, game.batch);
//    Dialog.renderTransparent("OP: " + Math.max(player.OP, 0), 0.5f, 6 - 2 * statFontHeight, hpFontSize, Color.YELLOW, game.batch);


//    Dialog.renderTransparent("HP: " + Math.max(opponent.hp, 0), 12.5f, 8, hpFontSize, Color.RED, game.batch);
//    Dialog.renderTransparent("ATK: " + Math.max(opponent.atck, 0), 12.5f, 8 - statFontHeight, hpFontSize, Color.BLUE, game.batch);
//    Dialog.renderTransparent("OP: " + Math.max(opponent.OP, 0), 12.5f, 8 - 2 * statFontHeight, hpFontSize, Color.YELLOW, game.batch);


    if (state == States.ACTION_CHOOSE) {
        selectorFocus.draw(game.batch);
        float selectorFontSize = 0.025f;
        Dialog.renderTransparent(player.moveSet[0].name, 0.7f, 1.75f, selectorFontSize, Color.BLACK, game.batch);
        Dialog.renderTransparent(player.moveSet[1].name, 5.65f, 1.75f, selectorFontSize, Color.BLACK, game.batch);
        Dialog.renderTransparent(player.moveSet[2].name, 0.7f, 1.15f, selectorFontSize, Color.BLACK, game.batch);
        Dialog.renderTransparent(player.moveSet[3].name, 5.65f, 1.15f, selectorFontSize, Color.BLACK, game.batch);
        Dialog.renderTransparent(player.moveSet[currentSelectedMoveIndex].description, 10.75f, 3.75f, selectorFontSize, Color.BLACK, game.batch);
    } else {
        Dialog.renderTransparent(displayString, 0.7f, 1.75f, 0.025f, Color.BLACK, game.batch);
    }

    game.batch.end();
}

@Override
public void resize(int width, int height) {
    if (canResize) {
        game.viewport.update(width, height, true);
    }
}

private void blockAction(long delay) {
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

private void blockAction(long delay, Action action) {
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

boolean canResize = true;

@Override
public void show() {
}
}


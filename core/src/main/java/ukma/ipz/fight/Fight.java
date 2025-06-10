package ukma.ipz.fight;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ukma.ipz.Action;

import java.util.Random;

public class Fight {
    public boolean fightResult = false;
//    public boolean fightOver = false;
    public boolean available=true;
    public Action afterAction;
    public Action winAction;

    Sprite bgr;
    Sprite player = new Sprite(new Texture("isometric\\fights\\mc_batle.png"));
    Sprite opponent;

    Types playerType;
    int playerLvl;

    @Deprecated
    int playerHp;

    Types opponentType;
    int opponentLvl;
    String opponentName;
    MoveChooserAI moveChooser;

    @Deprecated
    int opponentHp;

    public Fight(Texture bgrTexture, Types playerType, int playerLvl, String opponentName, Texture opponentTexture, Types opponentType, int opponentLvl, Action afterAction, Action winAction) {
        this.bgr = new Sprite(bgrTexture);
        this.playerType = playerType;
        this.playerLvl = playerLvl;
        this.opponent = new Sprite(opponentTexture);
        this.opponentType = opponentType;
        this.opponentLvl = opponentLvl;
        this.afterAction = afterAction;
        this.winAction = winAction;
        this.opponentName = opponentName;
        moveChooser = OPAvailable -> {
            if (OPAvailable<3){
                return getDropIndex(50, 0, 0, 50);
            }
            if (OPAvailable<5){
                return getDropIndex(33, 33, 0, 33);
            }
            if (OPAvailable>=5){
                return getDropIndex(20, 0, 80, 0);
            }
            return 0;
        };
        };

    private static int getDropIndex(int percent0, int percent1, int percent2, int percent3) {
        int[] chances = { percent0, percent1, percent2, percent3 };
        int total = percent0 + percent1 + percent2 + percent3;

        if (total <= 0) throw new IllegalArgumentException("Total percentage must be greater than 0.");

        Random random = new Random();
        int rand = random.nextInt(total) + 1; // 1 to total

        int cumulative = 0;
        for (int i = 0; i < chances.length; i++) {
            cumulative += chances[i];
            if (rand <= cumulative) {
                return i;
            }
        }

        // Fallback, though this should never be hit if logic is correct
        return 0;
    }
}

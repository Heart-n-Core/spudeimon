package ukma.ipz.level;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ukma.ipz.Direction;

public class Player {
    Sprite sprite;
    Texture down, downW, side, sideW, up, upW;
    Texture moveDirectionResultTexture;
    boolean directedRight = true;
    Direction direction;

    Sprite lvlBar;
    Sprite n1, n2, n3;
    Texture[] numbers = new Texture[10];

    private final String path = "isometric\\hero\\";

    Player(){
        setup();
        sprite = new Sprite(down);
        direction = Direction.DOWN;
        sprite.setSize(1, 1);
    }

    private void setup(){
        down = new Texture(path+"down.png");
        downW = new Texture(path+"downW.png");
        side = new Texture(path+"side.png");
        sideW = new Texture(path+"sideW.png");
        up = new Texture(path+"up.png");
        upW = new Texture(path+"upW.png");

        lvlBar = new Sprite(new Texture("isometric\\other\\lvlBar.png"));

        for (int i = 0; i < numbers.length; i++){
            numbers[i] = new Texture("isometric\\other\\numbers\\"+i + ".png");
        }
        n1 = new Sprite(numbers[0]);n2 = new Sprite(numbers[0]);n3 = new Sprite(numbers[0]);
        lvlBar.setSize(1, 1f);
        lvlBar.setPosition(0, 0);
    }

    void move(Direction direction){
        this.direction = direction;
        switch (direction){
            case RIGHT:{sprite.setTexture(sideW);moveDirectionResultTexture=side; flipRight();break;}
            case LEFT:{sprite.setTexture(sideW); moveDirectionResultTexture=side; flipLeft();break;}
            case UP:{sprite.setTexture(upW);moveDirectionResultTexture=up; flipRight();break;}
            case DOWN:{sprite.setTexture(downW);moveDirectionResultTexture=down; flipRight();break;}
        }
    }

    private void flipRight(){
        if (!directedRight){
            sprite.flip(true, false);
            directedRight = true;
        }
    }

    private void flipLeft(){
        if (directedRight){
            sprite.flip(true, false);
            directedRight = false;
        }
    }

    void finalizeMove(){
        sprite.setTexture(moveDirectionResultTexture);
    }
}

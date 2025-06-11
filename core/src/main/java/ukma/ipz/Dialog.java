package ukma.ipz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Dialog {

    String currentLine;
    ArrayList<String> lines = new ArrayList<>();
    private Iterator<String> it;
    public Action afterAction;

//    private Texture boxTexture = new Texture("isometric\\dialog_bar.png");
//    private Texture boxTexture;
//    Sprite boxSprite = new Sprite(boxTexture);
static Sprite boxSprite;

//    BitmapFont font = new BitmapFont();
    static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("isometric\\NotoSans.ttf"));
    static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    static BitmapFont font;

    static {
//        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
//            "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
//            "абвгґдеєжзиіїйклмнопрстуфхцчшщъыьэюя";
//        parameter.size = 12;
//        font = generator.generateFont(parameter);
        //        parameter.magFilter = Texture.TextureFilter.Linear;
//        parameter.minFilter = Texture.TextureFilter.Linear;
        System.out.println("Dialog class loaded at: " + System.currentTimeMillis());

        boxSprite = new Sprite(new Texture("isometric\\dialog_bar.png"));
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + //temp solution
            "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "абвгґдеєжзиіїйклмнопрстуфхцчшщъыьэюя";
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();
    }


    public Dialog(String[] lines, Action afterAction) {
        this(lines);
        this.afterAction = afterAction;
    }
    public Dialog(String[] lines) {

        this.lines = new ArrayList<>(Arrays.asList(lines));
        it = this.lines.iterator();
        font.setUseIntegerPositions(false);
        nextLine();
    }
    public void regenerate(String[] lines, Action afterAction) {
        this.lines = new ArrayList<>(Arrays.asList(lines));
        it = this.lines.iterator();
        this.afterAction = afterAction;
        nextLine();
    }

    public boolean hasNext() {
        return it.hasNext();
    }
    public void nextLine() {
        if (!it.hasNext()) {
            generator.dispose();
            return;}
        currentLine = it.next();
    }
    public void render(SpriteBatch batch, int absX, int absY, int relX, int relY, float camX, float camY) {
        boxSprite.setSize(relX, relY);
        boxSprite.setPosition(camX-(float) relX /2, camY-(float) relY /2);
        boxSprite.draw(batch);
        font.getData().setScale(0.015f);
        font.setColor(Color.BLACK);
        font.draw(batch, currentLine, camX- (float) relX /2.45f, camY- (float)relY/3.75f);

    }

    public static void renderTransparent(String content, float relX, float relY, float fontSize, Color color,  SpriteBatch batch) {
        font.setColor(color);
        font.getData().setScale(fontSize);
//        font.setColor(Color.BLACK);
        font.draw(batch, content, relX, relY);
    }
}

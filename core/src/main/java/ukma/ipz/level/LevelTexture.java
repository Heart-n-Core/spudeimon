package ukma.ipz.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelTexture {
    Texture texture;
    int x,y;
    Sprite sprite;
//    public LevelTexture(Texture texture, int x, int y) {
//        this.texture = texture;
//        this.x = x;
//        this.y = y;
//        sprite = new Sprite(texture);
//        sprite.setPosition(x, y);
//    }
public LevelTexture(String textureName, int x, int y) {
    this.texture = new Texture("isometric\\npc\\"+textureName);
    this.x = x;
    this.y = y;
    sprite = new Sprite(texture);
    sprite.setSize(1, 1);
    sprite.setPosition(x, y);
}
public LevelTexture(Texture texture, int x, int y) {
    this.texture = texture;
    this.x = x;
    this.y = y;
    sprite = new Sprite(texture);
    sprite.setSize(1, 1);
    sprite.setPosition(x, y);
}
}

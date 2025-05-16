package ukma.ipz.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Level {
    Texture texture;
    Sprite sprite;
    int sizeX, sizeY;
    int X, Y;
    int scaleX=17; int scaleY=11;
    Tile[][] tiles = new Tile[sizeX][sizeY];

    public Level(Texture texture, int sizeX, int sizeY, int initialX, int initialY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.X = initialX;
        this.Y = initialY;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        sprite.setSize(sizeX, sizeY);
        emptyTiles();
    }

    public Level(Texture texture, int sizeX, int sizeY, int initialX, int initialY, int scaleX, int scaleY) {
        this(texture, sizeX, sizeY, initialX, initialY);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    void emptyTiles() {
        tiles = new Tile[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                tiles[i][j]=new Tile(i, j, false);
            }
        }
    }
}

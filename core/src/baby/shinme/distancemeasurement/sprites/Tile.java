package baby.shinme.distancemeasurement.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import baby.shinme.distancemeasurement.managers.ImageManager;

/**
 * Created by jichoul on 2017-05-06.
 */
public class Tile {
    private TextureRegion tile;
    private float positionX;
    private Random rand;

    public Tile(float x, ImageManager imageManager, int tileNumber, int tileType) {
        this.tile = imageManager.getTile(tileNumber).get(tileType);
        positionX = x;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tile, positionX, 0);
    }

    public float getPositionX() {
        return positionX;
    }

    public int getWidth() {
        return tile.getRegionWidth();
    }

    public int getHeight() {
        return tile.getRegionHeight();
    }
}

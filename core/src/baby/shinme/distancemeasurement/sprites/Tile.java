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
    private TextureRegion point;
    private float positionX;
    private Random rand;
    private boolean isPoint;

    public Tile(float x, ImageManager imageManager, int tileNumber, int tileType, boolean isPoint) {
        this.isPoint = isPoint;
        this.tile = imageManager.getTile(tileNumber).get(tileType);
        if (isPoint) {
            this.point = imageManager.getGroundPoint();
        }
        positionX = x;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(tile, positionX, 0);
        if (isPoint) {
            batch.draw(point, positionX + tile.getRegionWidth() / 2 - point.getRegionWidth() / 2, tile.getRegionHeight() - point.getRegionHeight());
        }
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

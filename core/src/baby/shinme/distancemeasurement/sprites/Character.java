package baby.shinme.distancemeasurement.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import baby.shinme.distancemeasurement.managers.ImageManager;

/**
 * Created by jichoul on 2017-05-05.
 */
public class Character {

    public static final int CHARACTERS_SIZE = 9;

    private static final float STAND_DURATION = 1/8f;
    private static final float RUNNING_DURATION = 1/4f;
    private Vector3 position;
    private TextureRegion defaultCharacter;
    private Animation character;
    private Animation stand;
    private Animation walk;
    private ImageManager imageManager;
    private int heroNumber;

    public Character(int heroNumber, float x, float y, ImageManager imageManager) {
        position = new Vector3(x, y, 0);
        this.imageManager = imageManager;
        this.heroNumber = heroNumber;
        stand = imageManager.getStand("hero" + this.heroNumber, STAND_DURATION);
        walk = imageManager.getWalk("hero" + this.heroNumber, RUNNING_DURATION);
        defaultCharacter = imageManager.getCharacter("hero" + this.heroNumber);
        character = stand;
    }

    public void walk() {
        character = walk;
    }

    public void stand() {
        character = stand;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Animation getCharacter() {
        return character;
    }

    public int getWidth() {
        return defaultCharacter.getRegionWidth();
    }

    public int getHeight() {
        return defaultCharacter.getRegionHeight();
    }

    public boolean isPressed(Vector3 touchPos) {
        float x = touchPos.x;
        float y = touchPos.y;
        if (x > position.x && x < position.x + defaultCharacter.getRegionWidth() && y > position.y && y < position.y + defaultCharacter.getRegionHeight()) {
            return true;
        }
        return false;
    }

}

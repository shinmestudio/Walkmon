package baby.shinme.distancemeasurement.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

import baby.shinme.distancemeasurement.managers.ImageManager;

/**
 * Created by jichoul on 2017-05-05.
 */
public class Bamboo {

    private int oneBambooWidth = 10;
    private int oneBambooHeight = 5;
    private float positionX;
    private float positionY = 48;
    private ImageManager imageManager;
    List<Sprite> bamboos = new ArrayList<Sprite>();

    public Bamboo(float positionX, ImageManager imageManager) {
        this.positionX = positionX;
        this.imageManager = imageManager;
    }

    public void buildingBamboo() {
        Sprite bamboo = new Sprite(imageManager.getBamboo());
        bamboo.setPosition(positionX, positionY + (6 * bamboos.size()));
        bamboo.setOrigin(0, (-6 * bamboos.size()));
        bamboos.add(bamboo);
    }

    public void rotateBamboo() {
        for (Sprite bamboo : bamboos) {
            if (bamboo.getRotation() != -90)
                bamboo.rotate((float) -3);
        }
    }

    public boolean checkRotatedBamboo() {
        boolean isAllRotated = true;
        for (Sprite bamboo : bamboos) {
            if (bamboo.getRotation() != -90) {
                isAllRotated = false;
                break;
            }
        }
        return isAllRotated;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public List<Sprite> getBamboos() {
        return bamboos;
    }

    public void resetBamboos() {
        bamboos.clear();
    }

    public int getBamboosLength() {
        return bamboos.size() * 6;
    }
}

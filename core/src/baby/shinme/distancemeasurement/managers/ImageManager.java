package baby.shinme.distancemeasurement.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by jichoul on 2017-03-05.
 */
public class ImageManager {

    private TextureAtlas atlas;

    public void load() {
        //atlas = Assets.manager.get(Assets.IMAGES, TextureAtlas.class);
        atlas = new TextureAtlas(Gdx.files.internal("images/texture.atlas"));
    }

    public Array<TextureAtlas.AtlasRegion> getBackgrounds() {
        return atlas.findRegions("background");
    }

    public  Array<TextureAtlas.AtlasRegion> getTile(int tile) {
        return atlas.findRegions("tile" + tile);
    }

    public TextureRegion getCharacter(String heroName) {
        return atlas.findRegion(heroName + "-stand", 0);
    }

    public Animation getStand(String heroName, float frameDuration) {
        return new Animation(frameDuration, atlas.findRegions(heroName + "-stand"));
    }

    public Animation getWalk(String heroName, float frameDuration) {
        return new Animation(frameDuration, atlas.findRegions(heroName + "-walk"));
    }

    public TextureRegion getBamboo() {
        return atlas.findRegion("bamboo");
    }

    public TextureRegion getGameOverPanel() {
        return atlas.findRegion("gameover-panel");
    }

    public TextureRegion getGameOverButtonPanel() {
        return atlas.findRegion("gameover-button-panel");
    }

    public TextureRegion getGameoverButtonRestart() {
        return atlas.findRegion("gameover-button-restart");
    }

    public TextureRegion getGameoverButtonQuit() {
        return atlas.findRegion("gameover-button-quit");
    }

    public TextureRegion getGameOverMainMessage() {
        return atlas.findRegion("gameover");
    }

    public TextureRegion getPauseMenuBackground() {
        return atlas.findRegion("pause-menu-background");
    }

    public TextureRegion getButtonSquareBeige() {
        return atlas.findRegion("buttonSquare-beige");
    }

    public TextureRegion getAudioOff() {
        return atlas.findRegion("audioOff");
    }

    public TextureRegion getAudioOn() {
        return atlas.findRegion("audioOn");
    }

    public TextureRegion getForward() {
        return atlas.findRegion("forward");
    }

    public TextureRegion getPower() {
        return atlas.findRegion("power");
    }

    public TextureRegion getMusicOff() {
        return atlas.findRegion("musicOff");
    }

    public TextureRegion getMusicOn() {
        return atlas.findRegion("musicOn");
    }

    public TextureRegion getReturn() {
        return atlas.findRegion("return");
    }

    public TextureRegion getPause() { return atlas.findRegion("pause"); }

    public TextureRegion getTutorial() { return atlas.findRegion("tutorial"); }

    public TextureRegion getTitle() { return atlas.findRegion("title"); }

    public TextureRegion getInformation() { return atlas.findRegion("information"); }

    public TextureRegion getMedal() { return atlas.findRegion("medal"); }

    public TextureRegion getUnlocked() { return atlas.findRegion("unlocked"); }

    public TextureRegion getRightBig() { return atlas.findRegion("rightBig"); }

    public TextureRegion getCross() { return atlas.findRegion("cross"); }

    public TextureRegion getLocked() { return atlas.findRegion("locked"); }

    public TextureRegion getButtonBigYellowDown() { return atlas.findRegion("buttonBig-yellow-down"); }

    public TextureRegion getButtonNormalYellowDown() { return atlas.findRegion("buttonNormal-yellow-down"); }

    public TextureRegion getButtonSmallYellowDown() { return atlas.findRegion("buttonSmall-yellow-down"); }

    public TextureRegion getCustomisePanel() { return atlas.findRegion("customise-panel"); }

    public TextureRegion getCustomiseTitle() { return atlas.findRegion("customise-title"); }
}

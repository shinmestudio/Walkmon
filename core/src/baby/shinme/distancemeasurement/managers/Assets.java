package baby.shinme.distancemeasurement.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by jichoul on 2017-03-20.
 */
public class Assets {

    public AssetManager manager = new AssetManager();

    public static final String IMAGES = "images/texture.atlas";

    public void load() {
        loadImage();
        loadSounds();
    }

    private void loadImage() {
        manager.load(IMAGES, TextureAtlas.class);
    }

    public static final String BAMBOO_SOUND = "sounds/bamboo.wav";
    public static final String GAME_BACKGROUND_MUSIC = "sounds/game-background.mp3";
    public static final String MAIN_BACKGROUND_MUSIC = "sounds/main-background.mp3";
    public static final String GAMEOVER_MUSIC = "sounds/gameover.mp3";
    public static final String CREDIT_MUSIC = "sounds/credit.mp3";

    private void loadSounds() {
        manager.load(BAMBOO_SOUND, Sound.class);
        manager.load(GAME_BACKGROUND_MUSIC, Music.class);
        manager.load(MAIN_BACKGROUND_MUSIC, Music.class);
        manager.load(GAMEOVER_MUSIC, Music.class);
        manager.load(CREDIT_MUSIC, Music.class);
    }

    public void dispose() {
        manager.dispose();
    }
}

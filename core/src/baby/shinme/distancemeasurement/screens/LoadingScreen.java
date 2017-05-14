package baby.shinme.distancemeasurement.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;

import baby.shinme.distancemeasurement.DistanceMeasurement;
import baby.shinme.distancemeasurement.managers.FontProvider;
import baby.shinme.distancemeasurement.managers.ImageManager;
import baby.shinme.distancemeasurement.managers.SoundManager;

/**
 * Created by jichoul on 2017-03-20.
 */
public class LoadingScreen extends ScreenAdapter {

    private DistanceMeasurement game;
    private float deltaTime;
    private Texture logo;
    private boolean firstLoad;

    public LoadingScreen(DistanceMeasurement game) {
        this.game = game;
        game.camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        logo = new Texture(Gdx.files.internal("images/logo.png"));
        firstLoad = true;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(logo, 0, 0);
        game.batch.end();

        deltaTime += delta;

        if (deltaTime > 0.1 && firstLoad) {
            //TODO check what make time lack.
            game.imageManager = new ImageManager();
            game.imageManager.load();
            game.soundManager = new SoundManager();
            game.soundManager.load();
            game.fontProvider = new FontProvider();
        }

        if (deltaTime > 2) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

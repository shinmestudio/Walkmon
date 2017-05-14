package baby.shinme.distancemeasurement;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import baby.shinme.distancemeasurement.managers.FontProvider;
import baby.shinme.distancemeasurement.managers.GameStateManager;
import baby.shinme.distancemeasurement.managers.ImageManager;
import baby.shinme.distancemeasurement.managers.SoundManager;
import baby.shinme.distancemeasurement.screens.LoadingScreen;

public class DistanceMeasurement extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITLE = "The Walker";

	private GameStateManager gsm;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public ImageManager imageManager;
	public SoundManager soundManager;
	public FontProvider fontProvider;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
}

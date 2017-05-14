package baby.shinme.distancemeasurement;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import baby.shinme.distancemeasurement.handler.GPGSHandler;
import baby.shinme.distancemeasurement.managers.FontProvider;
import baby.shinme.distancemeasurement.managers.ImageManager;
import baby.shinme.distancemeasurement.managers.SoundManager;
import baby.shinme.distancemeasurement.screens.LoadingScreen;

public class DistanceMeasurement extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITLE = "The Walker";

	public GPGSHandler handler;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public ImageManager imageManager;
	public SoundManager soundManager;
	public FontProvider fontProvider;

	public DistanceMeasurement(GPGSHandler handler) {
		this.handler = handler;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		setScreen(new LoadingScreen(this));
	}
}

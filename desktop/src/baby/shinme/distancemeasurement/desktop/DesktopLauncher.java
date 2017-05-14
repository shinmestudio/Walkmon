package baby.shinme.distancemeasurement.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import baby.shinme.distancemeasurement.DistanceMeasurement;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DistanceMeasurement.WIDTH;
		config.height = DistanceMeasurement.HEIGHT;
		config.title = DistanceMeasurement.TITLE;
		new LwjglApplication(new DistanceMeasurement(), config);
	}
}

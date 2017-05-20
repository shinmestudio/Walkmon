package baby.shinme.distancemeasurement.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import baby.shinme.distancemeasurement.DistanceMeasurement;
import baby.shinme.distancemeasurement.handler.GPGSHandler;

public class DesktopLauncher implements GPGSHandler {
	public static void main (String[] arg) {
		DesktopLauncher desktopLauncher = new DesktopLauncher();
		desktopLauncher.test();
	}

	private void test() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DistanceMeasurement.WIDTH;
		config.height = DistanceMeasurement.HEIGHT;
		config.title = DistanceMeasurement.TITLE;
		new LwjglApplication(new DistanceMeasurement(this), config);
	}

	@Override
	public void showAds(boolean show) {
	}

	@Override
	public void signIn() {

	}

	@Override
	public void signOut() {

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void unlockAchievement(int achievementCase) throws Exception {

	}

	@Override
	public void submitScore(int highScore) {

	}

	@Override
	public void showAchievement() {

	}

	@Override
	public void showScore() {

	}

	@Override
	public boolean isSignedIn() {
		return false;
	}

	@Override
	public void reconnect() {
	}
}

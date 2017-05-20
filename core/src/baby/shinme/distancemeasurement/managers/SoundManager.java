package baby.shinme.distancemeasurement.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import baby.shinme.distancemeasurement.constants.GameConstant;

public class SoundManager {

	private boolean isSoundOn = true;
	private boolean isMusicOn = true;
	private Sound bambooSound;
	private Music inGameBackgroundMusic;
	private Music mainMenuBackgroundMusic;
	private Music gameoverMusic;
	private Music creditMusic;
	private Preferences prefs;

	public void setSoundOn(boolean isSoundOn) {
		this.isSoundOn = isSoundOn;
		prefs.putBoolean(GameConstant.SOUND_MAP_KEY, isSoundOn);
		prefs.flush();
	}
	
	public boolean getSoundOn() {
		return this.isSoundOn;
	}

	public void setInGameBackgroundVolume(float volume) {
		inGameBackgroundMusic.setVolume(volume);
	}

	public void setMainMenuBackgroundVolume(float volume) {
		mainMenuBackgroundMusic.setVolume(volume);
	}

	public void setMusicOn(boolean isMusicOn) {
		this.isMusicOn = isMusicOn;
		prefs.putBoolean(GameConstant.MUSIC_MAP_KEY, isMusicOn);
		prefs.flush();
	}

	public void stopInGameBackgroundMusic() {
		if (inGameBackgroundMusic.isPlaying()) {
			inGameBackgroundMusic.stop();
		}
	}

	public void stopMainMenuBackgroundMusic() {
		if (mainMenuBackgroundMusic.isPlaying()) {
			mainMenuBackgroundMusic.stop();
		}
	}

	public void stopGameoverMusic() {
		if (gameoverMusic.isPlaying()) {
			gameoverMusic.stop();
		}
	}

	public void stopLoopMusic() {
		stopInGameBackgroundMusic();
		stopMainMenuBackgroundMusic();
		stopGameoverMusic();
	}

	public boolean getMusicOn() {
		return this.isMusicOn;
	}

	public SoundManager() {
	}

	public void load(Assets assets) {
		prefs = Gdx.app.getPreferences(GameConstant.PREFERENCES_KEY_NAME);
		if (prefs.contains(GameConstant.SOUND_MAP_KEY)) {
			setSoundOn(prefs.getBoolean(GameConstant.SOUND_MAP_KEY));
		}
		if (prefs.contains(GameConstant.MUSIC_MAP_KEY)) {
			setMusicOn(prefs.getBoolean(GameConstant.MUSIC_MAP_KEY));
		}

		bambooSound = assets.manager.get(Assets.BAMBOO_SOUND, Sound.class);
		inGameBackgroundMusic = assets.manager.get(Assets.GAME_BACKGROUND_MUSIC, Music.class);
		mainMenuBackgroundMusic = assets.manager.get(Assets.MAIN_BACKGROUND_MUSIC, Music.class);
		gameoverMusic = assets.manager.get(Assets.GAMEOVER_MUSIC, Music.class);
		creditMusic = assets.manager.get(Assets.CREDIT_MUSIC, Music.class);


//		bambooSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bamboo.wav"));
//		inGameBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/game-background.mp3"));
		inGameBackgroundMusic.setLooping(true);
//		mainMenuBackgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/main-background.mp3"));
		mainMenuBackgroundMusic.setLooping(true);
//		gameoverMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameover.mp3"));
		gameoverMusic.setLooping(true);
//		creditMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/credit.mp3"));
		creditMusic.setLooping(true);
	}

	public void playBambooSound() {
		if (isSoundOn) {
			bambooSound.play();
		}
	}

	public void loopInGameBackgroundMusic() {
		if (isMusicOn) {
			inGameBackgroundMusic.setVolume(1f);
		} else {
			inGameBackgroundMusic.setVolume(0f);
		}
		inGameBackgroundMusic.play();
	}

	public void loopMainMenuBackgroundMusic() {
		if (isMusicOn) {
			mainMenuBackgroundMusic.setVolume(1f);
		} else {
			mainMenuBackgroundMusic.setVolume(0f);
		}
		mainMenuBackgroundMusic.play();
	}

	public void loopGameOverMusic() {
		if (isMusicOn) {
			gameoverMusic.setVolume(1f);
		} else {
			gameoverMusic.setVolume(0f);
		}
		gameoverMusic.play();
	}

	public void stopCreditMusic() {
		if (creditMusic.isPlaying()) {
			creditMusic.stop();
		}
	}

	public void loopCreditMusic() {
		if (isMusicOn) {
			creditMusic.setVolume(1f);
		} else {
			creditMusic.setVolume(0f);
		}
		creditMusic.play();
	}

	public void dispose() {
		inGameBackgroundMusic.dispose();
		mainMenuBackgroundMusic.dispose();
		gameoverMusic.dispose();
		creditMusic.dispose();
	}
}

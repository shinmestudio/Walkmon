package baby.shinme.distancemeasurement.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import baby.shinme.distancemeasurement.DistanceMeasurement;
import baby.shinme.distancemeasurement.constants.GameConstant;
import baby.shinme.distancemeasurement.models.Button;
import baby.shinme.distancemeasurement.models.GameMode;
import baby.shinme.distancemeasurement.models.MainMenuState;
import baby.shinme.distancemeasurement.sprites.Character;
import baby.shinme.distancemeasurement.sprites.Tile;

/**
 * Created by jichoul on 2017-05-04.
 */
public class MenuScreen extends ScreenAdapter implements InputProcessor {

    private DistanceMeasurement game;
    private float deltaTime;
    private TextureRegion background1;
    private TextureRegion background2;
    private float background1X;
    private float background2X;
    private Array<Tile> tiles;
    private TextureRegion gameTitle;
    private int backgroundRandomNumber;
    private Random rand;
    private Button playButton;
    private Button unlockedButton;
    private Button informationButton;
    private Button musicButton;
    private Button soundButton;
    private Button leaderBoardButton;
    private Button quitButton;
    private Vector3 beforeTouch;
    private Preferences prefs;
    private Character character;
    private float characterPositionX;
    private MainMenuState state;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private Color pauseShapeColor;
    private BitmapFont creditsTitleBitmapFont;
    private BitmapFont creditsContentsBitmapFont;
    private StringBuilder creditsContents;
    private TextureRegion customiseTitle;
    private TextureRegion customisePanel;
    private Array<Character> customiseCharacters;
    private int selectedCharacterNumber;
    private Character selectedCharacter;
    private Button customiseQuitButton;
    private int nomalBestScore;
    private int timeAttackBestScore;
    private TextureRegion locked;
    private int haveToOverScore;
    private boolean showHaveToOverScore;
    private BitmapFont customiseLockedMessageBitmapFont;
    private int unlockScoreCondition = 400;

    // Game mode
    private TextureRegion gameModePanel;
    private Button gameModeNomalButton;
    private Button gameModeTimeAttackButton;
    private Button gameModeQuitButton;
    private BitmapFont gameModeMessageBitmapFont;

    public MenuScreen(DistanceMeasurement game) {
        this.game = game;
        game.camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        state = MainMenuState.NOMAL;
        prefs = Gdx.app.getPreferences(GameConstant.PREFERENCES_KEY_NAME);

        game.soundManager.stopLoopMusic();
        game.soundManager.loopMainMenuBackgroundMusic();

        rand = new Random();
        backgroundRandomNumber = rand.nextInt(4);
        background1 = game.imageManager.getBackgrounds().get(backgroundRandomNumber);
        background1X = game.camera.position.x - game.camera.viewportWidth / 2;
        background2X = background1X + background1.getRegionWidth() - 1;
        background2 = game.imageManager.getBackgrounds().get(backgroundRandomNumber);

        gameTitle = game.imageManager.getTitle();

        tiles = new Array<Tile>();
        tiles.add(new Tile(game.camera.position.x - game.camera.viewportWidth / 2, game.imageManager, backgroundRandomNumber, 1, false));
        for (int i = 1; i <= 8; i++) {
            Tile beforeTile = tiles.get(i - 1);
            tiles.add(new Tile(beforeTile.getPositionX() + beforeTile.getWidth() - 1, game.imageManager, backgroundRandomNumber, 1, false));
        }

        playButton = new Button(game.imageManager.getButtonBigYellowDown(), game.imageManager.getRightBig());
        leaderBoardButton = new Button(game.imageManager.getButtonSmallYellowDown(), game.imageManager.getLeaderboardsComplex());
        quitButton = new Button(game.imageManager.getButtonSmallYellowDown(), game.imageManager.getPower());
        unlockedButton = new Button(game.imageManager.getButtonNormalYellowDown(), game.imageManager.getUnlocked());
        informationButton = new Button(game.imageManager.getButtonNormalYellowDown(), game.imageManager.getInformation());
        musicButton = new Button(game.imageManager.getButtonSmallYellowDown(),
                prefs.getBoolean(GameConstant.MUSIC_MAP_KEY, true) ? game.imageManager.getMusicOn() : game.imageManager.getMusicOff());
        soundButton = new Button(game.imageManager.getButtonSmallYellowDown(),
                prefs.getBoolean(GameConstant.SOUND_MAP_KEY, true) ? game.imageManager.getAudioOn() : game.imageManager.getAudioOff());

        shapeRenderer = new ShapeRenderer();
        pauseShapeColor = new Color(0, 0, 0, 0.95f);
        layout = new GlyphLayout();
        creditsTitleBitmapFont = game.fontProvider.getCreditsTitleBitmapFont();
        creditsContentsBitmapFont = game.fontProvider.getCreditsContentsBitmapFont();
        creditsContents = new StringBuilder();
        creditsContents.append("<Coding>\n");
        creditsContents.append("J_SHIN\n");
        creditsContents.append("<IMAGE / BGM / SOUND>\n");
        creditsContents.append("http://opengameart.org/\n");
        creditsContents.append("<FONTS>\n");
        creditsContents.append("http://www.1001freefonts.com/\n\n");
        creditsContents.append("for Dang Thi Hoa");

        customiseTitle = game.imageManager.getCustomiseTitle();
        customisePanel = game.imageManager.getCustomisePanel();
        customiseQuitButton = new Button(game.imageManager.getCross());
        locked = game.imageManager.getLocked();
        showHaveToOverScore = false;
        haveToOverScore = 0;
        customiseLockedMessageBitmapFont = game.fontProvider.getCustomiseLockedMessageBitmapFont();

        settingCharacters();
        nomalBestScore = prefs.getInteger(GameMode.NOMAL.name() + GameConstant.HIGH_SCORE_SAVE_MAP_KEY, 0);
        timeAttackBestScore = prefs.getInteger(GameMode.TIME_ATTACK.name() + GameConstant.HIGH_SCORE_SAVE_MAP_KEY, 0);

        gameModePanel = game.imageManager.getModePanel();
        gameModeNomalButton = new Button(game.imageManager.getButtonBigYellowDown(), game.imageManager.getRightBig());
        gameModeTimeAttackButton = new Button(game.imageManager.getButtonBigYellowDown(), game.imageManager.getRingingAlarm());
        gameModeMessageBitmapFont = game.fontProvider.getGameModeMessageBitmapFont();
        gameModeQuitButton = new Button(game.imageManager.getCross());

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        game.handler.showAds(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        deltaTime += delta;

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        drawBackground();

        game.batch.draw(gameTitle, game.camera.position.x - gameTitle.getRegionWidth() / 2, 320);

        playButton.setPos(game.camera.position.x - playButton.getRegionWidth() / 2, 115);
        unlockedButton.setPos(playButton.getPosX() + playButton.getRegionWidth() + 100, 115);
        informationButton.setPos(playButton.getPosX() - 100 - informationButton.getRegionWidth(), 115);
        musicButton.setPos(unlockedButton.getPosX() + unlockedButton.getRegionWidth() / 2 - musicButton.getRegionWidth() - 10, unlockedButton.getPosY() + unlockedButton.getRegionHeight() + 10);
        soundButton.setPos(unlockedButton.getPosX() + unlockedButton.getRegionWidth() / 2 + 10, unlockedButton.getPosY() + unlockedButton.getRegionHeight() + 10);
        quitButton.setPos(informationButton.getPosX() + informationButton.getRegionWidth() / 2 - quitButton.getRegionWidth() - 10, informationButton.getPosY() + informationButton.getRegionHeight() + 10);
        leaderBoardButton.setPos(informationButton.getPosX() + informationButton.getRegionWidth() / 2 + 10, informationButton.getPosY() + informationButton.getRegionHeight() + 10);
        playButton.draw(game.batch);
        unlockedButton.draw(game.batch);
        informationButton.draw(game.batch);
        musicButton.draw(game.batch);
        soundButton.draw(game.batch);
        quitButton.draw(game.batch);
        leaderBoardButton.draw(game.batch);

        for (int i = 0; i < tiles.size; i++) {
            Tile tile = tiles.get(i);
            tile.draw(game.batch);
        }

        characterPositionX += 3;
        game.batch.draw((TextureRegion) character.getCharacter().getKeyFrame(deltaTime, true),
                characterPositionX, character.getPosition().y);
        if (characterPositionX > game.camera.position.x + game.camera.viewportWidth / 2) {
            characterPositionX = game.camera.position.x - game.camera.viewportWidth / 2 - 48;
        }

        game.batch.end();

        if (state == MainMenuState.CREDITS) {
            drawCredits();
        }

        if (state == MainMenuState.CHARACTER_SELECT) {
            drawCharacterSelector();
        }

        if (state == MainMenuState.GAME_MODE_SELECT) {
            drawGameModeSelector();
        }
    }

    private void drawGameModeSelector() {
        drawBlackAlphaScreen();
        game.batch.begin();
        game.batch.draw(gameModePanel, game.camera.position.x - gameModePanel.getRegionWidth() / 2, game.HEIGHT / 2 - gameModePanel.getRegionHeight() / 2);
        gameModeNomalButton.setPos(game.camera.position.x - gameModeNomalButton.getRegionWidth() - 35, game.HEIGHT / 2 - gameModeNomalButton.getRegionHeight() / 2 + 15);
        gameModeNomalButton.draw(game.batch);
        gameModeTimeAttackButton.setPos(game.camera.position.x + 35, game.HEIGHT / 2 - gameModeTimeAttackButton.getRegionHeight() / 2 + 15);
        gameModeTimeAttackButton.draw(game.batch);
        layout.setText(gameModeMessageBitmapFont, "Nomal Play");
        gameModeMessageBitmapFont.draw(game.batch, layout, gameModeNomalButton.getPosX() + gameModeNomalButton.getRegionWidth() / 2 - layout.width / 2, gameModeNomalButton.getPosY() - 10);
        layout.setText(gameModeMessageBitmapFont, "Time Attack");
        gameModeMessageBitmapFont.draw(game.batch, layout, gameModeTimeAttackButton.getPosX() + gameModeNomalButton.getRegionWidth() / 2 - layout.width / 2, gameModeTimeAttackButton.getPosY() - 10);
        gameModeQuitButton.setPos(game.camera.position.x + gameModePanel.getRegionWidth() / 2 - gameModeQuitButton.getRegionWidth() + 10, game.HEIGHT / 2 + gameModePanel.getRegionHeight() / 2 - 5);
        gameModeQuitButton.draw(game.batch);

        game.batch.end();
    }

    private void settingCharacters() {
        selectedCharacterNumber = prefs.getInteger(GameConstant.CHARACTER_NUMBER_KEY, 0);
        characterPositionX = game.camera.position.x - game.camera.viewportWidth / 2;
        character = new Character(selectedCharacterNumber, characterPositionX, tiles.get(0).getHeight(), game.imageManager);
        character.walk();

        selectedCharacter = new Character(selectedCharacterNumber, game.camera.position.x - customisePanel.getRegionWidth() / 2 + 30, 100, game.imageManager);
        selectedCharacter.walk();

        customiseCharacters = new Array<Character>();
        for (int i = 0; i < Character.CHARACTERS_SIZE; i++) {
            customiseCharacters.add(new Character(i,  game.camera.position.x - customisePanel.getRegionWidth() / 2 + 30 + (i * 55), 200, game.imageManager));
        }
    }

    private void drawCharacterSelector() {
        drawBlackAlphaScreen();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(customiseTitle, game.camera.position.x - customiseTitle.getRegionWidth() / 2, 325);
        game.batch.draw(customisePanel, game.camera.position.x - customisePanel.getRegionWidth() / 2, 70);
        for (int i = 0; i < customiseCharacters.size; i++) {
            Sprite sprite = new Sprite((TextureRegion) customiseCharacters.get(i).getCharacter().getKeyFrame(deltaTime, true));
            sprite.setPosition(customiseCharacters.get(i).getPosition().x, customiseCharacters.get(i).getPosition().y);

            if (i * unlockScoreCondition > nomalBestScore || i * unlockScoreCondition > timeAttackBestScore) {
                sprite.setColor(0, 0, 0, 1);
            }

            sprite.draw(game.batch);

            if (i * unlockScoreCondition > nomalBestScore || i * unlockScoreCondition > timeAttackBestScore) {
                game.batch.draw(locked, sprite.getX() + sprite.getWidth() / 2 - locked.getRegionWidth() / 2, sprite.getY());
            }
        }
        game.batch.draw((TextureRegion) selectedCharacter.getCharacter().getKeyFrame(deltaTime, true),
                selectedCharacter.getPosition().x, selectedCharacter.getPosition().y);
        if (selectedCharacter.getPosition().x > game.camera.position.x + customiseTitle.getRegionWidth() / 2 - 30) {
            selectedCharacter.getPosition().x = game.camera.position.x - customisePanel.getRegionWidth() / 2 + 30;
        } else {
            selectedCharacter.getPosition().x += 1;
        }
        customiseQuitButton.setPos(game.camera.position.x + customisePanel.getRegionWidth() / 2 - 40, 265);
        customiseQuitButton.draw(game.batch);
        layout.setText(customiseLockedMessageBitmapFont, "Nomal Score : " + nomalBestScore);
        customiseLockedMessageBitmapFont.draw(game.batch, layout, game.camera.position.x - layout.width / 2, 325);
        layout.setText(customiseLockedMessageBitmapFont, "Time Attack Score : " + timeAttackBestScore);
        customiseLockedMessageBitmapFont.draw(game.batch, layout, game.camera.position.x - layout.width / 2, 300);

        if (showHaveToOverScore) {
            layout.setText(customiseLockedMessageBitmapFont, haveToOverScore + " score to unlock");
            customiseLockedMessageBitmapFont.draw(game.batch, layout, game.camera.position.x - layout.width / 2, 185);
        }


        game.batch.end();
    }

    private void drawCredits() {
        drawBlackAlphaScreen();
        game.batch.begin();
        //game.batch.draw(gameTitle, game.camera.position.x - gameTitle.getRegionWidth() / 2, 320);
        layout.setText(creditsTitleBitmapFont, "CREDITS");
        creditsTitleBitmapFont.draw(game.batch, layout, game.camera.position.x - layout.width / 2, 410);
        layout.setText(creditsContentsBitmapFont, creditsContents.toString(),
				Color.WHITE, game.WIDTH, Align.center, true);
        creditsContentsBitmapFont.draw(game.batch, layout, 0, 320);

        game.batch.end();
    }

    private void drawBlackAlphaScreen() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(pauseShapeColor);
        shapeRenderer.rect(game.camera.position.x - game.camera.viewportWidth / 2, 0, game.WIDTH, game.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawBackground() {
        background1X -= 1;
        background2X -= 1;
        if (background1X + background1.getRegionWidth() < game.camera.position.x - game.camera.viewportWidth / 2) {
            background1X = background2X + background2.getRegionWidth() - 1;
        } else if (background2X + background2.getRegionWidth() < game.camera.position.x - game.camera.viewportWidth / 2) {
            background2X = background1X + background1.getRegionWidth() - 1;
        }
        game.batch.draw(background1, background1X, 0);
        game.batch.draw(background2, background2X, 0);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) {
            Gdx.app.exit();
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        beforeTouch = new Vector3(screenX, screenY, 0);
        game.camera.unproject(beforeTouch);
        showHaveToOverScore = false;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (beforeTouch == null) {
            return true;
        }
        Vector3 touch = new Vector3(screenX, screenY, 0);
        game.camera.unproject(touch);
        if (state == MainMenuState.NOMAL) {
            if (playButton.isPressed(beforeTouch) && playButton.isPressed(touch)) {
                //game.setScreen(new GameScreen(game));
                state = MainMenuState.GAME_MODE_SELECT;
            } else if (informationButton.isPressed(beforeTouch) && informationButton.isPressed(touch)) {
                state = MainMenuState.CREDITS;
            } else if (unlockedButton.isPressed(beforeTouch) && unlockedButton.isPressed(touch)) {
                state = MainMenuState.CHARACTER_SELECT;
            } else if (musicButton.isPressed(beforeTouch) && musicButton.isPressed(touch)) {
                if (prefs.getBoolean(GameConstant.MUSIC_MAP_KEY, true)) {
                    prefs.putBoolean(GameConstant.MUSIC_MAP_KEY, false);
                    musicButton.setText(game.imageManager.getMusicOff());
                    game.soundManager.setMusicOn(false);
                    game.soundManager.setMainMenuBackgroundVolume(0f);
                } else {
                    prefs.putBoolean(GameConstant.MUSIC_MAP_KEY, true);
                    musicButton.setText(game.imageManager.getMusicOn());
                    game.soundManager.setMusicOn(true);
                    game.soundManager.setMainMenuBackgroundVolume(1f);
                }
                prefs.flush();
            } else if (soundButton.isPressed(beforeTouch) && soundButton.isPressed(touch)) {
                if (prefs.getBoolean(GameConstant.SOUND_MAP_KEY, true)) {
                    prefs.putBoolean(GameConstant.SOUND_MAP_KEY, false);
                    soundButton.setText(game.imageManager.getAudioOff());
                    game.soundManager.setSoundOn(false);
                } else {
                    prefs.putBoolean(GameConstant.SOUND_MAP_KEY, true);
                    soundButton.setText(game.imageManager.getAudioOn());
                    game.soundManager.setSoundOn(true);
                }
                prefs.flush();
            } else if (quitButton.isPressed(beforeTouch) && quitButton.isPressed(touch)) {
                Gdx.app.exit();
            } else if (leaderBoardButton.isPressed(beforeTouch) && leaderBoardButton.isPressed(touch)) {
                game.handler.showScore();
            }
        } else if (state == MainMenuState.CREDITS) {
            state = MainMenuState.NOMAL;
        } else if (state == MainMenuState.CHARACTER_SELECT) {
            if (customiseQuitButton.isPressed(beforeTouch) && customiseQuitButton.isPressed(touch)) {
                state = MainMenuState.NOMAL;
                character = new Character(selectedCharacterNumber, characterPositionX, tiles.get(0).getHeight(), game.imageManager);
                character.walk();
            } else {
                for (int i = 0; i < customiseCharacters.size; i++) {
                    if (customiseCharacters.get(i).isPressed(beforeTouch) && customiseCharacters.get(i).isPressed(touch)) {
                        if (i * unlockScoreCondition > nomalBestScore || i * unlockScoreCondition > timeAttackBestScore) {
                            haveToOverScore = i * unlockScoreCondition;
                            showHaveToOverScore = true;
                        } else {
                            selectedCharacterNumber = i;
                            selectedCharacter = new Character(selectedCharacterNumber, game.camera.position.x - customisePanel.getRegionWidth() / 2 + 30, 100, game.imageManager);
                            selectedCharacter.walk();
                            prefs.putInteger(GameConstant.CHARACTER_NUMBER_KEY, selectedCharacterNumber);
                            prefs.flush();
                        }
                    }
                }
            }
        } else if (state == MainMenuState.GAME_MODE_SELECT) {
            if (gameModeQuitButton.isPressed(beforeTouch) && gameModeQuitButton.isPressed(touch)) {
                state = MainMenuState.NOMAL;
            } else if (gameModeNomalButton.isPressed(beforeTouch) && gameModeNomalButton.isPressed(touch)) {
                game.setScreen(new GameScreen(game, GameMode.NOMAL));
            } else if (gameModeTimeAttackButton.isPressed(beforeTouch) && gameModeTimeAttackButton.isPressed(touch)) {
                game.setScreen(new GameScreen(game, GameMode.TIME_ATTACK));
            }
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

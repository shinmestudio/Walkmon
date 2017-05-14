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
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import baby.shinme.distancemeasurement.DistanceMeasurement;
import baby.shinme.distancemeasurement.constants.GameConstant;
import baby.shinme.distancemeasurement.models.Button;
import baby.shinme.distancemeasurement.sprites.Bamboo;
import baby.shinme.distancemeasurement.sprites.Character;
import baby.shinme.distancemeasurement.sprites.Tile;
import baby.shinme.distancemeasurement.models.GameState;

/**
 * Created by jichoul on 2017-05-05.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {

    private static final int TILE_MIN_SPACING = 15;
    private static final int TILE_MAX_SPACING = 71;
    private static final int TILE_COUNT = 7;
    private static final int TILE_HEIGHT = 48;
    private static final int HERO_WIDTH = 48;

    private DistanceMeasurement game;
    private float deltaTime;
    private float bambooDeltaTime;
    private float stopWatchDeltaTime;

    private TextureRegion background1;
    private TextureRegion background2;
    private float background1X;
    private float background2X;

    private Random rand;
    private Bamboo bamboo;
    private Character hero;
    private TextureRegion gameOverPanel;
    private Button gameOverRestartButton;
    private Button gameOverQuitButton;
    private TextureRegion gameOverMessage;
    private TextureRegion tutorialImage;

    private int backgroundRandomNumber;
    private Array<Tile> tiles;
    private GameState gameState;
    private BitmapFont gameMainScoreBitmapFont;
    private BitmapFont gameMenuTitleBitmapFont;
    private BitmapFont gameMenuScoreBitmapFont;
    private BitmapFont gameChainBitmapFont;
    private GlyphLayout layout;
    private float score;
    private int heroStartPositionX;
    private ShapeRenderer shapeRenderer;
    private Preferences prefs;
    private int clickCount;
    private Color pauseShapeColor;
    private Button pauseResumeButton;
    private Button pauseRestartButton;
    private Button pauseMusicButton;
    private Button pauseSoundButton;
    private Button pauseQuitButton;
    private Button gamePauseButton;
    private Color blackColor;

    private boolean isClicking = false;
    private boolean isFinishedClicking = false;

    public GameScreen(DistanceMeasurement game) {
        this.game = game;
        game.camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
        prefs = Gdx.app.getPreferences(GameConstant.PREFERENCES_KEY_NAME);

        game.soundManager.stopLoopMusic();
        game.soundManager.loopInGameBackgroundMusic();
        gameMainScoreBitmapFont = game.fontProvider.getGameMainScoreBitmapFont();
        gameMenuTitleBitmapFont = game.fontProvider.getGameMenuTitleBitmapFont();
        gameMenuScoreBitmapFont = game.fontProvider.getGameMenuScoreBitmapFont();
        gameChainBitmapFont = game.fontProvider.getGameChainBitmapFont();
        gameOverRestartButton = new Button(game.imageManager.getGameOverButtonPanel(), game.imageManager.getGameoverButtonRestart());
        gameOverQuitButton = new Button(game.imageManager.getGameOverButtonPanel(), game.imageManager.getGameoverButtonQuit());
        gameOverMessage = game.imageManager.getGameOverMainMessage();
        pauseResumeButton = new Button(game.imageManager.getButtonSquareBeige(), game.imageManager.getForward());
        pauseRestartButton = new Button(game.imageManager.getButtonSquareBeige(), game.imageManager.getReturn());
        pauseQuitButton = new Button(game.imageManager.getButtonSquareBeige(), game.imageManager.getPower());
        pauseMusicButton = new Button(game.imageManager.getButtonSquareBeige(),
                prefs.getBoolean(GameConstant.MUSIC_MAP_KEY, true) ? game.imageManager.getMusicOn() : game.imageManager.getMusicOff());
        pauseSoundButton = new Button(game.imageManager.getButtonSquareBeige(),
                prefs.getBoolean(GameConstant.SOUND_MAP_KEY, true) ? game.imageManager.getAudioOn() : game.imageManager.getAudioOff());
        gamePauseButton = new Button(game.imageManager.getButtonSquareBeige(), game.imageManager.getPause());
        tutorialImage = game.imageManager.getTutorial();

        rand = new Random();
        backgroundRandomNumber = rand.nextInt(4);
        gameOverPanel = game.imageManager.getGameOverPanel();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        pauseShapeColor = new Color(0, 0, 0, 0.9f);
        blackColor = new Color(0, 0, 0, 1);
        settingBackground();
        settingTile();

        gameState = prefs.getBoolean(GameConstant.FINISHED_TUTORIAL_KEY, false) ? GameState.PLAY : GameState.TUTORIAL;

        hero = new Character(prefs.getInteger(GameConstant.CHARACTER_NUMBER_KEY, 0), tiles.get(0).getPositionX() + tiles.get(0).getWidth() / 2 - HERO_WIDTH / 2, TILE_HEIGHT, game.imageManager);
        hero.stand();
        heroStartPositionX = (int) hero.getPosition().x;
        bamboo = new Bamboo(tiles.get(0).getPositionX() + tiles.get(0).getWidth(), game.imageManager);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.gl.glClearColor(1, 0, 0, 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        game.batch.setProjectionMatrix(game.camera.combined);
        deltaTime += delta;
        bambooDeltaTime += delta;

        game.camera.position.x = -100 + hero.getPosition().x + game.camera.viewportWidth / 2;
        game.camera.update();
        game.batch.begin();

        drawBackground();
        drawBamboo();

        layout.setText(gameMainScoreBitmapFont, "SCORE");
        gameMainScoreBitmapFont.draw(game.batch, "SCORE", game.camera.position.x + game.camera.viewportWidth / 2 - layout.width - 10, 480);
        layout.setText(gameMainScoreBitmapFont, String.valueOf((int) score));
        gameMainScoreBitmapFont.draw(game.batch, layout, game.camera.position.x + game.camera.viewportWidth / 2 - layout.width - 10, 440);

        game.batch.draw((TextureRegion) hero.getCharacter().getKeyFrame(deltaTime, true),
                hero.getPosition().x, hero.getPosition().y);

        for (int i = 0; i < tiles.size; i++) {
            Tile tile = tiles.get(i);
            tile.draw(game.batch);
        }

        if (isClicking && bambooDeltaTime >= 0.04 && !isFinishedClicking) {
            bambooDeltaTime = 0;
            game.soundManager.playBambooSound();
            bamboo.buildingBamboo();
            if (bamboo.getBamboosLength() + TILE_HEIGHT == game.HEIGHT) {
                isClicking = false;
                isFinishedClicking = true;
            }
        }

        if (isFinishedClicking && bamboo.getBamboos().size() > 0 && gameState == GameState.PLAY) {
            bamboo.rotateBamboo();
            if (bamboo.checkRotatedBamboo()) {
                int nextTileNumber = checkNextTileWithBambooLength();
                if (nextTileNumber > 0) {
                    moveHero(nextTileNumber);
                } else {
                    gameOverCheck();
                }
            }
        }

        float positionXZero = game.camera.position.x - game.camera.viewportWidth / 2;
        gamePauseButton.setPos(positionXZero + 20, game.HEIGHT - 60);
        gamePauseButton.draw(game.batch);

        game.batch.end();

        if (gameState == GameState.GAMEOVER) {
            drawGameOver();
            game.handler.showAds(true);
        }

        if (gameState == GameState.TUTORIAL) {
            game.handler.showAds(true);
            drawBlackAlphaScreen();
            game.batch.begin();
            game.batch.draw(tutorialImage, game.camera.position.x - tutorialImage.getRegionWidth() / 2, game.HEIGHT / 2 - tutorialImage.getRegionHeight() / 2);
            game.batch.end();
        }

        if (gameState == GameState.PLAY) {
            game.handler.showAds(false);
        }

        if (gameState == GameState.PAUSE) {
            pauseMenu();
            game.handler.showAds(false);
        }
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

    private void pauseMenu() {
        drawBlackAlphaScreen();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(game.camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(blackColor);
        shapeRenderer.rect(game.camera.position.x - game.camera.viewportWidth / 2, 185, game.WIDTH, 110);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        float positionXZero = game.camera.position.x - game.camera.viewportWidth / 2;
        game.batch.begin();
//        game.batch.draw(pauseMenuBackground, positionXZero, 0);
        pauseResumeButton.setPos(positionXZero + 133 - pauseResumeButton.getRegionWidth() / 2, 212.5f);
        pauseResumeButton.draw(game.batch);
        pauseRestartButton.setPos(positionXZero + 266 - pauseRestartButton.getRegionWidth() / 2, 212.5f);
        pauseRestartButton.draw(game.batch);
        pauseMusicButton.setPos(positionXZero + 400 - pauseMusicButton.getRegionWidth() / 2, 212.5f);
        pauseMusicButton.draw(game.batch);
        pauseSoundButton.setPos(positionXZero + 534 - pauseSoundButton.getRegionWidth() / 2, 212.5f);
        pauseSoundButton.draw(game.batch);
        pauseQuitButton.setPos(positionXZero + 667 - pauseQuitButton.getRegionWidth() / 2, 212.5f);
        pauseQuitButton.draw(game.batch);
        game.batch.end();
    }

    private void settingBackground() {
        background1 = game.imageManager.getBackgrounds().get(backgroundRandomNumber);
        background2 = game.imageManager.getBackgrounds().get(backgroundRandomNumber);
        background1X = -100;
        background2X = background1X + background1.getRegionWidth() - 1;
    }

    private void drawBackground() {
        if (game.camera.position.x - game.camera.viewportWidth / 2 > background1X + background1.getRegionWidth()) {
            background1X = background2X + background2.getRegionWidth() - 1;
        } else if (game.camera.position.x - game.camera.viewportWidth / 2 > background2X + background2.getRegionWidth()) {
            background2X = background1X + background1.getRegionWidth() - 1;
        }

        game.batch.draw(background1, background1X, 0);
        game.batch.draw(background2, background2X, 0);
    }

    private void settingTile() {
        tiles = new Array<Tile>();
        tiles.add(new Tile(20, game.imageManager, backgroundRandomNumber, 1));
        for (int i = 1; i < TILE_COUNT; i++) {
            int tileRandomGap = (rand.nextInt(TILE_MAX_SPACING - TILE_MIN_SPACING) + TILE_MIN_SPACING) * 6;
            Tile beforeTile = tiles.get(i - 1);
            tiles.add(new Tile(beforeTile.getPositionX() + beforeTile.getWidth() + tileRandomGap, game.imageManager, backgroundRandomNumber, 1));
        }
    }

    private void drawBamboo() {
        for (int i = 0; i < bamboo.getBamboos().size(); i++) {
            Sprite bamboo = this.bamboo.getBamboos().get(i);
            bamboo.draw(game.batch);
        }
    }

    private void gameOverCheck() {
        if (gameState != GameState.GAMEOVER) {
            for (int i = 0; i < bamboo.getBamboos().size(); i++) {
                Sprite bamboo = this.bamboo.getBamboos().get(i);
                bamboo.setPosition(bamboo.getX(), bamboo.getY() - 2);
            }
            if (this.bamboo.getBamboos().get(0).getY() <= -60) {
                gameState = GameState.GAMEOVER;
            }
        }
    }

    private void drawGameOver() {
        drawBlackAlphaScreen();
        game.soundManager.stopInGameBackgroundMusic();
        game.soundManager.loopGameOverMusic();
        game.batch.begin();
        game.batch.draw(gameOverMessage, game.camera.position.x - gameOverMessage.getRegionWidth() / 2, 320);
        game.batch.draw(gameOverPanel, game.camera.position.x - gameOverPanel.getRegionWidth() / 2, 80);
        gameMenuTitleBitmapFont.draw(game.batch, "SCORE", game.camera.position.x + 125, 250);
        layout.setText(gameMenuScoreBitmapFont, String.valueOf((int) score));
        gameMenuScoreBitmapFont.draw(game.batch, layout, game.camera.position.x + 210 - layout.width, 230);
        gameMenuTitleBitmapFont.draw(game.batch, "BEST", game.camera.position.x + 137, 170);
        int best = prefs.getInteger(GameConstant.HIGH_SCORE_SAVE_MAP_KEY);
        if (score > best) {
            prefs.putInteger(GameConstant.HIGH_SCORE_SAVE_MAP_KEY, (int) score);
            prefs.flush();
            best = (int) score;
        }
        layout.setText(gameMenuScoreBitmapFont, String.valueOf(best));
        gameMenuScoreBitmapFont.draw(game.batch, String.valueOf(best), game.camera.position.x + 210 - layout.width, 150);

        gameOverRestartButton.setPos(game.camera.position.x - 210, 195);
        gameOverRestartButton.draw(game.batch);
        gameOverQuitButton.setPos(game.camera.position.x - 210, 115);
        gameOverQuitButton.draw(game.batch);
        game.batch.end();
    }

    private void moveHero(int nextTileNumber) {
        Tile nextTile = tiles.get(nextTileNumber);
        if (hero.getPosition().x >= nextTile.getPositionX() + nextTile.getWidth() / 2 - HERO_WIDTH / 2) {
            for (int i = 0; i < nextTileNumber; i++) {
                int tileRandomGap = (rand.nextInt(TILE_MAX_SPACING - TILE_MIN_SPACING) + TILE_MIN_SPACING) * 6;
                tiles.removeIndex(0);
                Tile lastTile = tiles.get(tiles.size - 1);
                tiles.add(new Tile(lastTile.getPositionX() + lastTile.getWidth() + tileRandomGap, game.imageManager, backgroundRandomNumber, 0));
            }

            hero.stand();
            hero.getPosition().x = nextTile.getPositionX() + nextTile.getWidth() / 2 - HERO_WIDTH / 2;
            reset();
        } else {
            hero.walk();
            moveToNextTile(nextTileNumber);
        }

        if (nextTileNumber > 1) {
            layout.setText(gameChainBitmapFont, nextTileNumber + " CHAIN");
            gameChainBitmapFont.draw(game.batch, layout, game.camera.position.x - layout.width / 2, 450);
        }
    }

    private void reset() {
        isFinishedClicking = false;
        isClicking = false;
        bamboo.resetBamboos();
        bamboo.setPositionX(tiles.get(0).getPositionX() + tiles.get(0).getWidth());
    }

    private void moveToNextTile(int nextTileNumber) {
        hero.getPosition().x += 3;
        score += (0.5 * nextTileNumber);
        //TODO chain 2!!
    }

    private int checkNextTileWithBambooLength() {
        int bambooLength = bamboo.getBamboosLength();
        Tile nowTile = tiles.get(0);
        for (int i = 1; i < tiles.size; i++) {
            Tile nextTile = tiles.get(i);

            if (nextTile.getPositionX() - (nowTile.getPositionX() + nowTile.getWidth()) <= bambooLength
                && (nextTile.getPositionX() + nextTile.getWidth()) - (nowTile.getPositionX() + nowTile.getWidth()) >= bambooLength - 1) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            if (gameState == GameState.PAUSE) {
                gameState = GameState.PLAY;
            } else if (gameState == GameState.PLAY) {
                gameState = GameState.PAUSE;
            } else if (gameState == GameState.GAMEOVER) {
                game.setScreen(new MenuScreen(game));
            }
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
        if (clickCount > 0) {
            return true;
        }

        clickCount++;

        Vector3 clickPoint = new Vector3(screenX, screenY, 0);
        game.camera.unproject(clickPoint);

        if (gameState == GameState.GAMEOVER) {
            if (gameOverRestartButton.isPressed(clickPoint)) {
                game.setScreen(new GameScreen(game));
            } else if (gameOverQuitButton.isPressed(clickPoint)) {
                game.setScreen(new MenuScreen(game));
            }
        } else if (gameState == GameState.PAUSE) {
            if (pauseResumeButton.isPressed(clickPoint)) {
                gameState = GameState.PLAY;
            } else if (pauseRestartButton.isPressed(clickPoint)) {
                game.setScreen(new GameScreen(game));
            } else if (pauseQuitButton.isPressed(clickPoint)) {
                game.setScreen(new MenuScreen(game));
            } else if (pauseMusicButton.isPressed(clickPoint)) {
                if (prefs.getBoolean(GameConstant.MUSIC_MAP_KEY, true)) {
                    prefs.putBoolean(GameConstant.MUSIC_MAP_KEY, false);
                    pauseMusicButton.setText(game.imageManager.getMusicOff());
                    game.soundManager.setMusicOn(false);
                    game.soundManager.setInGameBackgroundVolume(0f);
                } else {
                    prefs.putBoolean(GameConstant.MUSIC_MAP_KEY, true);
                    pauseMusicButton.setText(game.imageManager.getMusicOn());
                    game.soundManager.setMusicOn(true);
                    game.soundManager.setInGameBackgroundVolume(1f);
                }
                prefs.flush();
            } else if (pauseSoundButton.isPressed(clickPoint)) {
                if (prefs.getBoolean(GameConstant.SOUND_MAP_KEY, true)) {
                    prefs.putBoolean(GameConstant.SOUND_MAP_KEY, false);
                    pauseSoundButton.setText(game.imageManager.getAudioOff());
                    game.soundManager.setSoundOn(false);
                } else {
                    prefs.putBoolean(GameConstant.SOUND_MAP_KEY, true);
                    pauseSoundButton.setText(game.imageManager.getAudioOn());
                    game.soundManager.setSoundOn(true);
                }
                prefs.flush();
            }
        } else if (gameState == GameState.TUTORIAL) {
            prefs.putBoolean(GameConstant.FINISHED_TUTORIAL_KEY, true);
            prefs.flush();
            gameState = GameState.PLAY;
        } else {
            if (gamePauseButton.isPressed(clickPoint)) {
                gameState = GameState.PAUSE;
            } else {
                isClicking = true;
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (clickCount != 1) {
            return true;
        }

        clickCount = 0;

        if (isClicking) {
            isFinishedClicking = true;
        }
        isClicking = false;
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

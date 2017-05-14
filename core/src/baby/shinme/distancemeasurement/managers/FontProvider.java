package baby.shinme.distancemeasurement.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

public class FontProvider {
	
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter fontParameter;
	private BitmapFont gameMainScoreBitmapFont;
	private BitmapFont gameMenuTitleBitmapFont;
	private BitmapFont gameMenuScoreBitmapFont;
	private BitmapFont creditsTitleBitmapFont;
	private BitmapFont creditsContentsBitmapFont;
	private BitmapFont customiseLockedMessageBitmapFont;

	
	public FontProvider() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/planetbe.ttf"));

		fontParameter = new FreeTypeFontParameter();
		fontParameter.color = Color.BLACK;
		fontParameter.size = 35;
		gameMainScoreBitmapFont = generator.generateFont(fontParameter);

		fontParameter = new FreeTypeFontParameter();
		fontParameter.color = Color.BLACK;
		fontParameter.size = 30;
		gameMenuScoreBitmapFont = generator.generateFont(fontParameter);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/komikax.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.color = Color.CORAL;
		fontParameter.size = 24;
		fontParameter.borderColor = Color.BLACK;
		fontParameter.borderWidth = 2;
		gameMenuTitleBitmapFont = generator.generateFont(fontParameter);

		fontParameter.color = Color.GREEN;
		fontParameter.size = 45;
		creditsTitleBitmapFont = generator.generateFont(fontParameter);

		fontParameter.color = Color.SKY;
		fontParameter.size = 20;
		creditsContentsBitmapFont = generator.generateFont(fontParameter);

		fontParameter.color = Color.WHITE;
		fontParameter.size = 20;
		customiseLockedMessageBitmapFont = generator.generateFont(fontParameter);

	}

	public BitmapFont getGameMainScoreBitmapFont() {
		return gameMainScoreBitmapFont;
	}

	public BitmapFont getGameMenuTitleBitmapFont() {
		return gameMenuTitleBitmapFont;
	}

	public BitmapFont getGameMenuScoreBitmapFont() {
		return gameMenuScoreBitmapFont;
	}

	public BitmapFont getCreditsTitleBitmapFont() { return creditsTitleBitmapFont; }

	public BitmapFont getCreditsContentsBitmapFont() { return creditsContentsBitmapFont; }

	public BitmapFont getCustomiseLockedMessageBitmapFont() { return customiseLockedMessageBitmapFont; }

	public void dispose() {
		if (generator != null) {
			generator.dispose();
		}
	}
}

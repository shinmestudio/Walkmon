package baby.shinme.distancemeasurement.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Button {

	private TextureRegion background = null;
	
	private TextureRegion text;

	private float startX;
	
	private float endX;
	
	private float startY;
	
	private float endY;

	private float textX;
	
	private float textY;

	private static long timePressed = 0;
	
	public Button(TextureRegion text) {
		this.text = text;
	}

	public Button(TextureRegion background, TextureRegion text) {
		this.background = background;
		this.text = text;
	}

	public void setPos(float x, float y) {
		startX = x;
		startY = y;
		
		if (background != null) {
			endX = x + background.getRegionWidth();
			endY = y + background.getRegionHeight();	
			
			int textWidth = text.getRegionWidth();
			textX = startX + (background.getRegionWidth()-textWidth)/2;
			textY = startY + (background.getRegionHeight()-text.getRegionHeight())/2;			
		}
		else {
			endX = x + text.getRegionWidth();
			endY = y + text.getRegionHeight();
			textX = startX;
			textY = startY;
		}
	}

	public void setBackground(TextureRegion textureRegion) {
		background = textureRegion;
	}
	
	public void setText(TextureRegion textureRegion) {
		text = textureRegion;
	}
	
	public void draw(SpriteBatch batch) {
		if (background != null) {
			batch.draw(background, startX, startY);
			batch.draw(text, textX, textY);
		}
		else {
			batch.draw(text, startX, startY);
		}
	}

	public boolean isPressed(Vector3 touchPos) {
        float x = touchPos.x;
        float y = touchPos.y;		
		if (x > startX && x < endX && y > startY && y < endY) {
			return true;
		}
		return false;
	}

	public int getRegionHeight() {
		if (background != null) {
			return background.getRegionHeight();
		}
		return text.getRegionHeight();
	}

	public int getRegionWidth() {
		if (background != null) {
			return background.getRegionWidth();
		}		
		return text.getRegionWidth();
	}

	public float getPosY() {
		return startY;
	}

	public float getPosX() {
		return startX;
	}	
}

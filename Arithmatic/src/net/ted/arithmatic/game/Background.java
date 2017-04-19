package net.ted.arithmatic.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

	// Instance and Field Variables --------------------------------------------

	private float scale;
	private Sprite backgroundSprite;

	// Methods -----------------------------------------------------------------

	public Background(Texture texture, float scale) {
		this.scale = scale;
		backgroundSprite = new Sprite(texture);
		backgroundSprite.setOrigin(0, 0);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.setScale(this.scale, this.scale);
	}

	public void draw(SpriteBatch batch) {
		backgroundSprite.draw(batch);
	}

	public void dispose() {
		backgroundSprite.getTexture().dispose();
	}
}

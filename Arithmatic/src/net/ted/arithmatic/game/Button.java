package net.ted.arithmatic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Button {

	// Instance and Field Variables --------------------------------------------
	private String value;
	private float locX, locY, scale, fontScale;
	private Texture sprite;

	// Methods -----------------------------------------------------------------

	public Button(String value, Texture texture, float locX, float locY,
			float scale) {

		this.value = value;
		this.locX = locX;
		this.locY = locY;
		this.scale = scale;
		sprite = texture; // sets texture to sprite
		fontScale = 0.4f * (float)Gdx.graphics.getWidth() / 480f;
		Assets.font.setScale(fontScale);
	}

	public boolean isClicked(Vector3 touchPos) {
		return (Gdx.input.isTouched() && touchPos.x >= locX
				&& touchPos.x <= locX + (sprite.getWidth() * scale)
				&& touchPos.y >= locY && touchPos.y <= locY
				+ (sprite.getHeight() * scale));
	}

	public void draw(SpriteBatch batch) {
		batch.draw(sprite, locX, locY, sprite.getWidth() * scale, sprite.getHeight() * scale);

		while (Assets.font.getBounds(value).width > sprite.getWidth() * scale) {
			// System.out.println("Infinite loop!");
			fontScale -= 0.05f;
			Assets.font.setScale(fontScale);
		}

		Assets.font.draw(batch, value,
				locX + (sprite.getWidth() * scale / 2) - (Assets.font.getBounds(value).width)
						/ 2,
				locY + (sprite.getHeight() * scale / 2) + (Assets.font.getCapHeight() / 2));
	}

	public void disposeTexture() {
		Assets.font.dispose();
		sprite.dispose();
	}
}

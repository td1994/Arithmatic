package net.ted.arithmatic.screens;

import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;
import net.ted.arithmatic.game.Background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Title implements Screen {

	// Instance and Field Variables --------------------------------------------

	private Arithmatic game;
	private ScreenResolution resolution;
	private boolean pressed;
	private Background background;
	private Background lighting;

	// Methods -----------------------------------------------------------------

	public Title(Arithmatic game, ScreenResolution resolution) {
		this.game = game;
		this.resolution = resolution;
		pressed = false;
		
		background = new Background(new Texture(Gdx.files.internal("data/" + resolution.imagesToUse()
				+ "/Prison/PrisonBG.png")), resolution.getScale());
		lighting = new Background(new Texture(Gdx.files.internal("data/" + resolution.imagesToUse()
				+ "/Prison/PrisonLighting.png")), resolution.getScale());
	}

	public boolean isPressed() {
		boolean verdict = false;
		if (Gdx.input.isTouched()) {
			pressed = true;
		} else if (pressed) {
			verdict = true;
			pressed = false;
		}
		return verdict;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Assets.batch.begin();
		Assets.font.setScale(0.75f * (float)Gdx.graphics.getWidth() / 480f);
		background.draw(Assets.batch);
		lighting.draw(Assets.batch);
		Assets.font.draw(
				Assets.batch,
				"Arithmatic",
				(Gdx.graphics.getWidth() - Assets.font.getBounds("Arithmatic").width) / 2,
				resolution.getPositionY(800));
		Assets.font.setScale(0.25f * (float)Gdx.graphics.getWidth() / 480f);
		Assets.font.draw(Assets.batch, "Touch screen to begin",
				(Gdx.graphics.getWidth() - Assets.font
						.getBounds("Touch screen to begin").width) / 2,
				resolution.getPositionY(300));
		Assets.batch.end();

		if (isPressed()) {
			dispose();
			game.setScreen(new MainMenu(game, resolution));
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	}
}

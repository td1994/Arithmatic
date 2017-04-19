package net.ted.arithmatic.screens;

// Copyright 2013 T.E.D.
import java.io.IOException;

import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class manages the opening logo for the game. Copyright 2013 T.E.D.
 */
public class Logo implements Screen {

	// Instance and Field Variables --------------------------------------------

	private ScreenResolution resolution;
	private Arithmatic game;
	private SpriteBatch batch;
	private Sprite bitmap; // logo bitmap
	private long beginTime; // time in which the logo starts
	private float opacity = 0; // logo opacity
	private boolean loaded;

	// Methods -----------------------------------------------------------------

	/**
	 * Constructor which initializes and sets values for field variables.
	 */
	public Logo(Arithmatic game, Texture logo, ScreenResolution resolution) {
		this.game = game;
		this.resolution = resolution;
		batch = new SpriteBatch();
		bitmap = new Sprite(logo);// gets bitmap from the GameLogic class
		bitmap.setScale(resolution.getScale());
		bitmap.setOrigin(0, 0);
		bitmap.setPosition(0, 0);
		beginTime = System.currentTimeMillis();
		loaded = false;
	}

	/**
	 * Draws and renders out the logo.
	 */
	@Override
	public void render(float delta) {
		batch.begin();
		bitmap.draw(batch);
		batch.end();

		long currentTime = System.currentTimeMillis();
		
		if(!loaded && opacity == 1f)
		{
			//load all assets needed for game
			try {
				Assets.LoadAssets(resolution);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loaded = true;
			beginTime = System.currentTimeMillis();
			currentTime = System.currentTimeMillis();
		}
		// Logo fades in for the first second
		else if (!loaded && currentTime - beginTime < 1000) {
			// Sets opacity at a value between 0 and 1
			opacity = (float) (currentTime - beginTime) / 1000;
		}
		// Last second has the logo fading out
		else if (loaded && currentTime - beginTime < 1000) {
			// Sets opacity at a value between 0 and 1
			opacity = (float) (1000 - (currentTime - beginTime)) / 1000;
		}
		// Logo display time has finished
		else if (loaded && currentTime - beginTime > 1000) {
			opacity = 0;
			dispose();
			game.setScreen(new Title(game, resolution));
		}
		// Rest of the time, the logo will just appear there
		else {
			opacity = 1f; // Opacity set to 1
		}
		bitmap.setColor(opacity, opacity, opacity, 1f);
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
		batch.dispose();
		bitmap.getTexture().dispose();
	}

}
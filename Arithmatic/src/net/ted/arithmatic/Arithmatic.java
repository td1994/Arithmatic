package net.ted.arithmatic;

import net.ted.arithmatic.screens.Logo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Arithmatic extends Game {

	// Instance and Field Variables --------------------------------------------

	private ScreenResolution resolution;
	public ActionResolver actionResolver;

	// Methods -----------------------------------------------------------------
	public Arithmatic(ActionResolver actionResolver)
	{
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create() {
		// Sets up the screen resolution
		resolution = new ScreenResolution();
		// First five seconds of application is the logo
		setScreen(new Logo(this, new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/logo.png")), resolution));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}

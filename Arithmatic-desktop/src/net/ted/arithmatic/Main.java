package net.ted.arithmatic;

import net.ted.arithmatic.Arithmatic;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Arithmatic";
		cfg.useGL20 = true;
		cfg.width = 540;
		cfg.height = 960;
		// cfg.fullscreen = true;
		// Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width,
		// Gdx.graphics.getDesktopDisplayMode().height, true);

		new LwjglApplication(new Arithmatic(new ActionResolverDesktop()), cfg);
	}
}

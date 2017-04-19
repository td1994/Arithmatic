package net.ted.arithmatic.screens;

import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class FinalScore {

	private boolean pressed;
	private FinalScoreSelection buttonSelected;

	private Button playAgain, leaderboard, mainMenu;

	private ScreenResolution resolution;

	public FinalScore(ScreenResolution resolution, Texture button) {

		this.resolution = resolution;
		
		playAgain = new Button("Play Again", button,
				Gdx.graphics.getWidth() / 2  - 
				(button.getWidth() * resolution.getScale()) - resolution.getPositionX(10),
				resolution.getPositionY(400), resolution.getScale());
		leaderboard = new Button("Leaderboard", button, Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10),
				resolution.getPositionY(400),
				resolution.getScale());
		mainMenu = new Button("Main Menu", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2, resolution.getPositionY(340),
				resolution.getScale());
	}

	public boolean selected(FinalScoreSelection select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane; switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!pressed
				&& (select == FinalScoreSelection.PLAY_AGAIN && playAgain
						.isClicked(touchPos))
				|| (select == FinalScoreSelection.LEADERBOARDS && leaderboard
						.isClicked(touchPos))
				|| (select == FinalScoreSelection.MAIN_MENU && mainMenu
						.isClicked(touchPos))) {
			buttonSelected = select;
			pressed = true;
		} else if (pressed && !Gdx.input.isTouched()
				&& select == buttonSelected) {
			isTrue = true;
			pressed = false;
		}
		return isTrue;
	}

	public void draw(SpriteBatch batch, BitmapFont font, int score, int type) {

		font.draw(batch, "Final Score: " + score, resolution.getPositionX(50),
				resolution.getPositionY(600));

		// TODO: Make sure it prints high score saved in Google Play Games
		font.draw(batch, "High Score: " + Assets.scores[type], resolution.getPositionX(50),
				resolution.getPositionY(550));

		playAgain.draw(batch);
		leaderboard.draw(batch);
		mainMenu.draw(batch);
	}

	public void dispose() {
		/*button.dispose();
		playAgain.disposeTexture();
		leaderboard.disposeTexture();
		mainMenu.disposeTexture();*/
	}
}

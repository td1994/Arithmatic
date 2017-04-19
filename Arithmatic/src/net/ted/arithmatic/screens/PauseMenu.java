package net.ted.arithmatic.screens;

import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class PauseMenu {

	// Instance and Field Variables --------------------------------------------

	private Button returnToGame, howToPlay, options, highScores,
			backToMainMenu;

	private boolean isPressed;

	private PauseMenuSelect buttonSelected;

	// Methods -----------------------------------------------------------------

	public PauseMenu(ScreenResolution resolution, Texture button) {

		returnToGame = new Button("Return To Game", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(620), resolution.getScale());
		howToPlay = new Button("How To Play", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(540), resolution.getScale());
		options = new Button("Achievements", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(460), resolution.getScale());
		highScores = new Button("High Scores", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(380), resolution.getScale());
		backToMainMenu = new Button("Back To Main Menu", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(300), resolution.getScale());

		isPressed = false;
	}

	public void draw(SpriteBatch batch) {
		returnToGame.draw(batch);
		howToPlay.draw(batch);
		options.draw(batch);
		highScores.draw(batch);
		backToMainMenu.draw(batch);
	}

	public boolean selected(PauseMenuSelect select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane; switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!isPressed
				&& (select == PauseMenuSelect.RETURN_TO_GAME && returnToGame
						.isClicked(touchPos))
				|| (select == PauseMenuSelect.HOW_TO_PLAY && howToPlay
						.isClicked(touchPos))
				|| (select == PauseMenuSelect.OPTIONS && options
						.isClicked(touchPos))
				|| (select == PauseMenuSelect.HIGH_SCORES && highScores
						.isClicked(touchPos))
				|| (select == PauseMenuSelect.BACK_TO_MAIN_MENU && backToMainMenu
						.isClicked(touchPos))) {
			buttonSelected = select;
			isPressed = true;
		} else if (isPressed && !Gdx.input.isTouched()
				&& select == buttonSelected) {
			isTrue = true;
			isPressed = false;
		}
		return isTrue;
	}

	public void dispose() {
		/*button.dispose();
		returnToGame.disposeTexture();
		howToPlay.disposeTexture();
		options.disposeTexture();
		highScores.disposeTexture();
		backToMainMenu.disposeTexture();*/
	}
}

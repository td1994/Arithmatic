package net.ted.arithmatic.screens;

import net.ted.arithmatic.GameState;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameSelect {

	// Instance and Field Variables --------------------------------------------

	private Button arcade, survival, backToMainMenu;
	private boolean isPressed;
	private GameSelection buttonSelected;
	private Texture button;

	// Methods -----------------------------------------------------------------

	public GameSelect(ScreenResolution resolution) {
		button = new Texture(Gdx.files.internal("data/button.png"));

		arcade = new Button("Arcade Mode", button,
				(Gdx.graphics.getWidth() - (button.getWidth() * resolution
						.getScale())) / 2, resolution.getPositionY(600),
				resolution.getScale());
		survival = new Button("Survival Mode", button,
				(Gdx.graphics.getWidth() - (button.getWidth() * resolution
						.getScale())) / 2, resolution.getPositionY(500),
				resolution.getScale());
		backToMainMenu = new Button("Back to Main Menu", button,
				(Gdx.graphics.getWidth() - (button.getWidth() * resolution
						.getScale())) - resolution.getPositionX(30),
				resolution.getPositionY(30), resolution.getScale());

		isPressed = false;
	}

	public boolean selected(GameSelection select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane; switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!isPressed
				&& (select == GameSelection.ARCADE && arcade
						.isClicked(touchPos))
				|| (select == GameSelection.SURVIVAL && survival
						.isClicked(touchPos))
				|| (select == GameSelection.BACK_TO_MAIN_MENU && backToMainMenu
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

	public GameState render(Arithmatic game, MainMenu menu, SpriteBatch batch) {
		GameState state = GameState.GAME_SELECT;
		arcade.draw(batch);
		survival.draw(batch);
		backToMainMenu.draw(batch);

		if (selected(GameSelection.ARCADE)) {
			state = GameState.MODE_SELECT_ARCADE;
		} else if (selected(GameSelection.SURVIVAL)) {
			state = GameState.MODE_SELECT_SURVIVAL;
		} else if (selected(GameSelection.BACK_TO_MAIN_MENU)) {
			state = GameState.MAIN_MENU;
		}
		return state;
	}

	public void dispose() {
		survival.disposeTexture();
		arcade.disposeTexture();
		backToMainMenu.disposeTexture();
	}
}

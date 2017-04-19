package net.ted.arithmatic.screens;

import net.ted.arithmatic.GameState;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Arcade;
import net.ted.arithmatic.game.Button;
import net.ted.arithmatic.game.Survival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ModeSelect {

	// Instance and Field Variables --------------------------------------------

	private Button standard, min5, min10, min15, backToMenu;
	private ScreenResolution resolution;
	private boolean pressed;
	private ModeSelection buttonSelected;

	// Methods -----------------------------------------------------------------

	public ModeSelect(ScreenResolution resolution, Texture button) {
		this.resolution = resolution;

		standard = new Button("Standard Rules", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(600), resolution.getScale());
		min5 = new Button("5 Minute Limit", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(500), resolution.getScale());
		min10 = new Button("10 Minute Limit", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(400), resolution.getScale());
		min15 = new Button("15 Minute Limit", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(300), resolution.getScale());
		backToMenu = new Button("Previous Menu", button,
				Gdx.graphics.getWidth() / 2, resolution.getPositionY(110), resolution.getScale());
		
		pressed = false;
	}

	public boolean selected(ModeSelection select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the y plane, switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!pressed
				&& (select == ModeSelection.STANDARD && standard
						.isClicked(touchPos))
				|| (select == ModeSelection.FIVE_MIN && min5
						.isClicked(touchPos))
				|| (select == ModeSelection.TEN_MIN && min10
						.isClicked(touchPos))
				|| (select == ModeSelection.FIFTEEN_MIN && min15
						.isClicked(touchPos))
				|| (select == ModeSelection.BACK_TO_MENU && backToMenu
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

	public GameState render(Arithmatic game, MainMenu menu,
			SpriteBatch batch, boolean mode) {
		GameState state;
		if (mode) {
			state = GameState.MODE_SELECT_ARCADE;
		} else {
			state = GameState.MODE_SELECT_SURVIVAL;
		}
		standard.draw(batch);
		min5.draw(batch);
		min10.draw(batch);
		min15.draw(batch);
		backToMenu.draw(batch);

		if (selected(ModeSelection.STANDARD)) {
			menu.dispose();
			if (mode) {
				//load.draw(batch, font);
				game.setScreen(new Arcade(game, resolution, 0));
			} else {
				//load.draw(batch, font);
				game.setScreen(new Survival(game, resolution, 0));
			}
		} else if (selected(ModeSelection.FIVE_MIN)) {
			menu.dispose();
			if (mode) {
				game.setScreen(new Arcade(game, resolution, 5));
			} else {
				game.setScreen(new Survival(game, resolution, 5));
			}
		} else if (selected(ModeSelection.TEN_MIN)) {
			menu.dispose();
			if (mode) {
				game.setScreen(new Arcade(game, resolution, 10));
			} else {
				game.setScreen(new Survival(game, resolution, 10));
			}
		} else if (selected(ModeSelection.FIFTEEN_MIN)) {
			menu.dispose();
			if (mode) {
				game.setScreen(new Arcade(game, resolution, 15));
			} else {
				game.setScreen(new Survival(game, resolution, 15));
			}
		} else if (selected(ModeSelection.BACK_TO_MENU)) {
			state = GameState.GAME_SELECT;
		}

		return state;
	}

	public void dispose() {
		/*standard.disposeTexture();
		min5.disposeTexture();
		min10.disposeTexture();
		min15.disposeTexture();
		backToMenu.disposeTexture();*/
	}
}

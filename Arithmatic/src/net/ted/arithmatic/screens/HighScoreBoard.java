package net.ted.arithmatic.screens;

// TODO: We're using Google Play, so do we still need this? I also removed the
// commented out code in the main game code that updated text files.
// Should we delete this class if it's taking up memory and not being used?

// TODO: Convert the methods to use Google Play instead of local text files.

import net.ted.arithmatic.ActionResolver.TimeMode;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.GameState;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class HighScoreBoard {

	// Instance and Field Variables --------------------------------------------

	private ScreenResolution resolution;
	private Arithmatic game;
	private Button a_standard, a_min5, a_min10, a_min15, s_standard, s_min5, s_min10, s_min15, backToMenu;
	private boolean isPressed;
	private HighScoreSelection buttonSelected;

	// Methods -----------------------------------------------------------------

	public HighScoreBoard(Arithmatic game, ScreenResolution resolution, Texture button) {
		this.resolution = resolution;
		this.game = game;
		
		a_standard = new Button("Arcade: Standard", button,
				(Gdx.graphics.getWidth() / 2 - (button.getWidth()  * resolution.getScale()) - resolution.getPositionX(10)),
				resolution.getPositionY(600), resolution.getScale());
		a_min5 = new Button("Arcade: 5 Minutes", button,
				(Gdx.graphics.getWidth() / 2 - (button.getWidth() * resolution.getScale()) - resolution.getPositionX(10)),
				resolution.getPositionY(500), resolution.getScale());
		a_min10 = new Button("Arcade: 10 Minutes", button,
				(Gdx.graphics.getWidth() / 2 - (button.getWidth() * resolution.getScale()) - resolution.getPositionX(10)),
				resolution.getPositionY(400), resolution.getScale());
		a_min15 = new Button("Arcade: 15 Minutes", button,
				(Gdx.graphics.getWidth() / 2 - (button.getWidth() * resolution.getScale()) - resolution.getPositionX(10)),
				resolution.getPositionY(300), resolution.getScale());
		s_standard = new Button("Survival: Standard", button,
				(Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10)),
				resolution.getPositionY(600), resolution.getScale());
		s_min5 = new Button("Survival: 5 Minutes", button,
				(Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10)),
				resolution.getPositionY(500), resolution.getScale());
		s_min10 = new Button("Survival: 10 Minutes", button,
				(Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10)),
				resolution.getPositionY(400), resolution.getScale());
		s_min15 = new Button("Survival: 15 Minutes", button,
				(Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10)),
				resolution.getPositionY(300), resolution.getScale());
		backToMenu = new Button("Previous Menu", button,
				Gdx.graphics.getWidth() / 2, resolution.getPositionY(110), resolution.getScale());
		
		isPressed = false;
	}

	public boolean selected(HighScoreSelection select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the y plane, switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!isPressed
				&& (select == HighScoreSelection.ARCADE_S && a_standard
						.isClicked(touchPos))
				|| (select == HighScoreSelection.ARCADE_5 && a_min5
						.isClicked(touchPos))
				|| (select == HighScoreSelection.ARCADE_10 && a_min10
						.isClicked(touchPos))
				|| (select == HighScoreSelection.ARCADE_15 && a_min15
						.isClicked(touchPos))
				|| (select == HighScoreSelection.SURVIVAL_S && s_standard
						.isClicked(touchPos))
				|| (select == HighScoreSelection.SURVIVAL_5 && s_min5
						.isClicked(touchPos))
				|| (select == HighScoreSelection.SURVIVAL_10 && s_min10
						.isClicked(touchPos))
				|| (select == HighScoreSelection.SURVIVAL_15 && s_min15
						.isClicked(touchPos))
				|| (select == HighScoreSelection.BACK_TO_MENU && backToMenu
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

	public GameState render(SpriteBatch batch) {
		GameState state = GameState.HIGH_SCORES;
		
		a_standard.draw(Assets.batch);
		a_min5.draw(Assets.batch);
		a_min10.draw(Assets.batch);
		a_min15.draw(Assets.batch);
		s_standard.draw(Assets.batch);
		s_min5.draw(Assets.batch);
		s_min10.draw(Assets.batch);
		s_min15.draw(Assets.batch);
		backToMenu.draw(Assets.batch);

		if (selected(HighScoreSelection.ARCADE_S)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(true, TimeMode.STANDARD);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.ARCADE_5)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(true, TimeMode.MIN5);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.ARCADE_10)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(true, TimeMode.MIN10);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.ARCADE_15)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(true, TimeMode.MIN15);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.SURVIVAL_S)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(false, TimeMode.STANDARD);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.SURVIVAL_5)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(false, TimeMode.MIN5);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.SURVIVAL_10)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(false, TimeMode.MIN10);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		} else if (selected(HighScoreSelection.SURVIVAL_15)) {
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.getLeaderboardGPGS(false, TimeMode.MIN15);
			}
			else
			{
				game.actionResolver.loginGPGS();
			}
		}else if (selected(HighScoreSelection.BACK_TO_MENU)) {
			state = GameState.GAME_SELECT;
		}
		return state;
	}

	public void dispose() {
		Assets.font.dispose();
	}

}

package net.ted.arithmatic.screens;

import net.ted.arithmatic.GameState;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

// TODO: This might be a culprit for the main menu loading time cause of all
// the stuff that needs to be loaded at once. I tried trimming it a little bit
// such as removing the Exit Assets.buttonP since most companies don't allow one past
// quality control, but I'll try to look at other ways to shorten it too.
public class MainMenu implements Screen {

	// Instance and Field Variables --------------------------------------------

	private GameState state = GameState.MAIN_MENU;
	private Arithmatic game;
	private ModeSelect modeSelect;
	private HowToPlay howToPlaySelect;
	private HighScoreBoard highScoreBoard;
	private Button arcade, survival, howToPlay, options, highScores;
	private boolean pressed;
	private MainMenuSelect buttonSelected;

	// Methods -----------------------------------------------------------------

	public MainMenu(Arithmatic game, ScreenResolution resolution) {
		this.game = game;
		howToPlaySelect = new HowToPlay(resolution, Assets.buttonP, 0);
		highScoreBoard = new HighScoreBoard(game, resolution, Assets.buttonP);

		arcade = new Button("Arcade Mode", Assets.buttonP,
				(Gdx.graphics.getWidth() - Assets.buttonP.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(620), resolution.getScale());
		survival = new Button("Survival Mode", Assets.buttonP,
				(Gdx.graphics.getWidth() - Assets.buttonP.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(540), resolution.getScale());
		howToPlay = new Button("How To Play", Assets.buttonP,
				(Gdx.graphics.getWidth() - Assets.buttonP.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(460), resolution.getScale());
		options = new Button("Achievements", Assets.buttonP,
				(Gdx.graphics.getWidth() - Assets.buttonP.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(380), resolution.getScale());
		highScores = new Button("High Scores", Assets.buttonP,
				(Gdx.graphics.getWidth() - Assets.buttonP.getWidth() * resolution.getScale()) / 2,
				resolution.getPositionY(300), resolution.getScale());

		pressed = false;
		modeSelect = new ModeSelect(resolution, Assets.buttonP);
	}

	private boolean selected(MainMenuSelect select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane; switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!pressed
				&& (select == MainMenuSelect.ARCADE && arcade
						.isClicked(touchPos))
				|| (select == MainMenuSelect.SURVIVAL && survival
						.isClicked(touchPos))
				|| (select == MainMenuSelect.HOW_TO_PLAY && howToPlay
						.isClicked(touchPos))
				|| (select == MainMenuSelect.OPTIONS && options
						.isClicked(touchPos))
				|| (select == MainMenuSelect.HIGH_SCORES && highScores
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (state == GameState.MODE_SELECT_SURVIVAL) {
			Assets.batch.begin();
			Assets.backgroundP.draw(Assets.batch);
			Assets.lightingP.draw(Assets.batch);
			state = modeSelect.render(game, this, Assets.batch, false);
			Assets.batch.end();
		} else if (state == GameState.MODE_SELECT_ARCADE) {
			Assets.batch.begin();
			Assets.backgroundP.draw(Assets.batch);
			Assets.lightingP.draw(Assets.batch);
			state = modeSelect.render(game, this, Assets.batch, true);
			Assets.batch.end();
		} else if (state == GameState.HOW_TO_PLAY) {
			Assets.batch.begin();
			Assets.backgroundP.draw(Assets.batch);
			Assets.lightingP.draw(Assets.batch);
			state = howToPlaySelect.render(this, Assets.batch);
			Assets.batch.end();
		} else if (state == GameState.HIGH_SCORES) {
			Assets.batch.begin();
			Assets.backgroundP.draw(Assets.batch);
			Assets.lightingP.draw(Assets.batch);
			state = highScoreBoard.render(Assets.batch);
			Assets.batch.end();
		} else {
			Assets.batch.begin();
			Assets.backgroundP.draw(Assets.batch);
			Assets.lightingP.draw(Assets.batch);
			arcade.draw(Assets.batch);
			survival.draw(Assets.batch);
			howToPlay.draw(Assets.batch);
			options.draw(Assets.batch);
			highScores.draw(Assets.batch);
			Assets.batch.end();

			// Determine which Assets.buttonP is pressed and load its screen
			// Arcade - Load the screen which selects arcade game mode
			if (selected(MainMenuSelect.ARCADE)) {
				state = GameState.MODE_SELECT_ARCADE;
			}
			// Survival - Load the screen that selects survival game mode
			else if (selected(MainMenuSelect.SURVIVAL)) {
				state = GameState.MODE_SELECT_SURVIVAL;
			}
			// How to Play - Loads cycle-able pages of instructions
			else if (selected(MainMenuSelect.HOW_TO_PLAY)) {
				state = GameState.HOW_TO_PLAY;
			}
			else if (selected(MainMenuSelect.OPTIONS)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getAchievementsGPGS();
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			}
			// High Scores - If connection is present, loads Google Play scores
			else if (selected(MainMenuSelect.HIGH_SCORES)) {
				state = GameState.HIGH_SCORES;
			}
			// TODO: Removed Exit Assets.buttonP since most companies disallow it.
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
		/*Assets.batch.dispose();
		arcade.disposeTexture();
		survival.disposeTexture();
		howToPlay.disposeTexture();
		options.disposeTexture();
		highScores.disposeTexture();
		modeSelect.dispose();*/
	}
}

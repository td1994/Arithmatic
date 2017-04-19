package net.ted.arithmatic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import net.ted.arithmatic.GameState;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.ActionResolver.TimeMode;
import net.ted.arithmatic.screens.FinalScore;
import net.ted.arithmatic.screens.FinalScoreSelection;
import net.ted.arithmatic.screens.HowToPlay;
import net.ted.arithmatic.screens.MainMenu;
import net.ted.arithmatic.screens.PauseMenu;
import net.ted.arithmatic.screens.PauseMenuSelect;

// TODO: Pretty sure this has to be deleted.
//import net.ted.number_crunch.R;

/**
 * GameTracker keeps track of the level of the player, the score, the board, and
 * the timer.
 * 
 * Copyright 2013 T.E.D.
 */
public class Survival implements Screen {

	// Instance and Field Variables --------------------------------------------

	// Name of the class. This line is not currently being used.
	// private final String TAG = GameTracker.class.getName();

	private Arithmatic game;
	// Touched-based variables
	private boolean isJustTouched, isResetTouched, isPausedTouched, isPaused,
			isGameOver, isHowToPlay;

	private int targetValue; // Value that needs to be obtained by the player
	private Random random; // Random number selector for the obtained value

	private int mode;

	private Board board; // Board class reference variable
	private Level level; // Calculates the level, experience, and score
	private Score score; // the game's score

	private ScreenResolution resolution;

	private Button resetButton, pauseButton;

	private PauseMenu pauseMenu;
	private FinalScore finalScore;

	private long gameOverTime, gameOverTimeLimit, prevTime;
	
	private HowToPlay htp;
	
	private int value;
	
	private TimeMode time;
	
	private boolean noReset;

	// Methods -----------------------------------------------------------------

	/**
	 * Gets the resources and the selected tiles from the GameLogic class.
	 */
	public Survival(Arithmatic game, ScreenResolution res, int time) {
		
		this.game = game;
		resolution = res;
		
		random = new Random(); // Implements Random
		finalScore = new FinalScore(resolution, Assets.buttonF);
		mode = time;
		
		board = new Board(resolution);
		
		resetButton = new Button("Reset Board", Assets.buttonF,
				(Gdx.graphics.getWidth() / 2) - (Assets.buttonF.getWidth() * resolution.getScale()) - resolution.getPositionX(10),
				resolution.getPositionY(150),
				resolution.getScale());
		pauseButton = new Button("Pause Game", Assets.buttonF,
				(Gdx.graphics.getWidth() / 2) + resolution.getPositionX(10), resolution.getPositionY(150),
				resolution.getScale());

		score = new Score(resolution, Assets.buttonF);

		pauseMenu = new PauseMenu(resolution, Assets.buttonF);
		
		htp = new HowToPlay(res, Assets.buttonF, 5);
		
		switch(mode)
		{
			case 0:
				this.time = TimeMode.STANDARD;
				value = 4;
				break;
			case 5:
				this.time = TimeMode.MIN5;
				value = 5;
				break;
			case 10:
				this.time = TimeMode.MIN10;
				value = 6;
				break;
			default:
				this.time = TimeMode.MIN15;
				value = 7;
				break;
		}
		startGame(mode);
	}

	/**
	 * Calculates a new achieve value.
	 */
	private void getNewGoal() {
		int limit = 10 * (level.getLevel() - 1); // Limit increases by 10 each level

		// Randomly selects value from limit to limit + 9
		targetValue = random.nextInt(10) + limit;
	}

	/**
	 * This method is called when the player releases the touch-screen and
	 * creates an equation.
	 */
	public void makePlay() {
		// Checks to see if equation is valid
		if (board.validMove() && board.calculate(game) == targetValue) {
			// Update experience
			if (level.addExperience(game, board.getTileCount(), board.getEquation(), noReset)) {
				score.addTileScore(100, level.getLevel());
			}
			score.addTileScore(board.getScore(), level.getLevel());

			board.implementNewPanels(level.getLevel(), Assets.tilesF, Assets.tilesSelectedF); // Implement new tiles

			getNewGoal(); // A new value is selected
		}

		board.resetEquation(); // Previous equation is deleted
	}

	/**
	 * Checks if the screen is being touched.
	 * 
	 * TODO: Get rid of magic numbers.
	 */
	public void checkInputs() {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the y plane, switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		if (!isPaused && !isGameOver) {
			if (Gdx.input.isTouched()) {

				// When the player's finger is within the vicinity of the board
				if (board.isWithinBoard(touchPos)
						&& (!isResetTouched || !isPausedTouched)) {
					isJustTouched = true;
					board.makeSelection(touchPos);
				}
				// Player touches the reset button
				else if (resetButton.isClicked(touchPos)
						&& (!isJustTouched || !isPausedTouched)) {
					isResetTouched = true;
				}
				// Player touches the pause button
				else if (pauseButton.isClicked(touchPos)
						&& (!isJustTouched || !isResetTouched)) {
					isPausedTouched = true;
				}
			}

			// When the player lets go of the screen
			else if (!Gdx.input.isTouched()) {
				
				// Player pressed the reset button
				if (isResetTouched) {
					noReset = false;
					board.setBoard(level.getLevel(), Assets.tilesF, Assets.tilesSelectedF); // A new board is created
					isResetTouched = false;
				} else if (isPausedTouched) {
					isPaused = true;
					isPausedTouched = false;
				} else if (isJustTouched) {
					// Log.d(TAG, "Making Play");
					makePlay(); // Game checks if the player made a valid move
					isJustTouched = false;
				}
			}
		} else if (!isGameOver) {
			if (pauseMenu.selected(PauseMenuSelect.RETURN_TO_GAME)) {
				level.previousTime = System.currentTimeMillis();
				isPaused = false;	
			}
			else if (pauseMenu.selected(PauseMenuSelect.HOW_TO_PLAY)) {
				isHowToPlay = true;
			}
			else if(pauseMenu.selected(PauseMenuSelect.OPTIONS)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getAchievementsGPGS();
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			}
			else if(pauseMenu.selected(PauseMenuSelect.HIGH_SCORES)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getLeaderboardGPGS(false, time);
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			}
			else if (pauseMenu.selected(PauseMenuSelect.BACK_TO_MAIN_MENU)) {
				dispose();
				game.setScreen(new MainMenu(game, resolution));
			}
		}
	}

	/**
	 * Updates the timer.
	 */
	public void updateTimer() {
		if (isGameOver) {
			gameOverTime += System.currentTimeMillis() - prevTime;
			prevTime = System.currentTimeMillis();
		}

		else if (!isPaused) {
			// Game over when timer hits 0. Check for possible high scores.
			if (level.isTimeUp() || board.divideByZero) {
				if(game.actionResolver.getSignedInGPGS() && board.divideByZero)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQFQ");
					board.divideByZero = false;
				}
				prevTime = System.currentTimeMillis();
				isGameOver = true;
				if(Assets.scores[value] < score.getScore())
				{
					Assets.scores[value] = score.getScore();
					BufferedWriter out = null;
					try {
						out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external("data/scores.txt").write(false)));
						for (int i = 0; i < value + 1; i++) {
							out.write(Integer.toString(Assets.scores[i]));
						}

					} catch (Throwable e) {
					} finally {
						try {
							if (out != null) out.close();
						} catch (IOException e) {
						}
					}
					
					if(game.actionResolver.getSignedInGPGS())
					{
						game.actionResolver.submitScoreGPGS(false, time, Assets.scores[value]);
					}
				}
			}

			// Game is still ongoing
			else {
				level.timer(System.currentTimeMillis());
			}
		}
	}

	/**
	 * Starts new game.
	 */
	public void startGame(int time) {
		level = new Level(resolution, time); // Player starts at level 1
		board.setBoard(level.getLevel(), Assets.tilesF, Assets.tilesSelectedF); // Sets the board (starting at level 1)
		score.resetScore();
		isGameOver = false;
		gameOverTime = 0;
		gameOverTimeLimit = 2000;
		prevTime = 0;

		isJustTouched = false;
		getNewGoal();

		isResetTouched = false; // Reset button not pressed at start
		isPausedTouched = false;
		isPaused = false;

		isGameOver = false;
		gameOverTime = 0;
		gameOverTimeLimit = 2000;
		prevTime = 0;
		isHowToPlay = false;
		noReset = true;
	}

	@Override
	public void render(float delta) {
		// TODO: Very minor, but unused variables take a tiny amount of time to
		// initialize. If it's not needed, we could remove them later.
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		// background.draw(Assets.batch);

		// Displays the board by transferring the canvas over to the Board class
		Assets.batch.begin();
		Assets.backgroundF.draw(Assets.batch);
		Assets.lightingF.draw(Assets.batch);
		if (!isPaused) {
			if (isGameOver && gameOverTime >= gameOverTimeLimit) {
				finalScore.draw(Assets.batch, Assets.font, score.getScore(), value);
				if (finalScore.selected(FinalScoreSelection.PLAY_AGAIN)) {
					startGame(mode);
				} else if (finalScore.selected(FinalScoreSelection.LEADERBOARDS)) {
					if(game.actionResolver.getSignedInGPGS())
					{
						game.actionResolver.getLeaderboardGPGS(false, time);
					}
					else
					{
						game.actionResolver.loginGPGS();
					}
				} else if (finalScore.selected(FinalScoreSelection.MAIN_MENU)) {
					dispose();
					game.setScreen(new MainMenu(game, resolution));
				}
			} else {
				board.displayBoard(Assets.batch);

				// Draw level label and number to get
				Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f * 0.5f); // Times normal
																// size
				Assets.font.draw(Assets.batch, "Target", resolution.getPositionX(60), h
						- resolution.getPositionY(30));
				Assets.font.draw(Assets.batch, "Time Left", resolution.getPositionX(310), h
						- resolution.getPositionY(30));

				Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f);
				Assets.font.draw(Assets.batch, "" + targetValue, resolution.getPositionX(80),
						h - resolution.getPositionY(80));

				if (level.getTime() <= 0) {
					Assets.font.draw(Assets.batch, "0", resolution.getPositionX(330), h
							- resolution.getPositionY(80));
				} else {
					Assets.font.draw(Assets.batch, level.getStringTime(),
							resolution.getPositionX(330),
							h - resolution.getPositionY(80));
				}

				Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f);

				if (isGameOver) {
					Assets.font.draw(Assets.batch, "Game Over", Gdx.graphics.getWidth() / 2
							- Assets.font.getBounds("Game Over").width / 2,
							resolution.getPositionY(500));
				}

				level.draw(Assets.batch);
				Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f * 0.4f);
				Assets.font.draw(Assets.batch, "Level " + level.getLevel(),
						resolution.getPositionX(40),
						resolution.getPositionY(240));
				Assets.font.draw(Assets.batch, "Score: " + score.getScore(),
						resolution.getPositionX(240),
						resolution.getPositionY(240));

				// Draws the reset and pause buttons
				resetButton.draw(Assets.batch);
				pauseButton.draw(Assets.batch);
			}
		}
		// Game is paused
		else {
			if(isHowToPlay)
			{
				GameState state = htp.render(null, Assets.batch);
				if(state == GameState.MAIN_MENU)
				{
					isHowToPlay = false;
				}
			}
			else
			{
				pauseMenu.draw(Assets.batch);
			}
		}
		Assets.batch.end();
		updateTimer();
		checkInputs();
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
		/*pauseMenu.dispose();
		score.dispose();
		Assets.batch.dispose();
		board.dispose();
		background.dispose();
		lighting.dispose();*/
	}
}

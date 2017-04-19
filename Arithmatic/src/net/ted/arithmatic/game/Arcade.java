package net.ted.arithmatic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import net.ted.arithmatic.ActionResolver.TimeMode;
import net.ted.arithmatic.GameState;
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.screens.FinalScore;
import net.ted.arithmatic.screens.FinalScoreSelection;
import net.ted.arithmatic.screens.HowToPlay;
import net.ted.arithmatic.screens.MainMenu;
import net.ted.arithmatic.screens.PauseMenu;
import net.ted.arithmatic.screens.PauseMenuSelect;

public class Arcade implements Screen {

	// Instance and Field Variables --------------------------------------------

	private Arithmatic game;

	// Touched-based variables
	private boolean justTouched, paused, pausedTouched, isHowToPlay;

	private boolean gameOver, levelComplete, nextLevel, noStops = false;

	private int targetValue; // Value that needs to be obtained by the player
	private Random random; // Random number selector for the obtained value

	private int wrongAttempts;

	private Board board; // Board class reference variable
	private Score score;

	private ScreenResolution resolution;

	private Button pauseButton;
	private PauseMenu pauseMenu;

	private long currentTime, finishedStartTime, finishedElapsedTime,
			initialTime, prevTime, timeLeft;
	private String time;
	private int level, mode;

	private FinalScore finalScore;
	
	private HowToPlay htp;
	
	private int value;
	
	private TimeMode timeMode;

	// Methods -----------------------------------------------------------------

	/**
	 * Gets the resources and the selected tiles from the GameLogic class.
	 */
	public Arcade(Arithmatic game, ScreenResolution res, int time) {
		// Sets the board by retrieving the tile bitmaps
		this.game = game;
		resolution = res;

		finalScore = new FinalScore(resolution, Assets.buttonMD);

		random = new Random(); // Implements Random
		mode = time;

		// Pause-related variable initialization
		pauseButton = new Button("Pause Game", Assets.buttonMD,
				resolution.getPositionX(250), resolution.getPositionY(150),
				resolution.getScale());
		
		board = new Board(resolution);
		score = new Score(resolution, Assets.buttonMD);
		
		htp = new HowToPlay(res, Assets.buttonF, 4);
		
		switch(mode)
		{
			case 0:
				timeMode = TimeMode.STANDARD;
				value = 0;
				break;
			case 5:
				timeMode = TimeMode.MIN5;
				value = 1;
				break;
			case 10:
				timeMode = TimeMode.MIN10;
				value = 2;
				break;
			case 15:
				timeMode = TimeMode.MIN15;
				value = 3;
				break;
		}
		
		startGame(mode);
	}

	/**
	 * Calculates a new achieve value.
	 */
	private void getNewGoal() {
		int lowerLimit = 100 * level; // Limit increases by 100 each level

		// Randomly selects value from lower limit to upper limit
		targetValue = random.nextInt(101) + lowerLimit;
	}

	/**
	 * This method is called when the player releases the touch-screen and
	 * creates an equation.
	 */
	public void makePlay() {

		// Checks to see if equation is valid
		if (board.validMove()) {

			double calculated = board.calculate(game);

			// If player has chipped away from the target
			if (calculated <= targetValue) {
				targetValue -= calculated;
				score.addTileScore(board.getScore(), level);
				wrongAttempts++;

				if (targetValue <= 0) {
					if (game.actionResolver.getSignedInGPGS()) 
					{
						if(level == 1)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQCQ");
						}
						else if(level == 5)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQCg");
						}
						else if(level == 10)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQCw");
						}
						else if(level == 20)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQDA");
						}
						else if(level == 50)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQDQ");
						}
						else if(level == 100)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQDg");
						}
						
						game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQJA");
					}
					if (!noStops) {
						levelComplete = true;
						score.clculateBonuses(game, wrongAttempts, level, initialTime,
								timeLeft);
						finishedStartTime = System.currentTimeMillis();
						finishedElapsedTime = System.currentTimeMillis();
					} else {
						getNewGoal();
						wrongAttempts = 0;
						score.addTileScore(1000, level);
						level++;
						score.resetBonus();
					}
				}

				// Implement new tiles
				board.implementNewPanels(level, Assets.tilesMD, Assets.tilesSelectedMD);
			}

			// getNewGoal(); // A new value is selected
		}

		board.resetEquation(); // Previous equation is deleted
	}

	/**
	 * Checks if the screen is being touched TODO: Get rid of magic numbers
	 */
	public void checkInputs() {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane; switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		if (!paused && !levelComplete && !gameOver) {
			if (Gdx.input.isTouched()) {

				// When the player's finger is within the vicinity of the board
				if (board.isWithinBoard(touchPos) && !pausedTouched) {
					justTouched = true;
					board.makeSelection(touchPos);
				} else if (!justTouched && pauseButton.isClicked(touchPos)) {
					pausedTouched = true;
				}
			}

			// When the player lets go of the screen
			else if (!Gdx.input.isTouched()) {
				if (justTouched) {
					makePlay(); // Game checks if the player made a valid move
					justTouched = false;
				} else if (pausedTouched) {
					paused = true;
					pausedTouched = false;
				}
			}
		} else if (levelComplete
				&& finishedElapsedTime - finishedStartTime >= 2000 && nextLevel) {
			level++;
			board.setBoard(level, Assets.tilesMD, Assets.tilesSelectedMD);
			score.resetBonus();
			getNewGoal();
			timeLeft = initialTime;
			prevTime = System.currentTimeMillis();
			levelComplete = false;
			nextLevel = false;
			wrongAttempts = 0;
		} else if (paused) {
			if (pauseMenu.selected(PauseMenuSelect.RETURN_TO_GAME)) {
				prevTime = System.currentTimeMillis();
				paused = false;
			} else if (pauseMenu.selected(PauseMenuSelect.HOW_TO_PLAY)) {
				isHowToPlay = true;
			} else if(pauseMenu.selected(PauseMenuSelect.OPTIONS)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getAchievementsGPGS();
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			} else if(pauseMenu.selected(PauseMenuSelect.HIGH_SCORES)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getLeaderboardGPGS(true, timeMode);
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			}else if (pauseMenu.selected(PauseMenuSelect.BACK_TO_MAIN_MENU)) {
				dispose();
				game.setScreen(new MainMenu(game, resolution));
			}
		}
	}

	/**
	 * Updates the timer.
	 */
	public void updateTimer() {
		if (!paused && !levelComplete && !gameOver) {
			currentTime = System.currentTimeMillis();
			timeLeft -= currentTime - prevTime;
			prevTime = currentTime;
			time = String.valueOf((int) Math.ceil(timeLeft / 1000) + 1);

			// Game over: Time ran out
			if (timeLeft <= 0 || board.divideByZero) {
				if(game.actionResolver.getSignedInGPGS() && board.divideByZero)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQFQ");
					board.divideByZero = false;
				}
				gameOver = true;
				finishedStartTime = System.currentTimeMillis();
				finishedElapsedTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * Starts new game.
	 */
	public void startGame(int time) {
		
		pausedTouched = false;
		pauseMenu = new PauseMenu(resolution, Assets.buttonMD);
		
		level = 1;
		board.setBoard(level, Assets.tilesMD, Assets.tilesSelectedMD);
		score.resetScore();
		justTouched = false;
		getNewGoal();

		// button = new Texture("data/button.png"); // Loads button png image
		// resetTouched = false; // Reset button not pressed at start

		// resetButton = new Button("Reset Board", button,
		// resolution.getPositionX(10),
		// resolution.getPositionY(100), resolution.getScale());

		levelComplete = false;
		wrongAttempts = 0;

		currentTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();

		if (time > 0) {
			timeLeft = time * 60 * 1000;
			this.time = Integer.toString(time * 60);
			noStops = true;
		} else {
			timeLeft = 30000;
			this.time = "30";
		}
		initialTime = timeLeft;

		gameOver = false;
		nextLevel = false;
		
		isHowToPlay = false;
	}

	@Override
	public void render(float delta) {
		Assets.batch.begin();

		// TODO: Should we remove this line to save time?
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Assets.backgroundMD.draw(Assets.batch);
		Assets.lightingMD.draw(Assets.batch);
		if (!paused
				&& !(levelComplete && finishedElapsedTime - finishedStartTime >= 2000)
				&& !(gameOver && finishedElapsedTime - finishedStartTime >= 2000)) {

			// Displays board by transferring the canvas over to the Board class
			board.displayBoard(Assets.batch);

			// Draw level label and number to get
			Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f * 0.5f); // Times normal size

			if (levelComplete && finishedElapsedTime - finishedStartTime < 2000) {
				Assets.font.draw(Assets.batch, "Level Complete", Gdx.graphics.getWidth() / 2
						- Assets.font.getBounds("Level Complete").width / 2,
						resolution.getPositionY(500));
				finishedElapsedTime = System.currentTimeMillis();
			} else if (gameOver
					&& finishedElapsedTime - finishedStartTime < 2000) {
				Assets.font.draw(Assets.batch, "Game Over", Gdx.graphics.getWidth() / 2
						- Assets.font.getBounds("Game Over").width / 2,
						resolution.getPositionY(500));
				
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
						game.actionResolver.submitScoreGPGS(true, timeMode, Assets.scores[value]);
					}
				}
				finishedElapsedTime = System.currentTimeMillis();
			}

			Assets.font.draw(Assets.batch, "Target", resolution.getPositionX(30), h
					- resolution.getPositionY(20));
			Assets.font.draw(Assets.batch, "Time", resolution.getPositionX(250), h
					- resolution.getPositionY(20));

			Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f);

			Assets.font.draw(Assets.batch, "" + targetValue, resolution.getPositionX(20), h
					- resolution.getPositionY(50));

			if (timeLeft <= 0) {
				Assets.font.draw(Assets.batch, "0", resolution.getPositionX(250), h
						- resolution.getPositionY(50));
			} else {
				Assets.font.draw(Assets.batch, time, resolution.getPositionX(250), h
						- resolution.getPositionY(50));
			}

			Assets.font.setScale((float)Gdx.graphics.getWidth() / 480f * 0.4f);

			Assets.font.draw(Assets.batch, "Level " + level, resolution.getPositionX(40),
					resolution.getPositionY(250));
			Assets.font.draw(Assets.batch, "Score: " + score.getScore(),
					resolution.getPositionX(240), resolution.getPositionY(250));

			// Draws the Pause button
			pauseButton.draw(Assets.batch);
		} else if (levelComplete
				&& finishedElapsedTime - finishedStartTime >= 2000) {
			nextLevel = score.printBonusScores(Assets.batch);
		} else if (gameOver && finishedElapsedTime - finishedStartTime >= 2000) {
			// TODO: Google Play high scores

			finalScore.draw(Assets.batch, Assets.font, score.getScore(), value);
			if (finalScore.selected(FinalScoreSelection.PLAY_AGAIN)) {
				startGame(mode);
			} else if (finalScore.selected(FinalScoreSelection.LEADERBOARDS)) {
				if(game.actionResolver.getSignedInGPGS())
				{
					game.actionResolver.getLeaderboardGPGS(true, timeMode);
				}
				else
				{
					game.actionResolver.loginGPGS();
				}
			} else if (finalScore.selected(FinalScoreSelection.MAIN_MENU)) {
				dispose();
				game.setScreen(new MainMenu(game, resolution));
			}
		}else
		{
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
		/*Assets.batch.dispose();
		board.dispose();
		background.dispose();
		lighting.dispose();
		pauseMenu.dispose();
		score.dispose();*/
	}
}
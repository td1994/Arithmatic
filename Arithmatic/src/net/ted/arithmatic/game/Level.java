package net.ted.arithmatic.game;

// Copyright 2013 T.E.D.
import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Manages the level properties of the game, including the timer and the
 * experience bar.
 */
public class Level {

	// Instance and Field Variables --------------------------------------------

	// TODO: We have unused variables. It's minor, but those take time to
	// initialize.

	private int level;
	private double experience, goal;
	private long timeLeft;
	private boolean limitedTime = false;
	private String time;
	
	long previousTime; // Previous time in which the timer was updated

	int barWidth, originalWidth;

	// Methods -----------------------------------------------------------------

	/**
	 * Presets for when the game starts. These are the starting values.
	 */
	public Level(ScreenResolution resolution, int time) {
		level = 1; // Player starts at level 1
		experience = 0; // Player starts with 0 experience
		goal = 30; // Player needs 100 experience to reach level 2
		
		if (time > 0) {
			timeLeft = time * 60 * 1000;
			limitedTime = true;
			this.time = Integer.toString(time * 60);
		} else {
			timeLeft = 30000;
			this.time = "30";
		}

		// Previous time starts as soon as the game starts
		previousTime = System.currentTimeMillis();

		Assets.bar = new Sprite(new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/bar.png")));
		Assets.bar.setScale(resolution.getScale());
		Assets.bar.setOrigin(0, 0);
		Assets.bar.setPosition(resolution.getPositionX(30),
				resolution.getPositionY(210));

		originalWidth = (int) Assets.bar.getWidth();
		barWidth = 0;
		Assets.bar.setSize(barWidth, Assets.bar.getHeight());
	}

	/**
	 * Returns the player's current level.
	 * 
	 * @return Current level.
	 */
	public int getLevel() {
		return level;
	}
	
	public String getStringTime()
	{
		return time;
	}

	/**
	 * Timer of the game, decreasing.
	 * 
	 * @param currentTime
	 *            The current time remaining.
	 */
	public long timer(long currentTime) {
		timeLeft -= currentTime - previousTime;
		time = String.valueOf((int) Math.ceil(timeLeft / 1000) + 1);
		return previousTime = currentTime;
	}

	/**
	 * Adds experience for every time the player gets an equation correct. The
	 * player gets experience depending on the amount of tiles the player uses.
	 */
	public boolean isTimeUp() {
		return timeLeft <= 0;
	}

	public long getTime() {
		return timeLeft;
	}

	public boolean addExperience(Arithmatic game, int tiles, int[] equation, boolean noReset) {
		boolean leveledUp = false;
		// Checks through the entire equation to see which tiles the player used
		long addedTime = tiles * 1000;
		if (!limitedTime) {
			if (addedTime + timeLeft > 60000) {
				timeLeft = 60000;
			} else {
				timeLeft += addedTime;
			}
		}

		for (int i = 0; i < tiles; i++) {
			// Addition and subtraction operators
			if (equation[i] == 10 || equation[i] == 11) {
				experience += 8 * level;
			}

			// Multiplication and division operators
			else if (equation[i] == 12 || equation[i] == 13) {
				experience += 16 * level;
			}

			// Power (exponential) operators
			else if (equation[i] == 14) {
				experience += 32 * level;
			}
			// Numbers
			else {
				experience += 4 * level;
			}
		}

		// If player's experience exceeds the goal, then the player levels up
		if (experience >= goal) {
			levelUp();
			leveledUp = true;
			if (game.actionResolver.getSignedInGPGS()) 
			{
				if(level == 2)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQDw");
				}
				else if(level == 6)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQEA");
				}
				else if(level == 11)
				{
					if(noReset)
					{
						game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQIg");
					}
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQEQ");
				}
				else if(level == 21)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQEg");
				}
				else if(level == 51)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQEw");
				}
				else if(level == 101)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQFA");
				}
				
				game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQJQ");
			}
		}

		// Current time becomes the new previous time
		barWidth = (int) (originalWidth * (experience / goal));
		Assets.bar.setSize(barWidth, Assets.bar.getHeight());

		return leveledUp;
	}

	/**
	 * Levels up the player. The level increases by one, the goal is multiplied
	 * by two, and the decrease rate is multiplied by two (keep timer the same).
	 */
	private void levelUp() {
		level++;
		experience -= goal;
		if (!limitedTime) {
			if (30000 + timeLeft > 60000) {
				timeLeft = 60000;
			} else {
				timeLeft += 30000;
			}
		}
		
		goal += 30 * level;
	}

	public void draw(SpriteBatch batch) {
		Assets.bar.draw(batch);
	}
}

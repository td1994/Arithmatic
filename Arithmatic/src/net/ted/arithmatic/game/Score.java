package net.ted.arithmatic.game;

import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Score {

	// Instance and Field Variables --------------------------------------------

	private ScreenResolution resolution;
	private int score, timeBonus, moveBonus, oneMoveBonus, speedBonus, perfectBonus, perfectStreak;
	private Button nextLevel;
	private boolean pressed;

	// Methods -----------------------------------------------------------------

	public Score(ScreenResolution resolution, Texture button) {
		this.resolution = resolution;
		score = 0;
		nextLevel = new Button("Next Level", button,
				resolution.getPositionX(270), resolution.getPositionY(150), resolution.getScale());
		pressed = false;
		perfectStreak = 0;
	}

	public int getScore() {
		return score;
	}
	
	public void resetScore(){
		score = 0;
	}

	public int addTileScore(int init, int level) {
		int finalAdd = init * level;
		score += finalAdd;
		return finalAdd;
	}

	public void clculateBonuses(Arithmatic game, int moves, int level, long startTime,
			long timeLeft) {

		timeBonus = (int) Math.ceil(timeLeft / 1000) * 10 * level;

		if (moves <= 10) {
			moveBonus = (10 - (moves - 1)) * 10 * level;
		}
		if (moves == 1) {
			oneMoveBonus = 100 * level;
		}
		if (startTime - timeLeft <= 10000) {
			speedBonus = 100 * level;
		}
		
		if (oneMoveBonus > 0 && speedBonus > 0)
		{
			perfectBonus = 1000 * level;
			if(game.actionResolver.getSignedInGPGS())
			{
				game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQIA");
				perfectStreak++;
				if(perfectStreak == 5)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQIQ");
				}
			}
		}
	}

	public boolean printBonusScores(SpriteBatch batch) {
		Assets.font.setScale(0.75f * (float)Gdx.graphics.getWidth() / 480f);
		Assets.font.draw(batch, "Results",
				Gdx.graphics.getWidth() / 2 - Assets.font.getBounds("Results").width
						/ 2, resolution.getPositionY(800));
		Assets.font.setScale(0.4f * (float)Gdx.graphics.getWidth() / 480f);
		Assets.font.draw(batch, "Time Bonus:			" + timeBonus,
				resolution.getPositionX(50), resolution.getPositionY(680));
		Assets.font.draw(batch, "Move Bonus:			" + moveBonus,
				resolution.getPositionX(50), resolution.getPositionY(600));
		Assets.font.draw(batch, "One Move Bonus:		" + oneMoveBonus,
				resolution.getPositionX(50), resolution.getPositionY(520));
		Assets.font.draw(batch, "Speed Bonus:			" + speedBonus,
				resolution.getPositionX(50), resolution.getPositionY(440));
		Assets.font.draw(batch, "Total Score:			" + (score + getTotalBonus()),
				resolution.getPositionX(50), resolution.getPositionY(360));
		if (oneMoveBonus > 0 && speedBonus > 0) {
			Assets.font.draw(batch, "PERFECT!",
					Gdx.graphics.getWidth() / 2
							- Assets.font.getBounds("PERFECT!").width / 2,
					resolution.getPositionY(300));
			Assets.font.draw(batch, perfectBonus + "PTS!",
					Gdx.graphics.getWidth() / 2
							- Assets.font.getBounds("" + perfectBonus + "PTS!").width / 2,
					resolution.getPositionY(250));
		}
		else
		{
			perfectStreak = 0;
		}
		nextLevel.draw(batch);

		return selected();
	}

	public int getTotalBonus() {
		return timeBonus + moveBonus + oneMoveBonus + speedBonus + perfectBonus;
	}

	public void resetBonus() {
		score += getTotalBonus();
		timeBonus = 0;
		moveBonus = 0;
		oneMoveBonus = 0;
		speedBonus = 0;
		perfectBonus = 0;
	}

	private boolean selected() {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the y plane, switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!pressed && nextLevel.isClicked(touchPos)) {
			pressed = true;
		} else if (pressed && !Gdx.input.isTouched()) {
			isTrue = true;
			pressed = false;
		}
		return isTrue;
	}

	public void dispose() {
		/*Assets.font.dispose();
		nextLevel.disposeTexture();*/
	}
}

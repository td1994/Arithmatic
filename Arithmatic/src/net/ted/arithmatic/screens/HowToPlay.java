package net.ted.arithmatic.screens;

import net.ted.arithmatic.GameState;
import net.ted.arithmatic.ScreenResolution;
import net.ted.arithmatic.game.Assets;
import net.ted.arithmatic.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * The How to Play section covers 5 pages which can be traversed linearly.
 */
public class HowToPlay {

	// Instance and Field Variables --------------------------------------------

	private boolean isPressed; // Is screen pressed
	private HowToPlaySelection buttonSelected; // Last selected button
	private ScreenResolution resolution; // TODO: Implement resolution.

	// Note: Previous should not appear on first screen, and Next not on last.
	private Button previous, next, returnToMenu;

	private int currentPage; // Track current page of instructions (5 pages)

	// Methods -----------------------------------------------------------------

	/**
	 * Constructor which draws what would appear on page 0 of the How to Play.
	 */
	public HowToPlay(ScreenResolution resolution, Texture button, int page) {

		this.resolution = resolution;

		currentPage = page;
		isPressed = false;

		// Previous button, if appearing, is on the left of the screen.
		previous = new Button("Previous", button, (Gdx.graphics.getWidth() / 2) - 
				(button.getWidth() * resolution.getScale()) - resolution.getPositionX(10),
				resolution.getPositionY(110), resolution.getScale());

		// Main menu button is in the middle of screen.
		returnToMenu = new Button("Main Menu", button,
				(Gdx.graphics.getWidth() - button.getWidth() * resolution.getScale()) / 2, resolution.getPositionY(170),
				resolution.getScale());

		// Next button, if appearing, is on the right of the screen.
		next = new Button("Next", button, Gdx.graphics.getWidth() / 2 + resolution.getPositionX(10),
				resolution.getPositionY(110), resolution.getScale());
	}

	public boolean selected(HowToPlaySelection select) {
		// If touched or clicked on
		Vector3 touchPos = new Vector3();

		// LibGDX flips the Y plane, switch it back here
		touchPos.set(Gdx.input.getX(),
				Gdx.graphics.getHeight() - Gdx.input.getY(), 0);

		boolean isTrue = false;
		if (!isPressed
				&& (select == HowToPlaySelection.PREVIOUS
						&& previous.isClicked(touchPos) && currentPage > 0)
				|| (select == HowToPlaySelection.NEXT
						&& next.isClicked(touchPos) && currentPage < 6)
				|| (select == HowToPlaySelection.RETURN_TO_MENU && returnToMenu
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

	/**
	 * Handles the drawing of appropriate buttons, manages behavior for the
	 * return to menu button, and makes calls to instruction loading methods.
	 */
	public GameState render(MainMenu menu, SpriteBatch batch) {
		GameState state = GameState.HOW_TO_PLAY;
		// Drawing out the buttons, which depends on the current page number
		batch.draw(Assets.pages[currentPage], 0,
				Gdx.graphics.getHeight() - Assets.pages[currentPage].getHeight() * resolution.getScale(),
				Assets.pages[currentPage].getWidth() * resolution.getScale(),
				Assets.pages[currentPage].getHeight() * resolution.getScale());
		returnToMenu.draw(batch);
		if (currentPage < 6) { // Next Button
			next.draw(batch);
		}
		if (currentPage > 0) { // Previous Button
			previous.draw(batch);
		}

		// If main menu is selected, exit How To Play and return to menu.
		if (selected(HowToPlaySelection.RETURN_TO_MENU)) {
			currentPage = 0; // Return settings to defaults
			state = GameState.MAIN_MENU;
		}
		// If previous button is clicked, go to previous page.
		else if (selected(HowToPlaySelection.PREVIOUS)) {
			currentPage--;
		}

		// If next button is clicked, go to next page.
		else if (selected(HowToPlaySelection.NEXT)) {
			currentPage++;
		}

		newPageLoaded(); // Load the instructions that go with the page number

		return state;
	}

	/**
	 * Called either once How to Play is first started from the main menu, or
	 * each time that the Next or Previous buttons are pressed.
	 * 
	 * This loads the appropriate instructions associated with the current page.
	 */
	public void newPageLoaded() {
		if (currentPage == 0) {
			// TODO: Replace loaded image with instructions page 0
		} else if (currentPage == 1) {
			// TODO: Replace loaded image with instructions page 1
		} else if (currentPage == 2) {
			// TODO: Replace loaded image with instructions page 2
		} else if (currentPage == 3) {
			// TODO: Replace loaded image with instructions page 3
		} else if (currentPage == 4) {
			// TODO: Replace loaded image with instructions page 4
		}
	}

}

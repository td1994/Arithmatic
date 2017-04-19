package net.ted.arithmatic.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Contains methods checking boundaries of selected and pressed spots.
 * 
 * Note: Three variables in Board are static to avoid having to initialize a
 * second board for class reference.
 */
public class Tile {

	// Instance and Field Variables --------------------------------------------

	private int value; // value that tile represents
	private float locX, locY, scale; // location of the tile and its scale
	private Texture stdSprite, selSprite; // standard and selected tile sprites
	private boolean isSelected; // value that checks if tile is selected

	// Methods -----------------------------------------------------------------

	/**
	 * Constructor: Sets the value, textures, placement, and size of tile.
	 */
	public Tile(int value, Texture stdTexture, Texture selTexture, float locX,
			float locY, float scale) {

		this.value = value;
		this.locX = locX;
		this.locY = locY;
		this.scale = scale;

		stdSprite = stdTexture;
		selSprite = selTexture;

		isSelected = false; // tile starts off unselected
		// System.out.println(this.locX + "," + this.locY + " " + this.scale);
	}

	/**
	 * Returns whether the player is touching a tile.
	 * 
	 * @param touchPos
	 *            Vector coordinates containing x and y positions.
	 * @param isMakingPlay
	 *            True if making a move, false if not.
	 * @return True if within non-selected tile, false if not.
	 */
	public boolean isTileSelected(Vector3 touchPos, boolean isMakingPlay) {
		if (isMakingPlay) {
			return touchPos.x >= locX + (stdSprite.getWidth() * scale * 0.2)
					&& touchPos.x <= locX
							+ (stdSprite.getWidth() * scale * 0.8)
					&& touchPos.y >= locY
							+ (stdSprite.getHeight() * scale * 0.2)
					&& touchPos.y <= locY
							+ (stdSprite.getHeight() * scale * 0.8);
		}
		return touchPos.x >= locX
				&& touchPos.x <= locX + (stdSprite.getWidth() * scale)
				&& touchPos.y >= locY
				&& touchPos.y <= locY + (stdSprite.getHeight() * scale);
	}

	/**
	 * Returns whether a tile is next to the last selected tile.
	 * 
	 * @param curRow
	 *            Current row of player touch.
	 * @param curCol
	 *            Current column of player touch.
	 * @param row
	 *            Board row.
	 * @param col
	 *            Board column.
	 * @return True if tile is next, false if not.
	 */
	public boolean isNextToLast(int curRow, int curCol, int row, int col) {
		return (curRow - row <= 1) && (curRow - row >= -1)
				&& (curCol - col <= 1) && (curCol - col >= -1);
	}

	/**
	 * Returns the value of the tile
	 * 
	 * @return The tile's value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Tells whether the tile is selected or not.
	 * 
	 * @return True if selected, false if not.
	 */
	public boolean getSelected() {
		return isSelected;
	}

	/**
	 * Displays the appropriate tile.
	 */
	public void displayTile(SpriteBatch batch) {
		if (isSelected) {
			batch.draw(selSprite, locX, locY, selSprite.getWidth() * scale,
					selSprite.getHeight() * scale);
		} else {
			batch.draw(stdSprite, locX, locY, stdSprite.getWidth() * scale,
					stdSprite.getHeight() * scale);
		}
	}

	public void setNewTile(int value, Texture stdTexture, Texture selTexture) {
		this.value = value;
		stdSprite = stdTexture;
		selSprite = selTexture;
		isSelected = false;
	}

	public void setSelected() {
		isSelected = !isSelected;
	}

	public void dispose() {
		stdSprite.dispose();
		selSprite.dispose();
	}
}

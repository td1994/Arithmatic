package net.ted.arithmatic.game;

// Copyright 2013 T.E.D.
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

import net.ted.arithmatic.Arithmatic;
import net.ted.arithmatic.ScreenResolution;

/**
 * This class manages the data and logic for the game board, changing them when
 * necessary. It is the "model" class for the number crunch game.
 */
public class Board {

	// Instance and Field Variables --------------------------------------------

	public final int BOARD_SIZE = 7; // Board size for the game (width/height)

	// Pixels between the left of the screen, and between the bottom edge
	public final static int LEFT_OFFSET = 30;
	public final static int BOTTOM_OFFSET = 260;

	// TODO: Adjust based on resolution using getWidth and getHeight
	public final static int TILE_SIZE = 55;

	private Tile[][] board; // Game board
	private Random number; // Randomly selects numbers

	private int[] equation; // Equation created by the player
	private int counter; // Counter of the equation array

	private boolean makingMove; // Player made valid move before check logic

	// Current and previous rows
	private int curRow, curCol; // Previous row/column selected by player
	private int[] prevRow, prevCol; // Row/column connects to selected tile

	// Counter variables
	private int prevCount, tileCount;

	private ScreenResolution resolution;
	
	public boolean divideByZero = false;
	
	public int sevenCount;

	// Methods -----------------------------------------------------------------

	/**
	 * This constructor sets the board and creates tiles.
	 * 
	 * @param tiles
	 *            an array of texture files representing the numbers
	 * @param tilesSelected
	 *            " that are currently selected
	 */
	public Board(ScreenResolution res) {
		resolution = res;

		board = new Tile[BOARD_SIZE][BOARD_SIZE]; // Creates empty board

		// Equation array holds as many characters as there are board spaces.
		equation = new int[(int) Math.pow(BOARD_SIZE, 2)];

		// Random numbers
		number = new Random(); // Sets random

		makingMove = false; // Player initially making any moves

		curRow = 0;
		curCol = 0;

		prevRow = new int[BOARD_SIZE * BOARD_SIZE - 1];
		prevCol = new int[BOARD_SIZE * BOARD_SIZE - 1];

		prevCount = 0;
		tileCount = 0;
		
		divideByZero = false;
		
		sevenCount = 0;
	}

	/**
	 * Creates a new board with alternating numbers and operators.
	 * 
	 * @param level
	 *            The level of the game to start at.
	 */
	public void setBoard(int level, Texture[] tiles, Texture[] tilesSelected) {
		int value;

		// Goes through all rows in the array
		for (int i = 0; i < BOARD_SIZE; i++) {

			// Goes through all columns in the array
			for (int j = 0; j < BOARD_SIZE; j++) {

				// If even spaced board, number will be selected
				if ((i + j) % 2 == 0) {
					value = number.nextInt(10);
					board[i][j] = new Tile(value, tiles[value],
							tilesSelected[value],
							resolution.getPositionX(LEFT_OFFSET + (60 * i)),
							resolution.getPositionY(BOTTOM_OFFSET + (60 * j)),
							resolution.getScale());
				}
				// Else a sign is selected
				else {
					// Level 1-5: Add, subtract
					// Level 6-10: Add, subtract, multiply, divide
					// Level 11-15: Add, subtract, multiply, divide, power
					if (level <= 5) {
						value = number.nextInt(2) + 10;
					} else if (level <= 10) {
						value = number.nextInt(4) + 10;
					} else {
						value = number.nextInt(5) + 10;
					}
					board[i][j] = new Tile(value, tiles[value],
							tilesSelected[value],
							resolution.getPositionX(LEFT_OFFSET + (60 * i)),
							resolution.getPositionY(BOTTOM_OFFSET + (60 * j)),
							resolution.getScale());
				}
			}
		}
	}

	/**
	 * Adds new panels when tiles are removed.
	 * 
	 * @param level
	 *            The new level of the board.
	 */
	public void implementNewPanels(int level, Texture[] tiles, Texture[] tilesSelected) {
		int value;
		// Goes through all rows in the board
		for (int i = 0; i < BOARD_SIZE; i++) {

			// Goes through all columns in the board
			for (int j = 0; j < BOARD_SIZE; j++) {

				// If the space is empty
				if (board[i][j].getSelected()) {
					if ((i + j) % 2 == 0) {
						value = number.nextInt(10);
						board[i][j].setNewTile(value, tiles[value],
								tilesSelected[value]);
					}

					// Else a sign is selected
					else {
						// Only add and subtract for level 5 or less
						if (level <= 5) {
							value = number.nextInt(2) + 10;
						} else if (level <= 10) {
							value = number.nextInt(4) + 10;
						} else {
							value = number.nextInt(5) + 10;
						}
						board[i][j].setNewTile(value, tiles[value],
								tilesSelected[value]);
					}
				}
			}
		}
	}

	/**
	 * Gets values from 2D array to display the board.
	 * 
	 * @param batch
	 * @param selected
	 *            TODO: Get rid of magic numbers
	 */
	public void displayBoard(SpriteBatch batch) {
		// Goes through all rows in board
		for (int i = 0; i < BOARD_SIZE; i++) {
			// Goes through all columns in board
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j].displayTile(batch);
			}
		}
	}

	/**
	 * Calculates the equation that the player inputs and checks if it's equal
	 * to the required answer (achieve).
	 * 
	 * @return True if the input is equal to the target number, false if not
	 */
	public boolean validMove() {

		// Goes through equation to retrieve all items and organize them into
		// signs and numbers.
		for (int i = 0; i < counter; i++) {

			// If there is a sign at the beginning or end of the equation, then
			// the equation is wrong
			if ((i == 0 && equation[i] > 9)
					|| (i == counter - 1 && equation[i] > 9)) {
				// Log.d(TAG, "ERROR: Sign at end");
				return false;
			}

			// If there are two consecutive signs, then the equation is wrong
			else if (equation[i] > 9 && equation[i + 1] > 9) {
				// Log.d(TAG, "ERROR: Two consecutive signs");
				return false;
			}

			else if (equation[i] == 13 && equation[i + 1] == 0) {
				divideByZero = true;
				return false;
			}

			else if (equation[i] == 14 && equation[i - 1] == 0
					&& equation[i + 1] == 0) {
				return false;
			}
		}
		return true;
	}

	public double calculate(Arithmatic game) {
		double[] numbers = new double[25]; // Numbers in equation (25 possible)
		int[] signs = new int[24]; // The signs in the equation (24 possible)

		int numberCounter = 0; // Tracks the number of items in numbers
		int signCounter = 0; // Tracks the number of items in signs

		boolean numStreak = false; // Checks if last character was a number
		
		//achievements
		int threeCount = 0, sixCount = 0;
		boolean[] numbersUsed = new boolean[10];
		boolean[] opsUsed = new boolean[5];
		for(int i = 0; i < numbersUsed.length; i++)
		{
			if(i < 5)
			{
				opsUsed[i] = false;
			}
			numbersUsed[i] = false;
		}
		

		for (int i = 0; i < counter; i++) {
			// If the value is a sign, then it's placed in the signs array
			if (equation[i] > 9) {
				if(game.actionResolver.getSignedInGPGS())
				{
					if(equation[i] == 12)
					{
						if(equation[i + 1] == 2)
						{
							if(i + 2 == equation.length || (i + 2 < equation.length 
									&& equation[i + 2] > 9))
							{
								game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQGA");
							}
						}
						else if(equation[i + 1] == 4)
						{
							if(i + 2 == equation.length || (i + 2 < equation.length 
									&& equation[i + 2] > 9))
							{
								game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQGg");
							}
						}
						else if(equation[i + 1] == 5)
						{
							if(i + 2 == equation.length || (i + 2 < equation.length 
									&& equation[i + 2] > 9))
							{
								game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQGw");
							}
						}
					}
					else if(equation[i] == 13)
					{
						if(equation[i + 1] == 9)
						{
							if(i + 2 == equation.length || (i + 2 < equation.length 
									&& equation[i + 2] > 9))
							{
								game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQHw");
							}
						}
					}
				}
				// Value organized in signs array
				signs[signCounter] = equation[i];
				// Log.d(TAG, "Sign added: " + signs[signCounter]);
				numStreak = false; // Number streak ends
				signCounter++;
			}

			// Number: the number is organized in the numbers array
			else {
				//if there's a zero in the equation, add it to the achievement counter
				if(game.actionResolver.getSignedInGPGS())
				{
					if(equation[i] == 0)
					{
						game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQFg");
					}
					//count the number of threes, if there are three "3" tiles, achievement unlocked!
					else if(equation[i] == 3)
					{
						threeCount++;
						if(threeCount == 3)
						{
							game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQGQ");
						}
					}
					//count the number of sixes in formula
					else if(equation[i] == 6)
					{
						sixCount++;
					}
					
					//keep track of the numbers used
					if(!numbersUsed[equation[i]])
					{
						numbersUsed[equation[i]] = true;
					}
				}
				// If there is a number streak, then the number is added to
				// the number is added to the recently updated spot.
				if (numStreak) {

					// Previous value multiplied by 10 to combine numbers
					numbers[numberCounter - 1] = (numbers[numberCounter - 1] * 10)
							+ equation[i];
					// Log.d(TAG, "Number added: " + numbers[numberCounter]);
				}

				// Else, the number is added to open spot
				else {
					// Value organized in numbers array
					numbers[numberCounter] = equation[i];
					// Log.d(TAG, "Number added: " + numbers[numberCounter]);
					numStreak = true; // Number streak begins
					numberCounter++;
				}

			}
		}
		
		//if all numbers besides one were used, achievement unlocked!
		if(game.actionResolver.getSignedInGPGS())
		{
			if(!numbersUsed[1])
			{
				for(int i = 0; i < numbersUsed.length; i++)
				{
					if(i != 1 && !numbersUsed[i])
					{
						break;
					}
					if(i == numbersUsed.length - 1)
					{
						game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQFw");
					}
				}
			}
		}

		// This follows PEMDAS, which is why there are multiple for loops.

		// Goes through all the signs, checking if there are any exponents
		for (int i = 0; i < signCounter; i++) {
			// Log.d(TAG, "Space in Array: " + i);
			// Log(TAG, "Sign: " + signs[i]);

			// If an exponent is found, calculate it
			if (signs[i] == 14) {
				opsUsed[4] = true;
				// The power function is calculated
				numbers[i] = (int) Math.pow(numbers[i], numbers[i + 1]);

				// Log(TAG, "Power Value: " + numbers[i]);

				// Shifts all later values up the array
				for (int a = i + 1; a < numberCounter; a++) {
					if (a == numbers.length - 1) {
						numbers[a] = 0;
					} else {
						numbers[a] = numbers[a + 1];
					}
				}

				numberCounter--; // Amount of numbers decrease

				// Shifts all later values up the array
				for (int b = i; b < signCounter; b++) {
					if (b == signs.length - 1) {
						signs[b] = 0;
					} else {
						signs[b] = signs[b + 1];
					}
				}

				signCounter--; // Amount of signs decreases

				// For loop counter decreases (to return to original spot to see
				// if there is another sign of the same type)
				i--;
			}
		}

		// Checks equation again to find multiplication and division signs
		for (int i = 0; i < signCounter; i++) {
			// Log(TAG, "Space in Array: " + i);
			// Log(TAG, "Sign: " + signs[i]);

			// If sign is multiplication
			if (signs[i] == 12) {
				opsUsed[2] = true;
				
				// Calculates multiplication of two values
				numbers[i] = numbers[i] * numbers[i + 1];

				// Log(TAG, "Multiply Value: " + numbers[i]);

				// Shifts all later values up the array
				for (int a = i + 1; a < numberCounter; a++) {
					if (a == numbers.length - 1) {
						numbers[a] = 0;
					} else {
						numbers[a] = numbers[a + 1];
					}
				}

				numberCounter--; // Amount of numbers decrease

				// Shifts all later values up the array
				for (int b = i; b < signCounter; b++) {
					if (b == signs.length - 1) {
						signs[b] = 0;
					} else {
						signs[b] = signs[b + 1];
					}
				}

				signCounter--; // Amount of signs decreases

				// For loop counter decreases (to return to original spot to see
				// if there is another sign of the same type)
				i--;
			}

			// If sign is division
			else if (signs[i] == 13) {
				opsUsed[3] = true;
				// Calculate division of two numbers
				numbers[i] = numbers[i] / numbers[i + 1];

				// Log(TAG, "Divide Value: " + numbers[i]);

				// Shifts all later values up the array
				for (int a = i + 1; a < numberCounter; a++) {
					if (a == numbers.length - 1) {
						numbers[a] = 0;
					} else {
						numbers[a] = numbers[a + 1];
					}
				}

				numberCounter--; // Amount of numbers decrease

				// Shifts all later values up the array
				for (int b = i; b < signCounter; b++) {
					if (b == signs.length - 1) {
						signs[b] = 0;
					} else {
						signs[b] = signs[b + 1];
					}
				}

				signCounter--; // Amount of signs decrease

				// For loop counter decreases (to return to original spot to see
				// if there is another sign of the same type)
				i--;
			}
		}

		// Goes through equation third time, find addition and subtraction signs
		for (int i = 0; i < signCounter; i++) {
			// Log(TAG, "Space in Array: " + i);
			// Log(TAG, "Sign: " + signs[i]);

			// If addition sign is found, add two values
			if (signs[i] == 10) {
				opsUsed[0] = true;
				numbers[i] = numbers[i] + numbers[i + 1];
				// Log(TAG, "Add Value: " + numbers[i]);

				// Shifts all later values up the array
				for (int a = i + 1; a < numberCounter; a++) {
					if (a == numbers.length - 1) {
						numbers[a] = 0;
					} else {
						numbers[a] = numbers[a + 1];
					}
				}

				numberCounter--; // Amount of numbers decrease

				// Shifts all later values up the array
				for (int b = i; b < signCounter; b++) {
					if (b == signs.length - 1) {
						signs[b] = 0;
					} else {
						signs[b] = signs[b + 1];
					}
				}

				signCounter--; // Amount of signs decrease

				// For loop counter decreases (to return to original spot to see
				// if there is another sign of the same type)
				i--;
			}

			// If subtraction sign is found, subtract the two values
			else if (signs[i] == 11) {
				opsUsed[1] = true;
				// Two values are subtracted
				numbers[i] = numbers[i] - numbers[i + 1];
				// Log(TAG, "Subtract Value: " + numbers[i]);

				// Shifts all later values up the array
				for (int a = i + 1; a < numberCounter; a++) {
					if (a == numbers.length - 1) {
						numbers[a] = 0;
					} else {
						numbers[a] = numbers[a + 1];
					}
				}

				numberCounter--; // Amount of numbers decrease

				// Shifts all later values up the array
				for (int b = i; b < signCounter; b++) {
					if (b == signs.length - 1) {
						signs[b] = 0;
					} else {
						signs[b] = signs[b + 1];
					}
				}

				signCounter--; // Amount of signs decrease

				// For loop counter decreases (to return to original spot to see
				// if there is another sign of the same type)
				i--;
			}
		}
		
		//more achievements
		if(game.actionResolver.getSignedInGPGS())
		{
			//gets the number of sixes in the board
			int actualSixCount = 0;
			for(int i = 0; i < BOARD_SIZE; i++)
			{
				for(int j = 0; j < BOARD_SIZE; j++)
				{
					if(board[i][j].getValue() == 6)
					{
						actualSixCount++;
					}
				}
			}
			
			//if equal, achievement unlocked!
			if(sixCount == actualSixCount)
			{
				game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQHA");
			}
			
			//if the user creates seven equations that add to 7, achievement unlocked
			if(numbers[0] == 7)
			{
				sevenCount++;
				if(sevenCount == 7)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQHQ");
				}
			}
			else if(numbers[0] == 8)
			{
				game.actionResolver.incrementAchievementGPGS("CgkIsda-i-YcEAIQHg");
			}
			
			//if the user used all numeric operators, achievement unlocked
			for(int i = 0; i < opsUsed.length; i++)
			{
				if(!opsUsed[i])
				{
					break;
				}
				if(i == opsUsed.length - 1)
				{
					game.actionResolver.unlockAchievementGPGS("CgkIsda-i-YcEAIQIw");
				}
			}
		}
		return numbers[0];
	}

	/**
	 * Retrieves the value of the selected tile from the board.
	 */
	public void selectTile(int a, int b) {
		board[a][b].setSelected();
		equation[counter] = board[a][b].getValue(); // Gets value from board

		// Log.d(TAG, equation[counter] + " added");

		counter++; // Number of values in equation increases
	}

	/**
	 * Removes value from the equation.
	 */
	public void removeTile(int a, int b) {
		board[a][b].setSelected();
		counter--; // Number of values in equation decreases
		equation[counter] = 0; // Removes value from board

		// Log.d(TAG, equation[counter] + " added");
	}

	/**
	 * Empties all values in equation array.
	 */
	public void resetEquation() {
		for (int i = 0; i < prevCount; i++) {
			prevRow[i] = 0;
			prevCol[i] = 0;
		}

		tileCount = 0;
		prevCount = 0;

		curRow = 0;
		curCol = 0;

		makingMove = false;

		// Resets all values in array
		for (int i = 0; i < counter; i++) {
			equation[i] = 0;
		}

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j].getSelected()) {
					board[i][j].setSelected();
				}
			}
		}

		counter = 0; // Resets counter

		// Log.d(TAG, "Board Reset");
	}

	/**
	 * Gets and returns the equation array.
	 * 
	 * @return The equation array.
	 */
	public int[] getEquation() {
		return equation;
	}

	/**
	 * Returns whether touch position is within the vicinity of the game board.
	 * 
	 * @param touchPos
	 *            Vector coordinates containing x and y positions.
	 * @return True if within the board, false if outside of it.
	 */
	public boolean isWithinBoard(Vector3 touchPos) {
		return (touchPos.x >= resolution.getPositionX(30))
				&& (touchPos.x <= resolution.getPositionX(450))
				&& (touchPos.y >= resolution.getPositionY(BOTTOM_OFFSET))
				&& (touchPos.y <= resolution.getPositionY(680));
	}

	public void makeSelection(Vector3 touchPos) {
		// Goes through every row on the board
		for (int i = 0; i < BOARD_SIZE; i++) {

			// Goes through every column on the board
			for (int j = 0; j < BOARD_SIZE; j++) {

				// Player is touching a tile not already selected
				if (board[i][j].isTileSelected(touchPos, makingMove)) {

					if (!board[i][j].getSelected()) {

						// Player in middle of making move, touching adjacent
						// tile
						if (makingMove
								&& board[i][j].isNextToLast(curRow, curCol, i,
										j)) {

							// Log.d(TAG, "Making Move: " + makingMove);

							selectTile(i, j); // Board set tile to selected mode

							prevRow[prevCount] = curRow;
							prevCol[prevCount] = curCol;

							// Update coordinates of tile
							curRow = i;
							curCol = j;

							prevCount++;
							tileCount++;
						}

						// Else if it's their first tile, select it
						else if (!makingMove) {
							// Log.d(TAG, "Making Move: " + makingMove);

							// Board set tile to selected mode
							selectTile(i, j);
							makingMove = true;

							curRow = i;
							curCol = j;
							tileCount++;
						}

					} else if (tileCount > 1 && i == prevRow[prevCount - 1]
							&& j == prevCol[prevCount - 1]) {

						removeTile(curRow, curCol);
						tileCount--;

						curRow = prevRow[prevCount - 1];
						curCol = prevCol[prevCount - 1];

						prevRow[prevCount - 1] = 0;
						prevCol[prevCount - 1] = 0;

						prevCount--;
					}
				}
			}
		}
	}

	public int getTileCount() {
		return tileCount;
	}

	public int getScore() {
		int score = 0;
		boolean isNumber = false;
		int digitLength = 0;
		for (int i = 0; i < tileCount; i++) {
			if (equation[i] >= 0 && equation[i] <= 9) {
				if (isNumber) {
					score += 5 * digitLength;
				} else {
					isNumber = true;
					score += 5;
				}
				digitLength++;
			} else {
				isNumber = false;
				digitLength = 0;
				if (equation[i] == 10 || equation[i] == 11) {
					score += 10;
				} else if (equation[i] == 12 || equation[i] == 13) {
					score += 25;
				} else if (equation[i] == 14) {
					score += 50;
				}
			}
		}

		return score;
	}

	public void dispose() {
		/*for (int i = 0; i < tiles.length; i++) {
			tiles[i].dispose();
			tilesSelected[i].dispose();
		}
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j].dispose();
			}
		}*/
	}
}

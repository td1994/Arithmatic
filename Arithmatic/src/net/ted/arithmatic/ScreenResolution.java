package net.ted.arithmatic;

import com.badlogic.gdx.Gdx;

/**
 * This class contains information for screen resolutions, so as to allow for
 * larger compatibility with more diverse device sizes.
 */
public class ScreenResolution {

	// Instance and Field Variables --------------------------------------------

	private static final double sixteenXnine = 16 / 9;
	private static final double sixteenXten = 16 / 10;
	private static final double fourXthree = 4 / 3;
	private double width, height;

	// Methods -----------------------------------------------------------------

	public ScreenResolution() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		// System.out.print(width + "x" + height);
	}

	public float getPositionX(float x) {
		return (float) Math.round((x / 480) * width);
	}

	public float getPositionY(float y) {
		return (float) Math.round((y / 854) * height);
	}

	public float getScale() {
		if (imagesToUse().equals("low")) {
			return (float) (width / 480);
		} else if (imagesToUse().equals("med")) {
			return (float) (width / 720);
		} else if (imagesToUse().equals("high")) {
			return (float) (width / 1080);
		}
		return (float) (width / 2160);
	}

	public String imagesToUse() {
		if (width <= 480) {
			return "low";
		} else if (width <= 720) {
			return "med";
		} else if (width <= 1080) {
			return "high";
		}
		return "xhigh";
	}
}

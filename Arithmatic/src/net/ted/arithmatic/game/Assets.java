package net.ted.arithmatic.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.ted.arithmatic.ScreenResolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Assets {
	
	public static BitmapFont font;
	public static Texture buttonMD;
	public static Texture buttonF;
	public static Texture buttonP;
	public static Texture[] tilesMD;
	public static Texture[] tilesF;
	public static Texture[] tilesSelectedMD;
	public static Texture[] tilesSelectedF;
	public static Background backgroundF;
	public static Background backgroundMD;
	public static Background backgroundP;
	public static Background lightingF;
	public static Background lightingMD;
	public static Background lightingP;
	public static Sprite bar;
	public static SpriteBatch batch;
	public static Texture[] pages;
	public static String scorePath;
	
	public static int [] scores;
	
	public static void LoadAssets(ScreenResolution resolution) throws NumberFormatException, IOException
	{
		//load font
		font = new BitmapFont(Gdx.files.internal("data/Argos.fnt"),
				Gdx.files.internal("data/Argos_0.png"), false);
		
		tilesMD = new Texture[15];
		tilesF = new Texture[15];
		tilesSelectedMD = new Texture[15];
		tilesSelectedF = new Texture[15];
		
		//load MageDesk tiles
		tilesMD[0] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/zero.png"));
		tilesMD[1] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/one.png"));
		tilesMD[2] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/two.png"));
		tilesMD[3] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/three.png"));
		tilesMD[4] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/four.png"));
		tilesMD[5] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/five.png"));
		tilesMD[6] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/six.png"));
		tilesMD[7] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/seven.png"));
		tilesMD[8] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/eight.png"));
		tilesMD[9] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/nine.png"));
		tilesMD[10] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/add.png"));
		tilesMD[11] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/subtract.png"));
		tilesMD[12] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/multiply.png"));
		tilesMD[13] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/divide.png"));
		tilesMD[14] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk/power.png"));
		
		//load forest tiles
		tilesF[0] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/zero.png"));
		tilesF[1] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/one.png"));
		tilesF[2] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/two.png"));
		tilesF[3] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/three.png"));
		tilesF[4] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/four.png"));
		tilesF[5] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/five.png"));
		tilesF[6] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/six.png"));
		tilesF[7] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/seven.png"));
		tilesF[8] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/eight.png"));
		tilesF[9] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/nine.png"));
		tilesF[10] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/add.png"));
		tilesF[11] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/subtract.png"));
		tilesF[12] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/multiply.png"));
		tilesF[13] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/divide.png"));
		tilesF[14] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest/power.png"));
		
		//load selected MageDesk tiles
		tilesSelectedMD[0] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/zeroselected.png"));
		tilesSelectedMD[1] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/oneselected.png"));
		tilesSelectedMD[2] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/twoselected.png"));
		tilesSelectedMD[3] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/threeselected.png"));
		tilesSelectedMD[4] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/fourselected.png"));
		tilesSelectedMD[5] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/fiveselected.png"));
		tilesSelectedMD[6] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/sixselected.png"));
		tilesSelectedMD[7] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/sevenselected.png"));
		tilesSelectedMD[8] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/eightselected.png"));
		tilesSelectedMD[9] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/nineselected.png"));
		tilesSelectedMD[10] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/addselected.png"));
		tilesSelectedMD[11] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/subtractselected.png"));
		tilesSelectedMD[12] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/multiplyselected.png"));
		tilesSelectedMD[13] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/divideselected.png"));
		tilesSelectedMD[14] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "MageDesk"
				+ "/powerselected.png"));
		
		//load selected Forest tiles
		tilesSelectedF[0] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/zeroselected.png"));
		tilesSelectedF[1] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/oneselected.png"));
		tilesSelectedF[2] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/twoselected.png"));
		tilesSelectedF[3] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/threeselected.png"));
		tilesSelectedF[4] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/fourselected.png"));
		tilesSelectedF[5] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/fiveselected.png"));
		tilesSelectedF[6] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/sixselected.png"));
		tilesSelectedF[7] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/sevenselected.png"));
		tilesSelectedF[8] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/eightselected.png"));
		tilesSelectedF[9] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/nineselected.png"));
		tilesSelectedF[10] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/addselected.png"));
		tilesSelectedF[11] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/subtractselected.png"));
		tilesSelectedF[12] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/multiplyselected.png"));
		tilesSelectedF[13] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/divideselected.png"));
		tilesSelectedF[14] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/" + "Forest"
				+ "/powerselected.png"));
		
		//load timer bar
		bar = new Sprite(new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/bar.png")));
		
		//load buttons
		buttonF = new Texture(Gdx.files.internal("data/" + resolution.imagesToUse() + "/Forest/Button_Forest.png"));
		buttonMD = new Texture(Gdx.files.internal("data/" + resolution.imagesToUse() + "/MageDesk/Button_Desk.png"));
		buttonP = new Texture(Gdx.files.internal("data/" + resolution.imagesToUse() + "/Prison/Button_Prison.png"));
		
		//load backgrounds
		backgroundP = new Background(new Texture(Gdx.files.internal("data/" + resolution.imagesToUse()
				+ "/Prison/PrisonBG.png")), resolution.getScale());
		lightingP = new Background(new Texture(Gdx.files.internal("data/" + resolution.imagesToUse()
				+ "/Prison/PrisonLighting.png")), resolution.getScale());
		backgroundMD = new Background(new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/MageDesk/MagedeskBG.png")),
				resolution.getScale());
		lightingMD = new Background(
				new Texture(Gdx.files.internal("data/"
						+ resolution.imagesToUse()
						+ "/MageDesk/MagedeskLighting.png")),
				resolution.getScale());
		backgroundF = new Background(new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/Forest/ForestBG.png")),
				resolution.getScale());
		lightingF = new Background(new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/Forest/ForestLighting.png")),
				resolution.getScale());
		
		pages = new Texture[7];
		for(int i = 1; i <= pages.length; i++)
		{
			pages[i - 1] = new Texture(Gdx.files.internal("data/"
				+ resolution.imagesToUse() + "/Page_" + i + ".png"));
		}
		
		scores = new int[8];
		
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader
					(Gdx.files.external("data/scores.txt").read()));
			for(int i = 0; i < scores.length; i++)
			{
				scores[i] = Integer.parseInt(in.readLine());
			}
		} catch (Throwable e) {
			// :( It's ok we have defaults
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
			}
		}
		
		batch = new SpriteBatch();
	}
}

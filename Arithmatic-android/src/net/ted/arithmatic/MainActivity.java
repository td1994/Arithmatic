package net.ted.arithmatic;

import net.ted.arithmatic.Arithmatic;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class MainActivity extends AndroidApplication implements GameHelperListener, ActionResolver {
	
	//change for later
	private static final String AD_UNIT_ID = "ca-app-pub-1390802893439736/1630178206";
	protected AdView adView;
	protected View gameView;
	private GameHelper gameHelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        FrameLayout fLayout = new FrameLayout(this);
        
        View gameView = createGameView(cfg);
        fLayout.addView(gameView);
        
        AdView admobView = createAdView();
        fLayout.addView(admobView);
        
        setContentView(fLayout);
        startAdvertising(admobView);
        
        setupGPGS();
    }
    
    private AdView createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        //adView.setId(8675309); // this is an arbitrary id, allows for relative positioning in createGameView()
        FrameLayout.LayoutParams adParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        adParams.gravity = Gravity.BOTTOM;
        adView.setLayoutParams(adParams);
        //adView.setBackgroundColor(Color.BLACK);
        return adView;
    }
    
    private View createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new Arithmatic(this), cfg);
        return gameView;
      }
    
    private void startAdvertising(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
      }
    
    @Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}
    
    @Override
    public void onResume() {
      super.onResume();
      if (adView != null) adView.resume();
    }

    @Override
    public void onPause() {
      if (adView != null) adView.pause();
      super.onPause();
    }

    @Override
    public void onDestroy() {
      if (adView != null) adView.destroy();
      super.onDestroy();
    }
    
    @Override
	public void onStop(){
		super.onStop();
		if (adView != null) adView.pause();
		gameHelper.onStop();
	}
    
    @Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(boolean mode, TimeMode time, int score) {
		if(mode) //Arcade Mode
		{
			switch(time)
			{
				case STANDARD:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAQ", score);
					break;
				case MIN5:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAg", score);
					break;
				case MIN10:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAw", score);
					break;
				case MIN15:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBA", score);
					break;
			}
		}
		else //Survival Mode
		{
			switch(time)
			{
				case STANDARD:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBQ", score);
					break;
				case MIN5:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBg", score);
					break;
				case MIN10:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBw", score);
					break;
				case MIN15:
					Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQCA", score);
					break;
			}
		}
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS(boolean mode, TimeMode time) {
		if (gameHelper.isSignedIn()) {
			if(mode) //Arcade Mode
			{
				switch(time)
				{
					case STANDARD:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAQ"), 100);
						break;
					case MIN5:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAg"), 100);
						break;
					case MIN10:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQAw"), 100);
						break;
					case MIN15:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBA"), 100);
						break;
				}
			}
			else //Survival Mode
			{
				switch(time)
				{
					case STANDARD:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBQ"), 100);
						break;
					case MIN5:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBg"), 100);
						break;
					case MIN10:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQBw"), 100);
						break;
					case MIN15:
						startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIsda-i-YcEAIQCA"), 100);
						break;
				}
			}
		}
		else if (!gameHelper.isConnecting()) {
		    loginGPGS();
		}
	}

	@Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
		    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		}
		else if (!gameHelper.isConnecting()) {
		    loginGPGS();
		}
	}

	@Override
	public void onSignInFailed() {
		
	}

	@Override
	public void onSignInSucceeded() {

	}

	@Override
	public void setupGPGS() {
        if(gameHelper == null)
        {
        	gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        gameHelper.setup(this);
	}

	@Override
	public void incrementAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		Games.Achievements.increment(gameHelper.getApiClient(), achievementId, 1);
	}
}
package net.ted.arithmatic;

public interface ActionResolver {
	public enum TimeMode
	{
		STANDARD, MIN5, MIN10, MIN15;
	}
	  public void setupGPGS();
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void submitScoreGPGS(boolean mode, TimeMode time, int score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS(boolean mode, TimeMode time);
	  public void getAchievementsGPGS();
	  public void incrementAchievementGPGS(String achievementId);
	}

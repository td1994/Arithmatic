package net.ted.arithmatic;

public class ActionResolverDesktop implements ActionResolver{
	boolean signedInStateGPGS = false;

	@Override
	public boolean getSignedInGPGS() {
		return signedInStateGPGS;
	}

	@Override
	public void loginGPGS() {
		System.out.println("loginGPGS");
		signedInStateGPGS = true;
	}

	@Override
	public void submitScoreGPGS(boolean mode, TimeMode time, int score) {
		System.out.println("submitScoreGPGS " + score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		System.out.println("unlockAchievement " + achievementId);
	}

	@Override
	public void getLeaderboardGPGS(boolean mode, TimeMode time) {
		System.out.println("getLeaderboardGPGS");
	}

	@Override
	public void getAchievementsGPGS() {
		System.out.println("getAchievementsGPGS");
	}

	@Override
	public void setupGPGS() {
		// TODO Auto-generated method stub
		System.out.println("Setup");
	}

	@Override
	public void incrementAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		System.out.println("Increment " + achievementId);
	}
}

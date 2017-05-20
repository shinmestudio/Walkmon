package baby.shinme.distancemeasurement.handler;

/**
 * Created by jichoul on 2017-05-14.
 */
public interface GPGSHandler {
    public void showAds(boolean show);
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(int achievementCase) throws Exception;
    public void submitScore(int highScore) throws Exception;
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
    public void reconnect();
}

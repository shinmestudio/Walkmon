package baby.shinme.distancemeasurement.handler;

/**
 * Created by jichoul on 2017-05-14.
 */
public interface GPGSHandler {
    public void showAds(boolean show);
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}

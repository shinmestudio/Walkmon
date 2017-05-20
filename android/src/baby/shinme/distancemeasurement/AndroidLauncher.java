package baby.shinme.distancemeasurement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import baby.shinme.distancemeasurement.handler.GPGSHandler;

public class AndroidLauncher extends AndroidApplication implements GPGSHandler {
	private static final String TAG = "AndroidLauncher";
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected AdView adView;
	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private static final String FIRST_PLAY_ACHIEVEMENT_CODE = "CgkIzafSgekTEAIQBA";
	private static final String CLEAR_TIME_ATTACK_MODE_ACHIEVEMENT_CODE = "CgkIzafSgekTEAIQAw";
	private static final String OPEN_ALL_CHARACTER_ACHIEVEMENT_CODE = "CgkIzafSgekTEAIQAQ";

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
				case SHOW_ADS :
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS :
					adView.setVisibility(View.INVISIBLE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);

		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new DistanceMeasurement(this), config);
		layout.addView(gameView);

		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visibility);
				Log.i(TAG, "Ad Loaded...");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-3209176700305221/6257845199");

		AdRequest.Builder builder = new AdRequest.Builder();
		//builder.addTestDevice("0C1A8E50CADA5A04BB7F6090F3B2199D");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);

		layout.addView(adView, adParams);
		adView.loadAd(builder.build());
		setContentView(layout);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e) {
			Log.e("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Log.e("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(int achievementCase) throws Exception {
		switch (achievementCase) {
			case 0 :
				break;
			case 1 :
				//first play this game.
				Games.Achievements.unlock(gameHelper.getApiClient(), FIRST_PLAY_ACHIEVEMENT_CODE);
				break;
			case 2 :
				// did not die time attack mode.
				Games.Achievements.unlock(gameHelper.getApiClient(), CLEAR_TIME_ATTACK_MODE_ACHIEVEMENT_CODE);
				break;
			case 3 :
				// unlock all character
				Games.Achievements.unlock(gameHelper.getApiClient(), OPEN_ALL_CHARACTER_ACHIEVEMENT_CODE);
				break;
		}
	}

	@Override
	public void submitScore(int highScore) throws Exception {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIzafSgekTEAIQAA", highScore);
		}
	}

	@Override
	public void showAchievement() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		} else {
			signIn();
		}
	}

	@Override
	public void showScore() {
		if (isSignedIn() == true) {
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkIzafSgekTEAIQAA"), requestCode);
		} else {
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void reconnect() {
		gameHelper.reconnectClient();
	}
}

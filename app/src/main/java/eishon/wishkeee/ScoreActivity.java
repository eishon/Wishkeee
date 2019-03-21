package eishon.wishkeee;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScoreActivity extends Activity {
	TextView scoreTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_score);

		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		scoreTextView.setText(readScore());

	}

	private String readScore() {
		String temp="";
		try{
			InputStream is = getAssets().open("score_data.txt");
			int size=is.available();
			byte[] buffer=new byte[size];
			is.read(buffer);
			is.close();
			temp=new String(buffer);
		}catch (IOException ex){
			ex.printStackTrace();
		}
		return temp;
	}
}

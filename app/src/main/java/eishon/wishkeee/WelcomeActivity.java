package eishon.wishkeee;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnTouchListener;

public class WelcomeActivity extends Activity {
	
	Button welcome_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.activity_welcome);
		
		welcome_btn=(Button) findViewById(R.id.welcome_btn);
		welcome_btn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					welcome_btn.getBackground().setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					welcome_btn.getBackground().setAlpha(255);
					Intent intent=new Intent(WelcomeActivity.this,MenuActivity.class);
					startActivity(intent);
					
					break;
				default:
					break;
				}
				return false;
			}
		});
		
	}
}

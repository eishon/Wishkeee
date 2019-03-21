package eishon.wishkeee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GameActivity extends Activity implements Runnable {
	
	GameSurfaceView gameSurfaceView;
	
	Button pauseButton,restartButton,exitButton,manButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_game);
		
		gameSurfaceView=(GameSurfaceView) findViewById(R.id.myGameView);
		
		final Button pauseButton=(Button) findViewById(R.id.pauseButton);
		final Button restartButton=(Button) findViewById(R.id.restartButton);
		final Button exitButton=(Button) findViewById(R.id.exitButton);
		final Button manButton=(Button) findViewById(R.id.btn_man);
		
		pauseButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(gameSurfaceView.drawingThread.pauseFlag==false){
						gameSurfaceView.drawingThread.manAnimationThread.stopThread();
						gameSurfaceView.drawingThread.backgroundAnimThread.stopThread();
						gameSurfaceView.drawingThread.obstacleThread.stopThread();
						gameSurfaceView.drawingThread.collisionDetectionThread.stopThread();
						gameSurfaceView.drawingThread.pauseFlag=true;
						pauseButton.setBackgroundResource(R.drawable.play_button);
						
					}else {
						gameSurfaceView.drawingThread.manAnimationThread=new ManAnimationThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.backgroundAnimThread=new BackgroundAnimThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.obstacleThread=new ObstacleThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.collisionDetectionThread=new CollisionDetectionThread(gameSurfaceView.drawingThread);
						gameSurfaceView.drawingThread.manAnimationThread.start();
						gameSurfaceView.drawingThread.backgroundAnimThread.start();
						gameSurfaceView.drawingThread.obstacleThread.start();
						gameSurfaceView.drawingThread.collisionDetectionThread.start();
						gameSurfaceView.drawingThread.pauseFlag=false;
						pauseButton.setBackgroundResource(R.drawable.pause_button);
						
					}
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		restartButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					restartGame();
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		exitButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					stopGame();
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		manButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					gameSurfaceView.drawingThread.man.centerY=(int) ((gameSurfaceView.drawingThread.displayY*0.951965)-gameSurfaceView.drawingThread.displayY/6);
					break;

				default:
					break;
				}
				
				return false;
			}
		});
		
		
	}
	
	
	private void restartGame() {
		gameSurfaceView.drawingThread.stopThread();
		gameSurfaceView.drawingThread = null;
		gameSurfaceView.drawingThread = new DrawingThread(this,gameSurfaceView);
		gameSurfaceView.drawingThread.start();
		DrawingThread.score=0;
	}
	
	private void stopGame() {
		this.finish();
	}

	public void setMessage() {
		AlertDialog.Builder alertBuilder=new AlertDialog.Builder(GameActivity.this);
		alertBuilder.setTitle("YOU LOSE...");
		alertBuilder.setIcon(R.drawable.alert);
		alertBuilder.setMessage("Do You Want To Save Score ?");
		
		alertBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		alertBuilder.setNegativeButton("MENU", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				
			}
		});
		
		AlertDialog alertDialog=alertBuilder.create();
		alertDialog.show();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void run() {
		
		if (gameSurfaceView.drawingThread.detectFlag) {
			setMessage();
		}
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

	private void writeScore(String score) {
		FileOutputStream os = null;
		try {

			os = new FileOutputStream("score_data.txt");
			os.write(("").getBytes());
			os.write(score.getBytes());
			os.flush();
			os.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (os != null) {
				os = null;
			}
		}
	}

	private void updateScore(){
		String tempStringScore=readScore();
		int tempScore=Integer.parseInt(tempStringScore);

		if(tempScore<gameSurfaceView.drawingThread.score){
			String updateScore=Integer.toString(gameSurfaceView.drawingThread.score);
			writeScore(updateScore);
		}

	}

}

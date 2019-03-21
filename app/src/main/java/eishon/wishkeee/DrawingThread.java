package eishon.wishkeee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;

public class DrawingThread extends Thread {

	private Canvas canvas;
	private Context context;
	private GameSurfaceView gameSurfaceView;

	boolean threadFlag = false;
	boolean jumpFlag = false;
	boolean slideFlag = false;
	boolean pauseFlag = false;
	boolean detectFlag = false;

	Bitmap background;

	public int displayX, displayY;
	static int index;
	static int backgroundCounter;
	static int score = 0;
	static float time = 0.5f;
	static int obstacleVelocity = 70;

	int obstacleXPosition, obstacleYPosition, obstacleYPosition_2;
	int dif, removePosition;
	int manHeight, manWidth;

	Random random;

	Man man;

	Paint rectPaint;

	ArrayList<Bitmap> animMan;

	ArrayList<Cloud> cloud;
	ArrayList<Obstacle> obstacle;
	ArrayList<Bitmap> obstacleBitmap;

	ManAnimationThread manAnimationThread;
	BackgroundAnimThread backgroundAnimThread;
	CloudThread cloudThread;
	ObstacleThread obstacleThread;
	CollisionDetectionThread collisionDetectionThread;

	DisplaySize displaySize;

	public DrawingThread(Context context, GameSurfaceView gameSurfaceView) {
		super();
		this.context = context;
		this.gameSurfaceView = gameSurfaceView;
		index = 0;
		backgroundCounter = 0;

		initializeAll();
	}

	private void initializeAll() {
		displaySize = new DisplaySize(gameSurfaceView);

		displayX = displaySize.getDisplayX();
		displayY = displaySize.getDisplayY();

		obstacleXPosition = displayX + (displayX / 6);
		obstacleYPosition = (int) (displayY * 0.951965) - displayY / 8;
		obstacleYPosition_2 = (int) ((displayY * 0.951965) - (displayY * 0.3));

		manWidth = displayX / 8;
		manHeight = displayY / 5;

		man = new Man(this, context);

		random = new Random();

		background = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.background);
		background = Bitmap.createScaledBitmap(background, displayX + 200,
				displayY, true);

		rectPaint=new Paint();

		initializeAnimBitmap();

	}

	private void initializeAnimBitmap() {
		animMan = new ArrayList<Bitmap>();

		cloud = new ArrayList<Cloud>();
		obstacle = new ArrayList<Obstacle>();
		obstacleBitmap = new ArrayList<Bitmap>();

		animMan.add(resizedManBitmap(R.drawable.man_0));
		animMan.add(resizedManBitmap(R.drawable.man_1));
		animMan.add(resizedManBitmap(R.drawable.man_2));
		animMan.add(resizedManBitmap(R.drawable.man_3));
		animMan.add(resizedManBitmap(R.drawable.man_4));
		animMan.add(resizedManBitmap(R.drawable.man_5));
		animMan.add(resizedManBitmap(R.drawable.man_6));
		animMan.add(resizedManBitmap(R.drawable.man_7));
		animMan.add(resizedManBitmap(R.drawable.man_12));
		animMan.add(resizedManBitmap_2(R.drawable.man_13));

		cloud.add(new Cloud(resizedCloudBitmap(R.drawable.cloud_0), random
				.nextInt(displayX), 50, 6));
		cloud.add(new Cloud(resizedCloudBitmap(R.drawable.cloud_1), random
				.nextInt(displayX), 25, 8));
		cloud.add(new Cloud(resizedCloudBitmap(R.drawable.cloud_2), random
				.nextInt(displayX), 75, 11));
		cloud.add(new Cloud(resizedCloudBitmap(R.drawable.cloud_0), random
				.nextInt(displayX), 125, 9));

		obstacleBitmap.add(resizedObstacleBitmap(R.drawable.obstacle_0));
		obstacleBitmap.add(resizedObstacleBitmap(R.drawable.obstacle_1));
		obstacleBitmap.add(resizedObstacleBitmap(R.drawable.obstacle_2));
		obstacleBitmap.add(resizedObstacleBitmap(R.drawable.obstacle_3));

	}

	private Bitmap resizedManBitmap(int resourceId) {
		Bitmap tempBitmap = BitmapFactory.decodeResource(
				context.getResources(), resourceId);
		tempBitmap = Bitmap.createScaledBitmap(tempBitmap, manWidth, manHeight,
				true);

		return tempBitmap;
	}
	
	private Bitmap resizedManBitmap_2(int resourceId) {
		Bitmap tempBitmap = BitmapFactory.decodeResource(
				context.getResources(), resourceId);
		tempBitmap = Bitmap.createScaledBitmap(tempBitmap, manWidth, manHeight - displayY / 20,
				true);

		return tempBitmap;
	}

	private Bitmap resizedCloudBitmap(int resourceId) {
		Bitmap tempBitmap = BitmapFactory.decodeResource(
				context.getResources(), resourceId);
		tempBitmap = Bitmap.createScaledBitmap(tempBitmap, displayX / 6,
				displayY / 6, true);

		return tempBitmap;
	}

	private Bitmap resizedObstacleBitmap(int resourceId) {
		Bitmap tempBitmap = BitmapFactory.decodeResource(
				context.getResources(), resourceId);
		tempBitmap = Bitmap.createScaledBitmap(tempBitmap, displayX / 9,
				displayY / 8, true);

		return tempBitmap;
	}

	@Override
	public void run() {
		threadFlag = true;
		manAnimationThread = new ManAnimationThread(this);
		backgroundAnimThread = new BackgroundAnimThread(this);
		cloudThread = new CloudThread(this);
		obstacleThread = new ObstacleThread(this);
		collisionDetectionThread = new CollisionDetectionThread(this);

		manAnimationThread.start();
		backgroundAnimThread.start();
		cloudThread.start();
		obstacleThread.start();
		collisionDetectionThread.start();

		while (threadFlag) {
			canvas = gameSurfaceView.surfaceHolder.lockCanvas();

			try {
				synchronized (gameSurfaceView.surfaceHolder) {
					
					if (detectFlag==false) {
						updateDisplay();
					}else {
						threadFlag=false;
						obstacle.clear();
						lostStateDrawing();
				}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					gameSurfaceView.surfaceHolder.unlockCanvasAndPost(canvas);

				}
			}

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		manAnimationThread.stopThread();
		backgroundAnimThread.stopThread();
		cloudThread.stopThread();
		obstacleThread.stopThread();
		collisionDetectionThread.stopThread();

	}

	

	private void updateDisplay() {
		
		
		canvas.drawBitmap(background, backgroundCounter, 0, null);

		updateManDrawing();
		updateCloudDrawing();
		updateObstacleDrawing();
		
		drawScoreData();

		//rectPaint.setColor(Color.BLACK);canvas.drawOval(collisionDetectionThread.manRect, rectPaint);
		//rectPaint.setColor(Color.BLACK);canvas.drawOval(collisionDetectionThread.obstacleRect, rectPaint);

		if (pauseFlag) {
			pauseStateDrawing();
		}
	}

	private void updateManDrawing() {
		if (pauseFlag == false) {

			if (jumpFlag) {
				manAnimationThread.stopThread();
				updateJumpPosition();
				canvas.drawBitmap(animMan.get(animMan.size() - 2), man.centerX, man.centerY,
						null);
			} else if (slideFlag) {
				manAnimationThread.stopThread();
				updateSlidePosition();
				canvas.drawBitmap(animMan.get(animMan.size() - 1), man.centerX,
						man.centerY + manHeight / 5 + displayY / 30, null);
			} else if (!jumpFlag && !slideFlag) {
				canvas.drawBitmap(animMan.get(index), man.centerX, man.centerY,
						null);
			}
		}

	}

	private void updateCloudDrawing() {
		for (int i = 0; i < cloud.size(); i++) {
			Cloud tempCloud = cloud.get(i);
			canvas.drawBitmap(tempCloud.cloudBitmap, tempCloud.centerX,
					tempCloud.centerY, tempCloud.cloudPaint);

		}

	}

	private void updateObstacleDrawing() {

		if (obstacle.size() == 0) {
			int temp = random.nextInt(4);
			if (temp == 0) {
				obstacle.add(new Obstacle(
						obstacleBitmap.get(random.nextInt(4)),
						obstacleXPosition, obstacleYPosition, obstacleVelocity));
			} else if (temp == 1) {
				obstacle.add(new Obstacle(
						obstacleBitmap.get(random.nextInt(4)),
						obstacleXPosition, obstacleYPosition_2,
						obstacleVelocity));
			}
		}

		for (int i = 0; i < obstacle.size(); i++) {
			Obstacle tempObstacle = obstacle.get(i);
			canvas.drawBitmap(tempObstacle.obstacleBitmap,
					tempObstacle.centerX, tempObstacle.centerY,
					tempObstacle.obstaclePaint);

			dif = obstacleXPosition - obstacle.get(i).centerX;
			removePosition = -displayX / 6;
			if (dif >= (displayX /2)
					&& dif <= (displayX/2) + obstacleVelocity / 3
					&& obstacle.size() < 3) {

				int temp = random.nextInt(2);
				if (temp == 0) {
					obstacle.add(new Obstacle(obstacleBitmap.get(random
							.nextInt(3)), obstacleXPosition, obstacleYPosition,
							obstacleVelocity));
				} else if (temp == 1) {
					obstacle.add(new Obstacle(obstacleBitmap.get(random
							.nextInt(3)), obstacleXPosition,
							obstacleYPosition_2, obstacleVelocity));

				}
			}
			if (obstacle.get(i).centerX >= 0
					&& obstacle.get(i).centerX < obstacleVelocity / 2) {
				score += 1;
			}
			if (obstacle.get(0).centerX <= removePosition) {
				obstacle.remove(0);
			}
		}
	}

	private void pauseStateDrawing() {

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(100);
		paint.setTextAlign(Align.CENTER);

		if (slideFlag) {
			canvas.drawBitmap(animMan.get(animMan.size() - 1), man.centerX,
					man.centerY + manHeight / 5, null);
		} else {
			canvas.drawBitmap(animMan.get(0), man.centerX, man.centerY, null);
		}

		canvas.drawARGB(150, 0, 0, 0);
		canvas.drawText("PAUSED", displayX / 2, displayY / 2, paint);

	}

	private void lostStateDrawing() {
		int tempScore;
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);

		canvas.drawARGB(150, 0, 0, 0);
		canvas.drawText("...!!! YOU LOST !!!...", displayX / 2, displayY / 2,
				paint);
		canvas.drawText("SCORE: " + score, displayX / 2, displayY / 2 + 100,
				paint);

	}

	private void updateSlidePosition() {
		man.velocityY -= man.gravityY * time;

		if (man.velocityY == -80) {
			man.velocityY = 80;
			slideFlag = false;
			restartThread();
		}

	}

	private void updateJumpPosition() {
		man.centerY -= man.velocityY * time - 0.5 * man.gravityY * time * time;

		man.velocityY -= man.gravityY * time;

		if (man.velocityY == -80) {
			man.velocityY = 80;
			man.centerY = (int) ((displayY * 0.951965) - manHeight);
			jumpFlag = false;
			restartThread();
		}

	}

	public void drawScoreData() {
		Paint paint = new Paint();
		paint.setTextSize(75);
		paint.setColor(Color.WHITE);
		canvas.drawText("Score: " + score, (displayX/2)-50, (float) (displayY*0.1), paint);
	}

	private void restartThread() {
		manAnimationThread.stopThread();
		manAnimationThread = null;
		manAnimationThread = new ManAnimationThread(this);
		manAnimationThread.start();

	}

	public void stopThread() {
		threadFlag = false;

	}
	
	public void drawRectangle(Rect rect,Paint paint) {
		canvas.drawRect(rect, paint);
	}

	public void detected() {
		detectFlag = true;
	}

	public void notDetected() {
		detectFlag = false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		DrawingThread.index = index;
	}

	public static int getBackgroundCounter() {
		return backgroundCounter;
	}

	public static void setBackgroundCounter(int backgroundCounter) {
		DrawingThread.backgroundCounter = backgroundCounter;
	}

	public static int getObstacleVelocity() {
		return obstacleVelocity;
	}

	public static void setObstacleVelocity(int obstacleVelocity) {
		DrawingThread.obstacleVelocity = obstacleVelocity;
	}

}

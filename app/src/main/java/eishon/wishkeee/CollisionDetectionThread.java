package eishon.wishkeee;

import android.graphics.RectF;

public class CollisionDetectionThread extends Thread {

	private boolean flag = false;

	DrawingThread drawingThread;

	RectF manRect;
	RectF obstacleRect;

	public CollisionDetectionThread(DrawingThread drawingThread) {
		super();
		this.drawingThread = drawingThread;
		
	}

	@Override
	public void run() {
		flag = true;

		while (flag) {
			
			collision();

			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void collision() {
		
		for (int i = 0; i < drawingThread.obstacle.size(); i++) {
			
			initializeManRects();
			
			Obstacle tempObstacle = drawingThread.obstacle.get(0);
			obstacleRect=new RectF(tempObstacle.centerX,
					tempObstacle.centerY, tempObstacle.centerX
					+ tempObstacle.width, tempObstacle.centerY
					+ tempObstacle.height);
			
			detectMethod();

		}

	}

	private void initializeManRects() {
		if (drawingThread.slideFlag) {
			manRect = new RectF(drawingThread.man.centerX,
					(int) (drawingThread.man.centerY+drawingThread.displayY*0.15), drawingThread.man.centerX
							+ drawingThread.manWidth, drawingThread.man.centerY
							+ drawingThread.manHeight);
		}else {
			manRect = new RectF(drawingThread.man.centerX + (int) (drawingThread.man.centerX * 0.25),
					drawingThread.man.centerY, drawingThread.man.centerX
							+ (int) (drawingThread.manWidth * 0.7), drawingThread.man.centerY
							+ drawingThread.manHeight);
		}

		
	}

	private void detectMethod() {
		if (obstacleRect.intersect(manRect)) {
			drawingThread.detected();
		} else {
			drawingThread.notDetected();
		}
	}

	public void startThread() {
		flag = true;

	}

	public void stopThread() {
		flag = false;

	}

}

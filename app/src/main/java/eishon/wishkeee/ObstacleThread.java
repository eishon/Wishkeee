package eishon.wishkeee;

public class ObstacleThread extends Thread {

boolean flag=false;
	
	DrawingThread drawingThread;
	
	float time=0.3f;
	float reactionRatio=-1f;
	
	int width,height;
	int left,right;
	
	public ObstacleThread(DrawingThread drawingThread) {
		super();
		this.drawingThread=drawingThread;
		left=-drawingThread.displayX/4;
		right=drawingThread.displayX+drawingThread.displayX/4;
	}
	
	@Override
	public void run() {
		flag=true;
		
		while (flag) {
			
			updateObstaclesPosition();
			
			try {
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

	private void updateObstaclesPosition() {
		
		for (int i = 0; i < drawingThread.obstacle.size(); i++) {
			updateAllObstaclesPosition(drawingThread.obstacle.get(i));
			
		}
		
	}

	private void updateAllObstaclesPosition(Obstacle obstacles) {
	obstacles.centerX-=obstacles.velocityX*time;
		
	}
	
	public void stopThread() {
		flag=false;

	}

	
}

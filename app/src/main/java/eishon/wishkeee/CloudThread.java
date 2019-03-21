package eishon.wishkeee;

public class CloudThread extends Thread{
	
	boolean flag=false;
	
	DrawingThread drawingThread;
	
	float time=0.3f;
	float reactionRatio=-1f;
	
	int width,height;
	int left,right;
	
	public CloudThread(DrawingThread drawingThread) {
		super();
		this.drawingThread=drawingThread;
		left=-drawingThread.displayX/4;
		right=drawingThread.displayX+drawingThread.displayX/4;
	}
	
	@Override
	public void run() {
		flag=true;
		
		while (flag) {
			
			updateCloudPosition();
			
			try {
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

	private void updateCloudPosition() {
		
		for (int i = 0; i < drawingThread.cloud.size(); i++) {
			updateAllCloudsPosition(drawingThread.cloud.get(i));
			
		}
		
	}

	private void updateAllCloudsPosition(Cloud clouds) {
		clouds.centerX+=clouds.velocityX*time;
	
		reversePosition(clouds);
		
	}
	
	private void reversePosition(Cloud clouds) {
		//x-axis
		if (clouds.centerX<left) {
			clouds.centerX=left;
			clouds.velocityX*=reactionRatio;
			
		}else if (clouds.centerX>right) {
			clouds.centerX=right;
			clouds.velocityX*=reactionRatio;
		}
		
	}

	public void stopThread() {
		flag=false;

	}

	
}

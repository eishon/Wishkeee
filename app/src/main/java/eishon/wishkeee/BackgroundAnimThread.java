package eishon.wishkeee;

public class BackgroundAnimThread extends Thread {

	private boolean flag = false;

	DrawingThread drawingThread;
	
	int backgrndcnt;
	
	Man man;

	public BackgroundAnimThread(DrawingThread drawingThread) {
		super();
		this.drawingThread = drawingThread;
	}

	@Override
	public void run() {
		flag = true;

		while (flag) {

			updateBackGround();

			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void updateBackGround() {
		backgrndcnt = DrawingThread.getBackgroundCounter();
		backgrndcnt--;
		DrawingThread.setBackgroundCounter(backgrndcnt);
		if (backgrndcnt == -200) {
			DrawingThread.setBackgroundCounter(0);
		}

	}

	public void startThread() {
		flag = true;

	}

	public void stopThread() {
		flag = false;

	}

}

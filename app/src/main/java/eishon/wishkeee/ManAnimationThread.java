package eishon.wishkeee;

public class ManAnimationThread extends Thread {
	private boolean flag=false;
	
	int indx;
	
	DrawingThread drawingThread;
	
	Man man;
	
	public ManAnimationThread(DrawingThread drawingThread) {
		super();
		this.drawingThread = drawingThread;
		this.man=drawingThread.man;
	}

	@Override
	public void run() {
		flag=true;
		
		while (flag) {
			
			updateMan();
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

	
	private void updateMan() {
			indx = drawingThread.getIndex();
			indx++;
			drawingThread.setIndex(indx);
			if (indx == drawingThread.animMan.size() - 2) {
				drawingThread.setIndex(0);
			}
	}
	
	public void startThread() {
		flag=true;

	}

	public void stopThread() {
		flag=false;

	}

}

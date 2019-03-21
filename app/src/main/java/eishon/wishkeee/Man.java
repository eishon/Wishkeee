package eishon.wishkeee;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Man {
	int centerX,centerY;
	float velocityX,velocityY;
	float gravityX,gravityY;
	int displayX,displayY;
	int height,width;
	
	Paint manPaint;
	
	DrawingThread drawingThread;
	
	Context context;
	
	public Man(DrawingThread drawingThread,Context context) {
		this.drawingThread=drawingThread;
		this.context=context;
		
		getDisplay();
		
		centerX=(int) (displayX*0.125);
		centerY=(int) ((displayY*0.951965)-drawingThread.manHeight);
		velocityX=0;
		velocityY=80;
		gravityX=0;
		gravityY=10;
		manPaint=new Paint();
		width=drawingThread.manWidth;
		height=drawingThread.manHeight;
		
	}
	
	private void getDisplay() {
		WindowManager windowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display defaultDisplay=windowManager.getDefaultDisplay();
		
		Point displayDimension=new Point();
		
		defaultDisplay.getSize(displayDimension);
		
		displayX=displayDimension.x;
		displayY=displayDimension.y;
		
	}

	public Man(DrawingThread drawingThread,Context context,int cX,int cY) {
		this(drawingThread,context);
		centerX=cX;
		centerY=cY;
		
	}
	
	public Man(DrawingThread drawingThread,Context context,Point center) {
		this(drawingThread,context,center.x, center.y);

	}
	
	public void setCenter(Point centerPoint) {
		centerX=centerPoint.x;
		centerY=centerPoint.y;
		
	}
	
	public void setVelocity(int vX,int vY) {
		velocityX=(float)vX;
		velocityY=(float)vY;
		
	}

}

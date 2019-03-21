package eishon.wishkeee;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;

public class Cloud {
	
	int centerX,centerY;
	float velocityX;
	int displayX,displayY;
	int height,width;
	int topLeft,topRight,bottomLeft,bottomRight;
	
	Bitmap cloudBitmap;
	
	Paint cloudPaint;

	public Cloud(Bitmap cloudBitmap) {
		this.cloudBitmap=cloudBitmap;
		
		centerX=0;
		centerY=0;
		velocityX=0;
		
		cloudPaint=new Paint();
		
	}

	public Cloud(Bitmap cloudBitmap,int cX,int cY) {
		this(cloudBitmap);
		centerX=cX;
		centerY=cY;
		
	}
	
	public Cloud(Bitmap cloudBitmap,int cX,int cY,int vX) {
		this(cloudBitmap,cX, cY);
		velocityX=vX;
	}
	
	public void setCenter(Point centerPoint) {
		centerX=centerPoint.x;
		centerY=centerPoint.y;
		
	}
	
	public void setVelocity(int vX) {
		velocityX=(float)vX;
		
	}


}

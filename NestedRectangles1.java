package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class NestedRectangles1 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 10;
      private int basePointY        = 10;
      private int rectWidth         = 300;
      private int rectHeight        = 300;
      private int currentX          = 0;
      private int currentY          = 0;
      private int strokeWidth       = 5;
 
      private int redColor          = 0xFFFF0000;
      private int blueColor         = 0xFF0000FF;
      private int[] rectColors      = {redColor, blueColor};
      private Paint rectPaint       = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         rectPaint = new Paint();
       //rectPaint.setStyle(Paint.Style.FILL);
         rectPaint.setAntiAlias(true);
         rectPaint.setDither(true);
         rectPaint.setColor(0xFFFF0000);
         rectPaint.setStyle(Paint.Style.STROKE);
         rectPaint.setStrokeJoin(Paint.Join.ROUND);
         rectPaint.setStrokeCap(Paint.Cap.ROUND);
         rectPaint.setStrokeWidth(strokeWidth);
      }
  
      @Override
      protected void onDraw(Canvas canvas)
      {
         renderGraphics(canvas);
      }      

      private void renderGraphics(Canvas canvas)
      {
         for(int x=0; x<rectWidth/2; x++)
         {
            currentX = basePointX+x;
            currentY = basePointY+x;
    
            rectPaint.setColor(rectColors[x%2]);
            canvas.drawRect(currentX+x, 
                            currentY+x,
                            currentX+rectWidth-x, 
                            currentY+rectHeight-x, 
                            rectPaint);
         }
      }
   }
}


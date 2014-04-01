package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class NestedRectangles3 extends Activity 
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
      private int rectColor         = 0;
      private int rVal              = 0;
      private int gVal              = 0;
      private int bVal              = 0;
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
    
            rVal = (int)(x*255/(rectWidth/2));
            rectColor = Color.rgb(rVal, gVal, bVal);
            rectPaint.setColor(rectColor);

            canvas.drawRect(currentX, 
                            currentY,
                            currentX+rectWidth-2*x, 
                            currentY+rectHeight-2*x, 
                            rectPaint);
         }
      }
   }
}


package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class NestedRectangles4 extends Activity 
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
      private int currStrip         = 0;
      private int stripCount        = 3;
      private int stripWidth        = (int)(rectWidth/2/stripCount); 
      private int currentX          = 0;
      private int currentY          = 0;
      private int strokeWidth       = 5;
      private int rectColor         = 0;
      private int rVal              = 0;
      private int gVal              = 0;
      private int bVal              = 0;
      private int index             = 0;
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
    
            index = (int)((x%stripWidth)*255/stripWidth);
            currStrip = (int)(x/stripWidth);

            rVal = 0; gVal = 0; bVal = 0; 

            if(currStrip % 3 == 0)
            {
               rVal = index;
            }
            else if(currStrip % 3 == 1)
            {
               rVal = index;
               gVal = index;
            }
            else 
            {
               bVal = index;
            }

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


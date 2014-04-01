package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class LituusOval1Perp1 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 120;
      private int basePointY        = 130;
      private int currentX          = 0;
      private int currentY          = 0;
      private int minorAxis         = 80;
      private int majorAxis         = 40;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant       = 200;
      private float startAngle      = (float)Math.PI/4;
      private float sweepAngle      = (float)Math.PI;
      private boolean useCenter     = false;
 
      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int greenColor        = 0xFF00FF00;
      private int blueColor         = 0xFF0000FF;
      private int[] ovalColors      = {redColor, blueColor,
                                       redColor, yellowColor};
      private Paint ovalPaint       = null;
      private RectF ovalRectF       = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         ovalPaint = new Paint();
       //ovalPaint.setStyle(Paint.Style.FILL);
         ovalPaint.setAntiAlias(true);
         ovalPaint.setDither(true);
         ovalPaint.setColor(0xFFFF0000);
         ovalPaint.setStyle(Paint.Style.STROKE);
         ovalPaint.setStrokeJoin(Paint.Join.ROUND);
         ovalPaint.setStrokeCap(Paint.Cap.ROUND);
         ovalPaint.setStrokeWidth(strokeWidth);
      }
  
      @Override
      protected void onDraw(Canvas canvas)
      {
         renderGraphics(canvas);
      }      

      private void renderGraphics(Canvas canvas)
      {
         for(angle=1; angle<maxAngle; angle+=deltaAngle) 
         {
            radius   = Constant*Constant/angle;
            offsetX  = radius*Math.cos(angle*Math.PI/180);
            offsetY  = radius*Math.sin(angle*Math.PI/180);
            currentX = (int)(basePointX+offsetX);
            currentY = (int)(basePointY-offsetY);
       
            colorIndex = (int)(Math.floor(angle/deltaAngle) % 2); 
            ovalPaint.setColor(ovalColors[colorIndex]);

            ovalRectF = new RectF(currentX,
                                  currentY,
                                  currentX+majorAxis,
                                  currentY+minorAxis);
 
            canvas.drawOval(ovalRectF, ovalPaint);

            ovalRectF = new RectF(currentX,
                                  currentY,
                                  currentX+minorAxis,
                                  currentY+majorAxis);
 
            ovalPaint.setColor(ovalColors[colorIndex+2]);
            canvas.drawOval(ovalRectF, ovalPaint);
         }
      }
   }
}


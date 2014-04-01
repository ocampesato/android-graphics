package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class ArchOval1Triple1 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 110;
      private int basePointY        = 150;
      private int currentX          = 0;
      private int currentY          = 0;
      private int minorAxis         = 70;
      private int majorAxis         = 30;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant       = 0.20;
      private float startAngle      = (float)Math.PI/4;
      private float sweepAngle      = (float)Math.PI;
      private boolean useCenter     = false;
 
      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int greenColor        = 0xFF00FF00;
      private int blueColor         = 0xFF0000FF;
      private int[] ovalColors      = {redColor,  blueColor,
                                       redColor,  yellowColor,
                                       blueColor, yellowColor};
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
         for(angle=0; angle<maxAngle; angle+=deltaAngle) 
         {
            radius   = Constant*angle;
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

            ovalRectF = new RectF(currentX+majorAxis,
                                  currentY,
                                  currentX+2*majorAxis,
                                  currentY+minorAxis);
 
            ovalPaint.setColor(ovalColors[colorIndex+2]);
            canvas.drawOval(ovalRectF, ovalPaint);

            ovalRectF = new RectF(currentX+majorAxis/2,
                                  currentY+minorAxis/2,
                                  currentX+3*majorAxis/2,
                                  currentY+3*minorAxis/2);
 
            ovalPaint.setColor(ovalColors[colorIndex+4]);
            canvas.drawOval(ovalRectF, ovalPaint);
         }
      }
   }
}


package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class LissajousQBezier2 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 150;
      private int basePointY        = 150;
      private int currentX          = 0;
      private int currentY          = 0;
      private int minorAxis         = 80;
      private int majorAxis         = 30;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaX            = 20;
      private int deltaY            = 40;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private int A                 = 120;
      private int B                 = 2;
      private int C                 = 100;
      private int D                 = 5;
 
      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int blueColor         = 0xFF0000FF;
      private int[] ovalColors      = {redColor,  blueColor,
                                       redColor,  yellowColor,
                                       blueColor, yellowColor};

      private Paint ovalPaint       = null;
      private Paint qBezierPaint    = null;
      private RectF ovalRectF       = null;
      private Path qBezierPath      = null;
  
      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         qBezierPaint = new Paint();
       //qBezierPaint.setStyle(Paint.Style.FILL);
         qBezierPaint.setAntiAlias(true);
         qBezierPaint.setDither(true);
         qBezierPaint.setColor(0xFFFF0000);
         qBezierPaint.setStyle(Paint.Style.STROKE);
         qBezierPaint.setStrokeJoin(Paint.Join.ROUND);
         qBezierPaint.setStrokeCap(Paint.Cap.ROUND);
         qBezierPaint.setStrokeWidth(strokeWidth);

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
            offsetX  = A*Math.sin(B*angle*Math.PI/180);
            offsetY  = C*Math.cos(D*angle*Math.PI/180);
            currentX = (int)(basePointX+offsetX);
            currentY = (int)(basePointY-offsetY);
             
            colorIndex = (int)(Math.floor(angle/deltaAngle) % 2); 
            ovalPaint.setColor(ovalColors[colorIndex]);

            ovalRectF = new RectF(currentX,
                                  currentY,
                                  currentX+majorAxis,
                                  currentY+minorAxis);
 
            canvas.drawOval(ovalRectF, ovalPaint);

            // render quadratic Bezier curve
            qBezierPath = new Path();
          //qBezierPath.moveTo(currentX,           currentY);
            qBezierPath.moveTo(0,                  0);
            qBezierPath.quadTo(currentX+3*deltaX, currentY+deltaY,
                               currentX-deltaX,   currentY-deltaY);
                             //currentX+2*deltaX, currentY+2*deltaY);

            qBezierPaint.setColor(ovalColors[colorIndex+2]);
            canvas.drawPath(qBezierPath, qBezierPaint);
         }
      }
   }
}


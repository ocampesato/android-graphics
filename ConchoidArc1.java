package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class ConchoidArc1 extends Activity 
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
      private int smallRadius       = 150;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant1      = 220;
      private double Constant2      = 80;
      private double startAngle     = Math.PI/4;
      private double sweepAngle     = Math.PI;
      private boolean useCenter     = true;
 
      private int redColor          = 0xFFFF0000;
      private int blueColor         = 0xFF0000FF;
      private int[] arcColors       = {redColor, blueColor};
      private Paint arcPaint        = null;
      private RectF arcRectF        = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         arcPaint = new Paint();
       //arcPaint.setStyle(Paint.Style.FILL);
         arcPaint.setAntiAlias(true);
         arcPaint.setDither(true);
         arcPaint.setStyle(Paint.Style.STROKE);
         arcPaint.setStrokeJoin(Paint.Join.ROUND);
         arcPaint.setStrokeCap(Paint.Cap.ROUND);
         arcPaint.setStrokeWidth(strokeWidth);
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
            radius   = Constant1+Constant2/Math.cos(angle*Math.PI/180);
            offsetX  = radius*Math.cos(angle*Math.PI/180);
            offsetY  = radius*Math.sin(angle*Math.PI/180);
            currentX = (int)(basePointX+offsetX);
            currentY = (int)(basePointY-offsetY);
       
            colorIndex = (int)(Math.floor(angle/deltaAngle) % 2); 
            arcPaint.setColor(arcColors[colorIndex]);

            arcRectF = new RectF(currentX,
                                 currentY,
                                 currentX+smallRadius,
                                 currentY+smallRadius);

            canvas.drawArc(arcRectF, (float)startAngle+angle, (float)sweepAngle, 
                           useCenter, arcPaint);
         }
      }
   }
}


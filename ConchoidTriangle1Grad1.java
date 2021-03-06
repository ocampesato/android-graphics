package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class ConchoidTriangle1Grad1 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 70;
      private int basePointY        = 150;
      private int currentX          = 0;
      private int currentY          = 0;
      private int deltaX            = 20;
      private int deltaY            = 30;
      private int minorAxis         = 80;
      private int majorAxis         = 30;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private int rVal              = 0;
      private int gVal              = 0;
      private int bVal              = 0;
      private int polyColor         = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant1      = 120;
      private double Constant2      = 80;
      private float startAngle      = (float)Math.PI/4;
      private float sweepAngle      = (float)Math.PI;
      private boolean useCenter     = false;
 
      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int blueColor         = 0xFF0000FF;
      private int[] polyColors      = {redColor, blueColor,
                                       redColor, yellowColor};

      private Paint polyPaint       = null;
      private RectF polyRectF       = null;
      private Path  polyPath        = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         polyPaint = new Paint();
       //polyPaint.setStyle(Paint.Style.FILL);
         polyPaint.setAntiAlias(true);
         polyPaint.setDither(true);
         polyPaint.setColor(0xFFFF0000);
         polyPaint.setStyle(Paint.Style.STROKE);
         polyPaint.setStrokeJoin(Paint.Join.ROUND);
         polyPaint.setStrokeCap(Paint.Cap.ROUND);
         polyPaint.setStrokeWidth(strokeWidth);
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
            polyPaint.setColor(polyColors[colorIndex]);

            // render triangle...
            polyPath = new Path();
            polyPath.moveTo(currentX,          currentY);
            polyPath.lineTo(currentX+deltaX,   currentY+deltaY);
            polyPath.lineTo(currentX-deltaX,   currentY+deltaY);
            polyPath.lineTo(currentX,          currentY);

            rVal = (int)(angle*255/maxAngle);
            polyColor = Color.rgb(rVal, gVal, bVal);

            polyPaint.setColor(polyColor);
            canvas.drawPath(polyPath, polyPaint);
         }
      }
   }
}


package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class CochleoidTriangle1Rotate1 extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 140;
      private int basePointY        = 320;
      private int currentX          = 0;
      private int currentY          = 0;
      private int deltaX            = 40;
      private int deltaY            = 50;
      private int minorAxis         = 80;
      private int majorAxis         = 30;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private int polyRadius        = 25;
      private int vertexCount       = 3;
      private int polyAngle         = 360/vertexCount;
      private int[] polyXPts        = new int[vertexCount];
      private int[] polyYPts        = new int[vertexCount];
      private double currPolyAngle  = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant       = 30000;
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
         for(angle=1; angle<maxAngle; angle+=deltaAngle) 
         {
            radius   = Constant*Math.sin(angle*Math.PI/180)/angle; 
            offsetX  = radius*Math.cos(angle*Math.PI/180);
            offsetY  = radius*Math.sin(angle*Math.PI/180);
            currentX = (int)(basePointX+offsetX);
            currentY = (int)(basePointY-offsetY);
       
            colorIndex = (int)(Math.floor(angle/deltaAngle) % 2); 
            polyPaint.setColor(polyColors[colorIndex]);

            // calculate new triangle vertices...
            for(int p=0; p<vertexCount; p++)
            {
               currPolyAngle = (angle+polyAngle*p)*Math.PI/180; 
               polyXPts[p] = (int)(polyRadius*Math.cos(currPolyAngle));
               polyYPts[p] = (int)(polyRadius*Math.sin(currPolyAngle));
            }

            polyPath = new Path();
            polyPath.moveTo(currentX+polyXPts[0],currentY-polyYPts[0]); 
            polyPath.lineTo(currentX+polyXPts[1],currentY-polyYPts[1]); 
            polyPath.lineTo(currentX+polyXPts[2],currentY-polyYPts[2]); 
            polyPath.lineTo(currentX+polyXPts[0],currentY-polyYPts[0]); 

            polyPaint.setColor(polyColors[colorIndex]);
            canvas.drawPath(polyPath, polyPaint);
         }
      }
   }
}


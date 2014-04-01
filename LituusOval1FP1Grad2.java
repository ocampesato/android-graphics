package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class LituusOval1FP1Grad2 extends Activity 
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
      private int basePointY        = 130;
      private int fixedPointX       = 150;
      private int fixedPointY       = 100;
      private int currentX          = 0;
      private int currentY          = 0;
      private int minorAxis         = 120;
      private int majorAxis         = 50;
      private int strokeWidth       = 5;
      private int angle             = 0;
      private int deltaAngle        = 1;
      private int maxAngle          = 720;
      private int colorIndex        = 0;
      private int stripCount        = 10;
      private int stripWidth        = (int)(maxAngle/stripCount);
      private int rVal              = 0;
      private int gVal              = 0;
      private int bVal              = 0;
      private int lineColor         = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
      private double radius         = 0;
      private double Constant       = 200;
 
      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int blueColor         = 0xFF0000FF;
      private int[] ovalColors      = {yellowColor, blueColor,
                                       redColor, yellowColor};
      private Paint linePaint       = null;
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
         linePaint = new Paint();
       //linePaint.setStyle(Paint.Style.FILL);
         linePaint.setAntiAlias(true);
         linePaint.setDither(true);
         linePaint.setColor(0xFFFF0000);
         linePaint.setStyle(Paint.Style.STROKE);
         linePaint.setStrokeJoin(Paint.Join.ROUND);
         linePaint.setStrokeCap(Paint.Cap.ROUND);
         linePaint.setStrokeWidth(strokeWidth);

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

            rVal = (int)((angle%stripWidth)*255/stripWidth);
            lineColor = Color.rgb(rVal, gVal, bVal);

            linePaint.setColor(lineColor);
            canvas.drawLine(fixedPointX, fixedPointY,
                            currentX,    currentY, 
                            linePaint);

         }
      }
   }
}


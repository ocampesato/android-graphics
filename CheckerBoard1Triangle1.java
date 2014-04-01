package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

public class CheckerBoard1Triangle1 extends Activity 
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
      private int rowCount          = 10;
      private int colCount          = 10;
      private int cellWidth         = 30;
      private int cellHeight        = 30;
      private int currentX          = 0;
      private int currentY          = 0;
      private int strokeWidth       = 5;
      private int colorIndex        = 0;
      private double offsetX        = 0;
      private double offsetY        = 0;
 
      private int redColor          = 0xFFFF0000;
      private int blueColor         = 0xFF0000FF;
      private int[] rectColors      = {redColor, blueColor};
      private Paint rectPaint       = null;
      private Paint polyPaint       = null;
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
         for(int row=0; row<rowCount; row++)
         {
            for(int col=0; col<colCount; col++)
            {
               currentX = basePointX+col*cellWidth;
               currentY = (int)(basePointY+row*cellHeight);
       
               colorIndex = (row+col)%2;

/*
               rectPaint.setColor(rectColors[colorIndex]);
               canvas.drawRect(currentX, 
                               currentY,
                               currentX+cellWidth, 
                               currentY+cellHeight,
                               rectPaint);
*/

               // left triangle (clockwise from top-most vertex)
               polyPath = new Path();
               polyPath.moveTo(currentX,           currentY);
               polyPath.lineTo(currentX+cellWidth, currentY+cellHeight);
               polyPath.lineTo(currentX,           currentY+cellHeight);
               polyPath.lineTo(currentX,           currentY);

             //polyPaint.setColor(rectColors[colorIndex]);
               polyPaint.setColor(rectColors[0]);
               canvas.drawPath(polyPath, polyPaint);
 
               // right triangle (clockwise from top-most vertex)
               polyPath = new Path();
               polyPath.moveTo(currentX,           currentY);
               polyPath.lineTo(currentX+cellWidth, currentY);
               polyPath.lineTo(currentX+cellWidth, currentY+cellHeight);
               polyPath.lineTo(currentX,           currentY);

             //colorIndex = (colorIndex+1)%2;
             //polyPaint.setColor(rectColors[colorIndex]);
               polyPaint.setColor(rectColors[1]);
               canvas.drawPath(polyPath, polyPaint);

               // white rectangular outline
               rectPaint.setColor(Color.WHITE);
               rectPaint.setStyle(Paint.Style.STROKE);
               canvas.drawRect(currentX, 
                               currentY,
                               currentX+cellWidth, 
                               currentY+cellHeight,
                               rectPaint);
            }
         }
      }
   }
}


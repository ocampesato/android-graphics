package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MultiLineGraph2D extends Activity 
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(new SimpleView(this));
   }

   private static class SimpleView extends View 
   {
      private int basePointX        = 40;
      private int basePointY        = 50;
      private int currentX          = 0;
      private int currentY          = 0;
      private int offsetX           = 0;
      private int offsetY           = 0;
      private int maxHeight         = 200;
      private int minHeight         = 20;
      private int barWidth          = 30;
      private int barCount          = 8;
      private int lineThickness     = 4;
      private int hTickMarkWidth    = 2;
      private int hTickMarkHeight   = 8;
      private int vTickMarkCount    = 10;
      private int textSize          = 16; // 12;
      private int textWidth         = 24;
      private int indentX           = 5;
      private int indentY           = 5;
      private int dotRadius         = 10;
      private int labelX            = 0;
      private int labelY            = 0;
      private int XAxisX            = basePointX-indentX;
      private int XAxisY            = basePointY+maxHeight+indentX;
      private int YAxisX            = basePointX-indentX;
      private int YAxisY            = basePointY-2*indentY;
      private int chartWidth        = barWidth*barCount;
      private int XAxisWidth        = chartWidth+barWidth/2;
      private int YAxisHeight       = maxHeight+barWidth/2;
      private int lineCount         = 5;
      private int[] barHeights      = new int[barCount];
      private int[][] multiLineXPts = new int[lineCount][barCount];
      private int[][] multiLineYPts = new int[lineCount][barCount];
      private String msgString      = "";

      private int redColor          = 0xFFFF0000;
      private int yellowColor       = 0xFFFFFF00;
      private int blueColor         = 0xFF0000FF;
      private int[] barColors       = {redColor, blueColor, yellowColor};

      private Paint dotPaint        = null;
      private Paint linePaint       = null;
      private Paint textPaint       = null;
      private RectF ovalRectF       = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         dotPaint = new Paint();
         dotPaint.setStyle(Paint.Style.FILL);
         dotPaint.setStrokeWidth(lineThickness); 
         dotPaint.setColor(Color.WHITE); 

         linePaint = new Paint();
         linePaint.setStyle(Paint.Style.FILL);
         linePaint.setStrokeWidth(lineThickness); 
         linePaint.setColor(Color.WHITE); 

         linePaint.setAntiAlias(true);
         linePaint.setDither(true);
         linePaint.setStrokeJoin(Paint.Join.ROUND);
         linePaint.setStrokeCap(Paint.Cap.ROUND);
 
         textPaint = new Paint();
         textPaint.setStyle(Paint.Style.FILL);
       //textPaint.setStrokeWidth(colorStrokeWidth); 
         textPaint.setColor(Color.WHITE);
         textPaint.setTextSize(textSize);

       //randomizeBarHeights();
         randomizeMultiLines();
      }
      
      private void randomizeMultiLines()
      {  
         for(int line=0; line<lineCount; line++) 
         {
            for(int bar=0; bar<barCount; bar++) 
            {
               multiLineXPts[line][bar] = basePointX+bar*barWidth;
               multiLineYPts[line][bar] = (int)(maxHeight*Math.random());
   
               if(multiLineYPts[line][bar] < minHeight)
               {
                  multiLineYPts[line][bar] = minHeight;
               }

               multiLineYPts[line][bar] += basePointY;
            }
         }
      }
      
      @Override
      protected void onDraw(Canvas canvas)
      {
         renderLineSegments(canvas);
         renderHorizontalAxis(canvas);
         renderVerticalAxis(canvas);
         labelHorizontalAxis(canvas);
         labelVerticalAxis(canvas);
      }      

      private void renderLineSegments(Canvas canvas)
      {
         for(int line=0; line<lineCount; line++) 
         {
            linePaint.setColor(barColors[line%3]); 

            for(int bar=0; bar<barCount-1; bar++) 
            {
               canvas.drawLine(multiLineXPts[line][bar],
                               multiLineYPts[line][bar],
                               multiLineXPts[line][bar+1],
                               multiLineYPts[line][bar+1],
                               linePaint);

               // render line text
               currentX  = basePointX+bar*barWidth;
               currentY  = multiLineYPts[line][bar]-textSize/2;
               msgString = ""+multiLineYPts[line][bar];
   
               canvas.drawText(msgString, currentX, currentY, linePaint);
            }
         }
      } 

      private void renderHorizontalAxis(Canvas canvas)
      {      
         canvas.drawLine(XAxisX, XAxisY, 
                         XAxisX+XAxisWidth, XAxisY, 
                         linePaint);
      }      

      private void renderVerticalAxis(Canvas canvas)
      {      
         canvas.drawLine(YAxisX, YAxisY, 
                         YAxisX, YAxisY+YAxisHeight,
                         linePaint);
      }      

      private void labelHorizontalAxis(Canvas canvas)
      {      
         for(int bar=0; bar<barCount; bar++) 
         {
            currentX = basePointX+bar*barWidth+barWidth/2-hTickMarkWidth/2;
            currentY = basePointY+maxHeight;

            canvas.drawRect(currentX,   
                            currentY,
                            currentX+hTickMarkWidth, 
                            currentY+hTickMarkHeight, 
                            linePaint);
         }
      }      

      private void labelVerticalAxis(Canvas canvas)
      {      
         for(int y=0; y<=vTickMarkCount; y++) 
         {
            labelY  = (int)(y*maxHeight/vTickMarkCount);
            offsetX = YAxisX - textWidth; 
            offsetY = basePointY + maxHeight - labelY; 
            msgString = ""+labelY;

            canvas.drawText(msgString, offsetX, offsetY, linePaint);
         }
      }      

      @Override
      public boolean onTouchEvent(MotionEvent event) 
      {
         switch (event.getAction()) {
             case MotionEvent.ACTION_DOWN:
                 randomizeMultiLines();
                 invalidate();
                 break;
             case MotionEvent.ACTION_MOVE:
                 break;
             case MotionEvent.ACTION_UP:
                 break;
         }

         return true;
      }
   }
}


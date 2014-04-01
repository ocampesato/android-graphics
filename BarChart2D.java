package oac.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class BarChart2D extends Activity 
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
      private int lineThickness     = 2;
      private int hTickMarkWidth    = 2;
      private int hTickMarkHeight   = 8;
      private int vTickMarkCount    = 10;
      private int textSize          = 12;
      private int textWidth         = 24;
      private int indentX           = 5;
      private int indentY           = 5;
      private int labelX            = 0;
      private int labelY            = 0;
      private int XAxisX            = basePointX-indentX;
      private int XAxisY            = basePointY+maxHeight+indentX;
      private int YAxisX            = basePointX-indentX;
      private int YAxisY            = basePointY-2*indentY;
      private int chartWidth        = barWidth*barCount;
      private int XAxisWidth        = chartWidth+barWidth/2;
      private int YAxisHeight       = maxHeight+barWidth/2;
      private int[] barHeights      = new int[barCount];
      private String msgString      = "";

      private int redColor          = 0xFFFF0000;
      private int blueColor         = 0xFF0000FF;
      private int[] barColors       = {redColor, blueColor};

      private Paint barPaint        = null;
      private Paint linePaint       = null;
      private Paint textPaint       = null;

      public SimpleView(Context context)
      {
         super(context);
         setFocusable(true);

         initialize();
      }
      
      private void initialize()
      {
         barPaint = new Paint();
         barPaint.setStyle(Paint.Style.FILL);
         barPaint.setStrokeWidth(lineThickness); 

         linePaint = new Paint();
         linePaint.setStyle(Paint.Style.FILL);
         linePaint.setStrokeWidth(lineThickness); 
         linePaint.setColor(Color.WHITE); 

         textPaint = new Paint();
         textPaint.setStyle(Paint.Style.FILL);
       //textPaint.setStrokeWidth(colorStrokeWidth); 
         textPaint.setColor(Color.WHITE);
         textPaint.setTextSize(textSize);

         randomizeBarHeights();
      }
      
      private void randomizeBarHeights()
      {
         for(int bar=0; bar<barCount; bar++) 
         {
            barHeights[bar] = (int)(maxHeight*Math.random());
            if(barHeights[bar] < minHeight)
            {
               barHeights[bar] = minHeight;
            }
         }
      }
      
      @Override
      protected void onDraw(Canvas canvas)
      {
         renderBars(canvas);
         renderHorizontalAxis(canvas);
         renderVerticalAxis(canvas);
         labelHorizontalAxis(canvas);
         labelVerticalAxis(canvas);
      }      

      private void renderBars(Canvas canvas)
      {
         for(int bar=0; bar<barCount; bar++) 
         {
            offsetY  = maxHeight-barHeights[bar];
            currentX = basePointX+bar*barWidth;
            currentY = basePointY+offsetY;

            // render single bar 
            barPaint.setColor(barColors[bar%2]); 
            canvas.drawRect(currentX,   
                            currentY,
                            currentX+barWidth, 
                            currentY+barHeights[bar], 
                            barPaint);

            // render bar text
            currentY  = basePointY+offsetY-textSize/2;
            msgString = ""+barHeights[bar];

            canvas.drawText(msgString, currentX, currentY, linePaint);
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
                 randomizeBarHeights();
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


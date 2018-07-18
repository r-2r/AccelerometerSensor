
// custom view

package com.example.snippet.accelerometersensor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class CustomView extends View {

    private Paint paint;
    private float left1, top1, right1, bottom1;
    private float left2, top2, right2, bottom2;
    private float left3, top3, right3, bottom3;

    private float x1, x2, x3, len;
    private String strx, stry, strz;

    // constructor
    public CustomView(Context context) {
        super(context);

        float k = 50.0f;
        float c = 10.0f;

        paint = new Paint();

        paint.setTextSize(k);
        paint.setColor(0xff7f7f7f);

        strx = stry = strz = "-.--";

        x1 = 50.0f;

        // upper bar
        top1    = 50.0f;
        bottom1 =  top1 + k;

        // middle bar
        top2    = top1 + k + c;
        bottom2 =  top2 + k;

        // lower bar
        top3    = top2 + k + c;
        bottom3 =  top3 + k;
    }

    // render screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("X", x1, bottom1, paint);
        canvas.drawRect(left1, top1, right1, bottom1, paint);
        canvas.drawText(strx, x3, bottom1, paint);

        canvas.drawText("Y", x1, bottom2, paint);
        canvas.drawRect(left2, top2, right2, bottom2, paint);
        canvas.drawText(stry, x3, bottom2, paint);

        canvas.drawText("Z", x1, bottom3, paint);
        canvas.drawRect(left3, top3, right3, bottom3, paint);
        canvas.drawText(strz, x3, bottom3, paint);
    }

    //
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        x2 = (float)w / 2.0f;
        x3 = (float)w  - 125.0f;

        len = x3 - x2;

        left1 = left2 = left3 = x2;
        right1 = right2 = right3 = left1 + len;
    }

    //
    public void setValue(float x, float y, float z){

        strx = String.format("%4.2f", x);
        stry = String.format("%4.2f", y);
        strz = String.format("%4.2f", z);

        float r = (float)Math.sqrt(x*x + y*y + z*z);
        float s = len * (r / 10.0f);

        // x - axis
        if(x < 0.0f){
            right1 = x2;
            left1 = right1 + s * (x / r);
        }
        else {
            left1 = x2;
            right1 = left1 + s * (x / r);
        }

        // y -  axis
        if(y < 0.0f){
            right2 = x2;
            left2 = right2 + s * (y / r);
        }
        else {
            left2 = x2;
            right2 = left2 + s * (y / r);
        }

        // z - axis
        if(z < 0.0f){
            right3 = x2;
            left3 = right3 + s * (z / r);
        }
        else {
            left3 = x2;
            right3 = left3 + s * (z / r);
        }

        // refresh display
        invalidate();
    }
}

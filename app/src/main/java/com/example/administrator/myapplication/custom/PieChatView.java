package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class PieChatView extends View {
    private static final float RADIUS=Utils.dp2px(150);
    private static final float OFFSETT_LENGTH=Utils.dp2px(20);
    private static final float OFFSET_INDEX=2;

    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds=new RectF();
    int angles[]={60,100,120,80};
    int color[]={Color.parseColor("#ff3212"),Color.parseColor("#448aff"),
            Color.parseColor("#9575cd"),Color.parseColor("#00c853")};

    public PieChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth()/2f-RADIUS,getHeight()/2f-RADIUS,getWidth()/2f+RADIUS,getHeight()/2f+RADIUS);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngles=0;

        for (int i = 0; i <angles.length ; i++) {
            if (i==OFFSET_INDEX){
                //save 和 restore 目的是保存要偏移的那一块，防止挪走的那一块后面的 不挪去全部跟着偏移了
                canvas.save();
                canvas.translate(OFFSETT_LENGTH*(float) Math.cos(Math.toRadians(currentAngles+angles[i]/2f)),
                        OFFSETT_LENGTH*(float) Math.sin(Math.toRadians(currentAngles+angles[i]/2f)));
            }

            paint.setColor(color[i]);
            canvas.drawArc(bounds,currentAngles,angles[i],true,paint);
            currentAngles+=angles[i];

          if (i==OFFSET_INDEX){
              canvas.restore();
          }

        }



    }
}

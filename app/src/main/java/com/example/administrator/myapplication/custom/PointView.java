package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class PointView extends View {

    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    PointF point=new PointF(0,0);



    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStrokeWidth(Utils.dp2px(20));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(point.x,point.y,paint);

    }



}

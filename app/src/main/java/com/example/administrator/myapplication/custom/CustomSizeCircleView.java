package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class CustomSizeCircleView extends View {

    private static final float RADIUS = Utils.dp2px(80);
    private static final float PADDING = Utils.dp2px(30);
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomSizeCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size= (int) ((RADIUS+PADDING)*2);
        int width = resolveSize(size, widthMeasureSpec);
        int height = resolveSize(size, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.drawCircle(RADIUS+PADDING,RADIUS+PADDING,RADIUS,paint);
    }
}

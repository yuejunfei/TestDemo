package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class SportView extends View {

    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
    String text="abab";
    private Rect bounds=new Rect();
    //设置
    Paint.FontMetrics fontMetrics=new Paint.FontMetrics();

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(100));
        //设置字体
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Quicksand-Regular.ttf"));
        //字体居中
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth()/2f,getHeight()/2f,RADIUS,paint);

        //绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        //设置线头的形状。线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
        paint.setStrokeCap(Paint.Cap.ROUND);
        //画弧
        canvas.drawArc(getWidth()/2f-RADIUS,getHeight()/2f-RADIUS,getWidth()/2f+RADIUS,getHeight()/2f+RADIUS,0,270,false,paint);
        paint.setStrokeCap(Paint.Cap.BUTT);

        //绘制文字

        //填充模式
        paint.setStyle(Paint.Style.FILL);
        //Rect设置文字的装载体--四边
        //静态文字可以使用这种居中方式，但动态文字不行，会一跳一跳的。因为上边和下边的会因为字的不一样而改变距离。
//        paint.getTextBounds(text,0,text.length(),bounds);
//        int offse=(bounds.top+bounds.bottom)/2;
        //动态文字使用
        paint.getFontMetrics(fontMetrics);
        float offse=(fontMetrics.descent+fontMetrics.ascent)/2;
        canvas.drawText(text,getWidth()/2f,getHeight()/2f-offse,paint);

        //文字靠左靠顶
        paint.setTextAlign(Paint.Align.LEFT);
        //要是bounds的top靠顶用这个
        paint.getTextBounds(text,0,text.length(),bounds);
        canvas.drawText(text,-bounds.left,-bounds.top,paint);
        //要是用fontMetrics.ascent或者fontMetrics.top靠顶的话用这个
//        paint.getFontMetrics(fontMetrics);
//        canvas.drawText(text,0,-fontMetrics.top ,paint);

    }
}

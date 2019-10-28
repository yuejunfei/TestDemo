package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class DashboardView extends View {

    private static final float RADIUS=Utils.dp2px(150);
    private static final float OPEN_ANGLE=120;
    private static final float STROKE_WIDTH=Utils.dp2px(3);
    private static final float POINT_LENGTH=Utils.dp2px(100);

    //配置
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash=new Path();
    PathEffect effect;
    Path path=new Path();
    PathMeasure pathMeasure;

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //设置填充模式
        paint.setStyle(Paint.Style.STROKE);
        //设置线的宽度
        paint.setStrokeWidth(STROKE_WIDTH);
        //设置刻度线的宽高以及逆时针方向
        dash.addRect(0,0,Utils.dp2px(2),Utils.dp2px(10),Path.Direction.CCW);
//        paint.setPathEffect(new PathDashPathEffect(dash,50,0,PathDashPathEffect.Style.ROTATE));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //绘制弧形
        path.addArc(getWidth()/2f-RADIUS,getHeight()/2f-RADIUS,getWidth()/2f+RADIUS,getHeight()/2f+RADIUS,90+OPEN_ANGLE/2,360-OPEN_ANGLE);

        //测量path的类
        pathMeasure=new PathMeasure(path,false);
        //获取path的长度
        float length = pathMeasure.getLength();
        //路径效果
        effect= new PathDashPathEffect(dash,(length-Utils.dp2px(2))/20f,0,PathDashPathEffect.Style.ROTATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //绘制弧形
        canvas.drawArc(getWidth()/2f-RADIUS,getHeight()/2f-RADIUS,getWidth()/2f+RADIUS,getHeight()/2f+RADIUS,90+OPEN_ANGLE/2,360-OPEN_ANGLE,false,paint);

        //画刻度
        paint.setPathEffect(effect);
        canvas.drawArc(getWidth()/2f-RADIUS,getHeight()/2f-RADIUS,getWidth()/2f+RADIUS,getHeight()/2f+RADIUS,90+OPEN_ANGLE/2,360-OPEN_ANGLE,false,paint);
        paint.setPathEffect(null);

        //画指针
        canvas.drawLine(getWidth()/2f,getHeight()/2f,getWidth()/2f+POINT_LENGTH*(float)Math.cos(getAngleFromMark(5)),getHeight()/2f+POINT_LENGTH*(float)Math.sin(getAngleFromMark(5)),paint);
    }

    private double getAngleFromMark(int marf){
        return Math.toRadians(90+OPEN_ANGLE/2+(360-OPEN_ANGLE)/20f*5);
    }


}

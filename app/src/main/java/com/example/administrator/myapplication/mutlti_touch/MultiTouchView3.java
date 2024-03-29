package com.example.administrator.myapplication.mutlti_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.Utils;

public class MultiTouchView3 extends View {


   Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
   Path path=new Path();
   SparseArray<Path> paths=new SparseArray<>();

    public MultiTouchView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(4));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                Path path=new Path();
                path.moveTo(event.getX(actionIndex),event.getY(actionIndex));
                paths.append(pointerId,path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i <event.getPointerCount() ; i++) {
                    pointerId=  event.getPointerId(i);
                    path= paths.get(pointerId);
                    path.lineTo(event.getX(i),event.getY(i));
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex= event.getActionIndex();
                pointerId=event.getPointerId(actionIndex);
                paths.remove(pointerId);
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <paths.size() ; i++) {
            Path path = paths.valueAt(i);
            canvas.drawPath(path,paint);
        }

    }


}

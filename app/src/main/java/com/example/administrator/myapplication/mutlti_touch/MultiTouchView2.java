package com.example.administrator.myapplication.mutlti_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.Utils;

public class MultiTouchView2 extends View {

   private static final float IMAGE_WIDTH = Utils.dp2px(200);
   Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
   Bitmap bitmap;
   float offsetX;
   float offSetY;
   float downX;
   float downY;
   float originaloffsetX;
   float originaloffsetY;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap=getAvatar((int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float sumX=0;
        float sumY=0;
        boolean isPointerUp=event.getActionMasked()==MotionEvent.ACTION_POINTER_UP;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i <pointerCount ; i++) {
            if (!isPointerUp||i!=event.getActionIndex()){
                sumX+=event.getX(i);
                sumY+=event.getY(i);
            }
        }
        if (isPointerUp){
            pointerCount--;
        }
        float focusX=sumX/pointerCount;
        float focusY=sumY/pointerCount;
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_DOWN:
                downX=focusX;
                downY=focusY;
                originaloffsetX=offsetX;
                originaloffsetY=offSetY;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX=focusX-downX+originaloffsetX;
                offSetY=focusY-downY+originaloffsetY;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,offsetX,offSetY,paint);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.wechatshare, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.wechatshare, options);
    }
}

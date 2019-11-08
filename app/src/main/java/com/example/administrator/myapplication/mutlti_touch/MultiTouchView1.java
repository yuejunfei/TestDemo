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

public class MultiTouchView1 extends View {

   private static final float IMAGE_WIDTH = Utils.dp2px(200);
   Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
   Bitmap bitmap;
   float offsetX;
   float offSetY;
   float downX;
   float downY;
   float originaloffsetX;
   float originaloffsetY;
   int trackingPoingterId;
    private int actionIndex;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap=getAvatar((int) IMAGE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                trackingPoingterId=event.getPointerId(0);
                downX=event.getX();
                downY=event.getY();
                originaloffsetX=offsetX;
                originaloffsetY=offSetY;
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPoingterId);
                offsetX=event.getX(index)-downX+originaloffsetX;
                offSetY=event.getY(index)-downY+originaloffsetY;
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex=event.getActionIndex();
                trackingPoingterId=event.getPointerId(actionIndex);
                downX=event.getX(actionIndex);
                downY=event.getY(actionIndex);
                originaloffsetX=offsetX;
                originaloffsetY=offSetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId=event.getPointerId(actionIndex);
                if (pointerId==trackingPoingterId){
                    int newIndex;
                    if (actionIndex==event.getPointerCount()-1){
                        newIndex = event.getPointerCount() - 2;
                    }else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPoingterId =event.getPointerId(newIndex);
                    downX=event.getX(newIndex);
                    downY=event.getY(newIndex);
                    originaloffsetX=offsetX;
                    originaloffsetY=offSetY;
                }
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

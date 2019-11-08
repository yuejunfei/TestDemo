package com.example.administrator.myapplication.custom;

import android.animation.ObjectAnimator;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.Utils;

public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH = Utils.dp2px(300);
    //大图的时候 的放大系数
    private static final float OVER_SCALE_FACTOR = 1.5f;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;
    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;
    //大图的比例
    float bigScale;
    //小图的比例
    float smallScale;
    boolean big;
    float currentScale;
    float maxOffsetX;
    float maxOffsetY;

    //侦查器
    GestureDetectorCompat detector;
    //动画
    ObjectAnimator scalenAnimator;
    //滚动器（滑动器）
    OverScroller scroller;
    //双指缩放
    ScaleGestureDetector scaleDetetor;
    ScaleGestureDetector.OnScaleGestureListener henScaleListener=new HenScaleListener();


    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = getAvatar((int) IMAGE_WIDTH);
        //创建滚动器  （Scroller适用于移动，OverScroller适用于跟手惯性滑动）
        scroller = new OverScroller(getContext());

        //双指缩放的监听器
        scaleDetetor=new ScaleGestureDetector(getContext(),henScaleListener);

        //侦查器(配合外挂使用) ⽤于在点击和⻓按之外，增加其他⼿势的监听，例如双击、滑动。
        //GestureDetector.SimpleOnGestureListener() 双监听器支持（OnGestureListener,
        // OnDoubleTapListener这俩种监听器）
        detector = new GestureDetectorCompat(getContext(), new GestureDetector
                .SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                //每次 ACTION_DOWN 事件出现的时候都会被调⽤，在这⾥返回
                //true 可以保证必然消费掉事件
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // ⽤户按下 100ms 不松⼿后会被调⽤，⽤于标记「可以显示按下状态了」
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // ⽤户单击时被调⽤(⽀持长按时长按后松⼿不会调⽤、双击的第⼆下时不会被调⽤)
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float
                    distanceY) {
                // ⽤户滑动时被调⽤
                // 第⼀个事件是⽤户按下时的 ACTION_DOWN 事件，第⼆个事件是当前事件
                // 偏移是按下时的位置 - 当前事件的位置

                //if  限制下  限制放大后才能移动，缩小后不能移动
                if (big) {
                    offsetX -= distanceX;
                    offsetY -= distanceY;
                    fixOffsets();
                    invalidate();
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // ⽤户⻓按（按下 500ms 不松⼿）后会被调⽤
                // 这个 500ms 在 GestureDetectorCompat 中变成了 600ms(？？？)
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                    velocityY) {
                // ⽤于滑动时迅速抬起时被调⽤，⽤于⽤户希望控件进⾏惯性滑动的场景

                if (big) {
                    //fling相当于计算器 秒表不断的去计算值  (最后的俩参数 是过渡滑动，滑动到最旁边还可以在滑动一点距离)
                    scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int)
                            velocityY, -(int) maxOffsetX, (int) maxOffsetX, -(int) maxOffsetY,
                            (int) maxOffsetY, (int) Utils.dp2px(50), (int) Utils.dp2px(50));
                    //刷新
//                    for (int i = 10; i < 100; i+=10) {
//                        postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                refresh();
//                            }
//                        },i);
//                    }

                    //下一帧
                    postOnAnimation(new Runnable() {
                        @Override
                        public void run() {
                            //scroller.computeScrollOffset()的返回值 判断动画是否还在进行中
                            if (scroller.computeScrollOffset()) {

                                offsetX = scroller.getCurrX();
                                offsetY = scroller.getCurrY();
                                invalidate();
                                postOnAnimation(this);

                            }
                        }
                    });

                }
                return false;
            }


            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // ⽤户单击时被调⽤
                // 和 onSingltTapUp() 的区别在于，⽤户的⼀次点击不会⽴即调⽤这个⽅法，⽽是在⼀定时间后（300ms），确认⽤户没有进⾏双击，这个⽅法才会被调⽤
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // ⽤户双击时被调⽤
                // 注意：第⼆次触摸到屏幕时就调⽤，⽽不是抬起时
                big = !big;
                if (big) {
                    //双击额外放大
                    offsetX=(e.getX()-getWidth()/2)*(1-bigScale/smallScale);
                    offsetY=(e.getY()-getHeight()/2)*(1-bigScale/smallScale);
                    fixOffsets();
                    //双击开启动画
                    getscaleAnimator().start();
                } else {
                    //双击恢复原样
                    getscaleAnimator().reverse();
                }

                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                // ⽤户双击第⼆次按下时、第⼆次按下后移动时、第⼆次按下后抬起时都会被调⽤
                // 常⽤于「双击拖拽」的场景
                //与onDoubleTap区别：当 down后，后续的 up。move，都会执行调用（用这个  用户点击后  屏幕的图片会反复的变大变小）
                return false;
            }

        });


    }

    private void fixOffsets() {
        offsetX = Math.max(Math.min(offsetX, maxOffsetX), -maxOffsetX);
        offsetY = Math.max(Math.min(offsetY, maxOffsetY), -maxOffsetY);
    }

//    private void refresh() {
//        scroller.computeScrollOffset();
//        offsetX= scroller.getCurrX();
//        offsetY=scroller.getCurrY();
//        invalidate();
//    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();

    }

    private ObjectAnimator getscaleAnimator() {
        if (scalenAnimator == null) {
            scalenAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale);
        }
        scalenAnimator.setFloatValues(smallScale,bigScale);
        return scalenAnimator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //将图片偏移到屏幕中心（屏幕宽度-图片宽度）/2 x轴偏移的距离  y轴同理
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        //图片的宽高比 比 view的宽高比  大（宽的是小的  高是大的）
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            //(反之   同理  则 宽是大的，高是小的)
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale=smallScale;
        maxOffsetX = (bitmap.getWidth() * bigScale - getWidth()) / 2;
        maxOffsetY = (bitmap.getHeight() * bigScale - getHeight()) / 2;
    }

    //外挂 更改侦测算法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = scaleDetetor.onTouchEvent(event);
        if (!scaleDetetor.isInProgress()){
            b= detector.onTouchEvent(event);
        }
        return b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scaleFraction=(currentScale-smallScale)/(bigScale-smallScale);
        //放大之后 在偏移
        canvas.translate(offsetX*scaleFraction, offsetY*scaleFraction);
        //缩放
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        //获取图片并放置屏幕中心
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);


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


    private class HenScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        private float initialCurrentScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale=initialCurrentScale*detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            //标识是否要消费这个
            initialCurrentScale=currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
           //放缩结束  该重置的重置 恢复的恢复
        }
    }
}

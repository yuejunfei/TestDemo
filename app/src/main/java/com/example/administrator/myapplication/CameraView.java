package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class CameraView  extends View {
    private static final float IMAGE_WIDTH = Utils.dp2px(200);
    private static final float IMAGE_OFFSET = Utils.dp2px(100);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    //三维（类似灯光的那个点，假想的相机）
    Camera camera = new Camera();

    float topFilp=0;
    float bottomFilp=0;
    float flipRotation=0;




    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);

        //移动假想的相机（灯光点）   z轴的  单位不是像素不是dp  是英寸（skia设计的）1英寸=72像素 默认是-8*72=576
        //4 * Resources.getSystem().getDisplayMetrics().density 目的是不管像素多大或者多小的手机显示出来效果一样
        //像素越大的手机，z轴的假想的相机就里的越远，糊脸的效果就越强
        camera.setLocation(0, 0, Utils.getZForCamera()); // -8 * 72
    }

    public float getTopFilp() {
        return topFilp;
    }

    public void setTopFilp(float topFilp) {
        this.topFilp = topFilp;
        invalidate();
    }

    public float getBottomFilp() {
        return bottomFilp;
    }

    public void setBottomFilp(float bottomFilp) {
        this.bottomFilp = bottomFilp;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //上半部分

        canvas.save();
        canvas.translate(IMAGE_OFFSET + IMAGE_WIDTH / 2f, IMAGE_OFFSET + IMAGE_WIDTH / 2f);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(topFilp);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(- IMAGE_WIDTH, - IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);
        canvas.translate(- (IMAGE_OFFSET + IMAGE_WIDTH / 2f), - (IMAGE_OFFSET + IMAGE_WIDTH / 2f));
        canvas.drawBitmap(bitmap, IMAGE_OFFSET, IMAGE_OFFSET, paint);
        canvas.restore();

        //下半部分

        //裁切后要记得保存
        canvas.save();
        //(canvas使用translate将canvas移出来，先移出来负的，在移进去正的。但是代码得反着写，先写正的，在写负的)
        //在使用后用translate将canvas移回来
        canvas.translate(IMAGE_OFFSET + IMAGE_WIDTH / 2f, IMAGE_OFFSET + IMAGE_WIDTH / 2f);
        //将二维转回来
        canvas.rotate(-flipRotation);
        //先保存
        camera.save();
        //三维转
        camera.rotateX(bottomFilp);
        //把三维应用到canvas中去
        camera.applyToCanvas(canvas);
        //在恢复
        camera.restore();
        //裁切 （不能比原图的尺寸小，要更大。）
        canvas.clipRect(- IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        //二维旋转
        canvas.rotate(flipRotation);
        //在使用之前用translate先将canvas移起来
        canvas.translate(- (IMAGE_OFFSET + IMAGE_WIDTH / 2f), - (IMAGE_OFFSET + IMAGE_WIDTH / 2f));

        canvas.drawBitmap(bitmap, IMAGE_OFFSET, IMAGE_OFFSET, paint);
        canvas.restore();
    }
}

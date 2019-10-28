package com.example.administrator.myapplication.custom;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.Utils;

public class AvatarView extends View {

    private static final float WIDTH=Utils.dp2px(300);
    private static final float OFFSET=Utils.dp2px(20);


    Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Xfermode xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    RectF bounds=new RectF();


    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap=getAvatar((int)WIDTH);
        bounds.set(OFFSET,OFFSET,OFFSET+WIDTH,OFFSET+WIDTH);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画黑圈
        canvas.drawCircle(OFFSET+WIDTH/2f,OFFSET+WIDTH/2f,WIDTH/2f+Utils.dp2px(10),paint);

        //Canvs.saveLayer() 把绘制区域拉到单独的离屏缓冲⾥
        int saveLayer = canvas.saveLayer(bounds, paint);
        //绘制离屏缓冲的绘制区域
        canvas.drawCircle(OFFSET+WIDTH/2f,OFFSET+WIDTH/2f,WIDTH/2f,paint);
        //⽤ Paint.setXfermode() 设置 Xfermode
        paint.setXfermode(xfermode);
        //绘制图片
        canvas.drawBitmap(bitmap,OFFSET,OFFSET,paint);
        //恢复Xfermode
        paint.setXfermode(null);
        //⽤ Canvas.restoreToCount() 把离屏缓冲中的合成后的图形放回绘制区域
        canvas.restoreToCount(saveLayer);



    }

    Bitmap getAvatar(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.wechatshare,options);
        options.inJustDecodeBounds=false;
        options.inDensity=options.outWidth;
        options.inTargetDensity=width;
        return BitmapFactory.decodeResource(getResources(),R.mipmap.wechatshare,options);
    }

}

package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.utils.Utils;

public class ProvinceView extends View {
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

    String province="北京市";



    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(80));
        paint.setTextAlign(Paint.Align.CENTER);
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(province,getWidth()/2f,getHeight()/2f,paint);
    }

}

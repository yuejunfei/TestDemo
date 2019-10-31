package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.myapplication.utils.Utils;

public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void layout(int l, int t, int r, int b) {
//        int width=r-l;
//        int height=b-t;
//        int size = Math.min(width, height);
//        super.layout(l, t, size+l, size+t);
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int size = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(size,size);
    }
}

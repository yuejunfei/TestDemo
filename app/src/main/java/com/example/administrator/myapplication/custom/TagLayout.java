package com.example.administrator.myapplication.custom;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    List<Rect> childrenBounds=new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthUsed=0;
        int heightUsed=0;
        int lineWidthUsed =0;
        int lineHeight = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,heightUsed);

            if (widthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.getMeasuredWidth() > widthSize) {
                lineWidthUsed = 0;
                heightUsed += lineHeight;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            //子view
            Rect childBounds;
            if (childrenBounds.size()<=1){
                childBounds  = new Rect();
                childrenBounds.add(childBounds);
            }else {
                childBounds=childrenBounds.get(i);
            }
            childBounds.set(widthUsed,heightUsed,child.getMeasuredWidth()+widthUsed,child.getMeasuredHeight()+heightUsed);
            //记录下子view的宽用了多少
            widthUsed+=child.getMeasuredWidth();
            widthUsed = Math.max(lineWidthUsed, widthUsed);
            //记录每一个子view de 最高高度（行高）
            lineHeight=Math.max(lineHeight,child.getMeasuredHeight());
        }

        int measuredWidth = widthUsed;
        heightUsed += lineHeight;
        int measuredHeight = heightUsed;
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Rect childBounds  = childrenBounds.get(i);
            childAt.layout(childBounds.left,childBounds.top,childBounds.right,childBounds.bottom);

        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}

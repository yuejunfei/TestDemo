package com.example.administrator.myapplication.custom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.utils.Utils;

public class MaterialEditText extends android.support.v7.widget.AppCompatEditText {

    private static final float TEXT_SIZE = Utils.dp2px(12);
    private static final float TEXT_MARGIN = Utils.dp2px(8);
    private static final float VERTICAL_OFFSET = Utils.dp2px(22);
    private static final float HORIZONTAL_OFFSET = Utils.dp2px(5);
    private static final float EXTRA_OFFSET = Utils.dp2px(8);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    boolean floatingLabelShown;
    float floatingLabelFraction;
    //开关（是否设置padding）
    boolean useFloatingLabel;
    ObjectAnimator animator;
    //根据尺寸的矩形的左高右底
    Rect backgroundPaddings = new Rect();

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //用xml控制开关用这个方法（需要在xml文件里设置 app:bbb="false"，然后新建attrs
    // <declare-styleable name="MaterialEditText">
    //  <attr name="bbb" format="boolean"/>
    //    </declare-styleable>）
    private void init(Context context, AttributeSet attrs) {
        //过滤 R.styleable.MaterialEditText所在 attrs里 那个控件的属性列表
        //R.styleable.MaterialEditText可以这样写new int[]{android.R.attr.colorAccent, android.R.attr
        // .layout_width, R.attr.useFloatingLabel}来获取里面的所有的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        //获取列表中 属性为MaterialEditText_bbb 这个属性的值（通过序号 来取值）
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_bbb, true);
        //typedArray不会自动回收，所以要调用自动回收的方法
        typedArray.recycle();


        //        //刚进来就强制判断一下 根据开关是否 设置padding
        refreshPadding();
        paint.setTextSize(TEXT_SIZE);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 内容为空的话  写文字时设置显示动画
                if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                    floatingLabelShown = true;
                    getAnimator().start();
                } else if (floatingLabelShown && TextUtils.isEmpty(s)) {
                    //内容不为空的话  删除的话设置隐藏动画
                    floatingLabelShown = false;
                    getAnimator().reverse();
                }
            }
        });
    }


    //用java代码控制用这个
//    {
//
//        //刚进来就强制判断一下 根据开关是否 设置padding
//        refreshPadding();
//
//        paint.setTextSize(TEXT_SIZE);
//
//        addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // 内容为空的话  写文字时设置显示动画
//                if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
//                    floatingLabelShown = true;
//                    getAnimator().start();
//                } else if (floatingLabelShown && TextUtils.isEmpty(s)) {
//                    //内容不为空的话  删除的话设置隐藏动画
//                    floatingLabelShown = false;
//                    getAnimator().reverse();
//                }
//            }
//        });
//
//    }


    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 1);
        }
        return animator;
    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    //其他类 调用调用这个方法
    public void setUseFloatingLabel(boolean useFloatingLabel) {
        //判断开关变化了吗
        if (this.useFloatingLabel != useFloatingLabel) {
            //要是开关变化了就给开关设置你给他赋予的值
            this.useFloatingLabel = useFloatingLabel;
            //根据开关判断是否设置padding
            refreshPadding();

        }
    }


    private void refreshPadding() {
        //获取矩形
        getBackground().getPadding(backgroundPaddings);
        if (useFloatingLabel) {
            //设置矩形的左高右低 (根据backgroundPaddings获取)  这样的话 值就是固定的值
            setPadding(backgroundPaddings.left, (int) (backgroundPaddings.top + TEXT_SIZE +
                    TEXT_MARGIN), backgroundPaddings.right, backgroundPaddings.bottom);
        } else {
            //（必须得用-  不能删掉- TEXT_SIZE - TEXT_MARGIN）现在用矩形的固定高度 就不用- TEXT_SIZE - TEXT_MARGIN，直接删掉
            setPadding(backgroundPaddings.left, backgroundPaddings.top, backgroundPaddings.right,
                    backgroundPaddings.bottom);
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //开关是开的情况下  去drawtext
        if (useFloatingLabel) {
            paint.setAlpha((int) (floatingLabelFraction * 0xff));
            float extraOffset = -EXTRA_OFFSET * floatingLabelFraction;
            canvas.drawText(getHint().toString(), HORIZONTAL_OFFSET, VERTICAL_OFFSET +
                    extraOffset, paint);
        }

    }

}

package com.example.administrator.myapplication.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.transition.TransitionManager;
import android.view.View;

import com.example.administrator.myapplication.R;

public class ConstraintAnimation extends Activity {

    private ConstraintLayout constraintLayout;
    //ConstraintSet这个 API 将记住我们在 XML 文件里实现的所有的 constraints。
    private ConstraintSet applyConstraintSet = new ConstraintSet();
    private ConstraintSet resetConstraintSet = new ConstraintSet();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_animation);

        constraintLayout = (ConstraintLayout) findViewById(R.id.main);
        resetConstraintSet.clone(constraintLayout);
        applyConstraintSet.clone(constraintLayout);

    }

    public void onApplyClick(View view) {
        TransitionManager.beginDelayedTransition(constraintLayout);

        //新需求:1
        //我想要让按钮 1 动起来，当用户点击启动按钮的时候，让它与父控件的左边对齐。

        //SetMargin() 方法将使用三个参数(viewId, anchor, margin)。当用户点击之后我会把间距改变到 8px
        //applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,8);

        //新需求:2
        //当用户点击应用按钮的时候，我想要让所有的按钮动起来并在父容器里水平居中。

        //给这些按钮设置了不同的外边距，这导致了我们点击应用按钮时他们将移动到中心，但是由于外边距的设定，它们最终的位置出现了偏移。这里我把所有按钮的左右外边距都设置为 0
//        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.START,0);
//        applyConstraintSet.setMargin(R.id.button1,ConstraintSet.END,0);
//        applyConstraintSet.setMargin(R.id.button2,ConstraintSet.START,0);
//        applyConstraintSet.setMargin(R.id.button2,ConstraintSet.END,0);
//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.START,0);
//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.END,0);
//
//
//        applyConstraintSet.centerHorizontally(R.id.button1, R.id.main);
//        applyConstraintSet.centerHorizontally(R.id.button2, R.id.main);
//        applyConstraintSet.centerHorizontally(R.id.button3, R.id.main);

        //新需求:3
        //用户的语言： 当用户点击应用按钮的时候，我想让按钮 3 动起来，然后移动到正中心。

//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.START,0);
//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.END,0);
//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.TOP,0);
//        applyConstraintSet.setMargin(R.id.button3,ConstraintSet.BOTTOM,0);
//
//        applyConstraintSet.centerHorizontally(R.id.button3, R.id.main);
//        applyConstraintSet.centerVertically(R.id.button3, R.id.main);

        //新需求:4
        //用户语言： 当用户点击应用按钮的时候，我想要让所有的按钮的宽度都变化成 600 像素。

//        applyConstraintSet.constrainWidth(R.id.button1,600);
//        applyConstraintSet.constrainWidth(R.id.button2,600);
//        applyConstraintSet.constrainWidth(R.id.button3,600);

        // applyConstraintSet.constrainHeight(R.id.button1,100);
        // applyConstraintSet.constrainHeight(R.id.button2,100);
        // applyConstraintSet.constrainHeight(R.id.button3,100);


        //新需求:5
        //当用户点击应用按钮的时候，我想要让按钮1的宽度和高度充满整个屏幕并且让其他的视图隐藏。
        //setVisibility:我觉得这个很简单。
        //
        //clear: 我想要把 view 上的所有 constraint 都清除掉。
        //
        //connect: 我想要 view 上添加 constraint。这个方法需要5个参数。
        //
        //第一个:我想要在上面添加 constraint 的 view。
        //
        //第二个：我准备添加的 constraint 的边缘状态。
        //
        //第三个：constraint 的第一个 view，它被用来作为我的锚点。
        //
        //第四个：我的锚点 view 的边缘状态。
        //
        //第五：外边距
//        applyConstraintSet.setVisibility(R.id.button2,ConstraintSet.GONE);
//        applyConstraintSet.setVisibility(R.id.button3,ConstraintSet.GONE);
//        applyConstraintSet.clear(R.id.button1);
//        applyConstraintSet.connect(R.id.button1,ConstraintSet.LEFT,R.id.main,ConstraintSet
// .LEFT,0);
//        applyConstraintSet.connect(R.id.button1,ConstraintSet.RIGHT,R.id.main,ConstraintSet
// .RIGHT,0);
//        applyConstraintSet.connect(R.id.button1,ConstraintSet.TOP,R.id.main,ConstraintSet.TOP,0);
//        applyConstraintSet.connect(R.id.button1,ConstraintSet.BOTTOM,R.id.main,ConstraintSet
// .BOTTOM,0);


        //新需求:6
        //当用户点击应用按钮的时候，我想要让所有的按钮都与屏幕的顶端对齐并且水平居中。
        applyConstraintSet.clear(R.id.button1);
        applyConstraintSet.clear(R.id.button2);
        applyConstraintSet.clear(R.id.button3);

        // chain 风格。(CHAIN_PACKED,CHAIN_SPREAD,CHAIN_SPREAD_INSIDE)
        applyConstraintSet.createHorizontalChain(R.id.main, ConstraintSet.LEFT, R.id.main,
                ConstraintSet.RIGHT, new int[]{R.id.button1, R.id.button2, R.id.button3}, null,
                ConstraintWidget.CHAIN_PACKED);

        //展示带有偏差值的 CHAIN_PACKED
        //新方法 setHorizontalBias()，在这个方法里我填入了我们 chain 组的头部和 float 类型的偏差值。
        applyConstraintSet.setHorizontalBias(R.id.button1, 0.1f);

        applyConstraintSet.constrainWidth(R.id.button1, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button2, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainWidth(R.id.button3, ConstraintSet.WRAP_CONTENT);

        applyConstraintSet.constrainHeight(R.id.button1, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button2, ConstraintSet.WRAP_CONTENT);
        applyConstraintSet.constrainHeight(R.id.button3, ConstraintSet.WRAP_CONTENT);

        applyConstraintSet.applyTo(constraintLayout);
    }

    public void onResetClick(View view) {
        resetConstraintSet.applyTo(constraintLayout);
    }

}

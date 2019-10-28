package com.example.administrator.myapplication;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.custom.CameraView;

import java.util.Arrays;
import java.util.List;

public class AnimatorActivity extends AppCompatActivity {

    public static List<String> provinces= Arrays.asList("北京市","天津市","上海市","山西省","陕西省");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animatoractivity);

//        ImageView imageView = (ImageView) findViewById(R.id.im_view);
//        //局限性特别大，只适用于属性自带的那几个，不能用于自定义动画
//        imageView.animate()
//                //平移
//                .translationX(Utils.dp2px(200))
//                .translationY(Utils.dp2px(200))
//                //旋转
//                .rotation(180)
//                .scaleX(2)
//                .scaleY(2)
//                //透明
//                .alpha(0.5f)
//                //设置持续时间；
//                .setDuration(2000)
//                .start();



//        CircleView circleView = (CircleView) findViewById(R.id.circleview);
//        //对单个动画使用
//        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(circleView,"radius",Utils.dp2px(150));
//        //延迟俩秒
//        objectAnimator.setStartDelay(2000);
//        objectAnimator.start();



        //对多个动画使用 做大
        CameraView cameraView = (CameraView) findViewById(R.id.cameraview);
//        ObjectAnimator topFilpAnimator=ObjectAnimator.ofFloat(cameraView,"topFilp",-45);
//        topFilpAnimator.setDuration(1000);
//        topFilpAnimator.setStartDelay(1000);
//
//        ObjectAnimator bottomFilpAnimator=ObjectAnimator.ofFloat(cameraView,"bottomFilp",45);
//        bottomFilpAnimator.setDuration(1000);
//        bottomFilpAnimator.setStartDelay(1000);
//
//        ObjectAnimator filpRotationAnimator=ObjectAnimator.ofFloat(cameraView,"flipRotation",270);
//        filpRotationAnimator.setDuration(1000);
//        filpRotationAnimator.setStartDelay(1000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(bottomFilpAnimator,filpRotationAnimator,topFilpAnimator);
//        animatorSet.setStartDelay(1000);
//        animatorSet.start();

        //做细，一个动画拆分
        PropertyValuesHolder topFileHolder=PropertyValuesHolder.ofFloat("topFilp",-45);
        PropertyValuesHolder bottomFileHolder=PropertyValuesHolder.ofFloat("bottomFilp",45);
        PropertyValuesHolder filpRotationHolder=PropertyValuesHolder.ofFloat("flipRotation",270);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, topFileHolder, bottomFileHolder, filpRotationHolder);
        objectAnimator.setDuration(1000);
        objectAnimator.setStartDelay(1000);
        objectAnimator.start();

//        ImageView imageView = (ImageView) findViewById(R.id.im_view);
//        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imageView,"translationX",Utils.dp2px(150));
//        //延迟俩秒
//        objectAnimator.setStartDelay(2000);
//        objectAnimator.setDuration(2000);
//        objectAnimator.start();


//        ImageView imageView = (ImageView) findViewById(R.id.im_view);
//        float length = Utils.dp2px(300);
//        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
//        Keyframe keyframe2 =Keyframe.ofFloat(0.1f,0.4f*length);
//        Keyframe keyframe3 =Keyframe.ofFloat(0.9f,0.6f*length);
//        Keyframe keyframe4 =Keyframe.ofFloat(1f,1f*length);
//        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, valuesHolder);
//        objectAnimator.setStartDelay(2000);
//        objectAnimator.setDuration(2000);
//        objectAnimator.start();



//        PointView pointView = (PointView) findViewById(R.id.pointview);
//        PointF pointF=new PointF(Utils.dp2px(300),Utils.dp2px(200));
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(pointView, "point", new PointFEvaluator(),pointF);
//        objectAnimator.setStartDelay(2000);
//        objectAnimator.setDuration(2000);
//        objectAnimator.start();



//        ProvinceView provinceView = (ProvinceView) findViewById(R.id.province);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(provinceView, "province", new ProvinceEvaluator(), "陕西省");
//        objectAnimator.setStartDelay(2000);
//        objectAnimator.setDuration(5000);
//        objectAnimator.start();
    }

    static class PointFEvaluator implements TypeEvaluator<PointF>{
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = startValue.x + (endValue.x - startValue.x) * fraction;
            float y = startValue.y + (endValue.y - startValue.y) * fraction;
            return new PointF(x,y);
        }
    }


    static class ProvinceEvaluator implements TypeEvaluator<String>{

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int i = provinces.indexOf(startValue);
            int j = provinces.indexOf(endValue);
            int currentIndex= (int) (i+(j-i)*fraction);
            return provinces.get(currentIndex);
        }
    }


}

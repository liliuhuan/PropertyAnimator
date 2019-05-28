package com.app.suit.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * @Author: 李刘欢
 * @CreateDate: 2019/5/27 12:15
 * @Version: 1.0.0
 * @Description:
 *
 * 插值器 ： https://blog.csdn.net/qq_15128547/article/details/50779964
 * 估值器 ： https://cloud.tencent.com/developer/article/1334680
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String ALPHA = "alpha";
    private final static String ROTATION = "rotation";
    private final static String TRANSLATION_X = "translationX";
    private final static String TRANSLATION_Y = "translationY";
    private final static String SCALE_X = "scaleX";
    private final static String SCALE_Y = "scaleY";
    private final static String BG_COLOR = "backgroundColor";
    private final static String TEXT_COLOR = "textColor";
    private final static int DURACATION = 2000;
    private View targetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetView = findViewById(R.id.text_view);

        findViewById(R.id.xml).setOnClickListener(this);
        findViewById(R.id.java_set).setOnClickListener(this);
        findViewById(R.id.view_property).setOnClickListener(this);
        findViewById(R.id.evaluator).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xml:
                testXml();
                break;
            case R.id.java_set:
                testJava();
                break;
            case R.id.view_property:
                testViewProperty();
                break;
            case R.id.evaluator:
                /**
                 *  { @link #loadPointAnimator()}
                 */
                loadColorAnimator(TEXT_COLOR,
                        getResources().getColor(R.color.color_000000),
                        getResources().getColor(R.color.color_FF5932));
                break;
            default:
        }
    }

    private void testViewProperty() {
        ViewPropertyAnimator animate = targetView.animate();
        animate.alpha(0);
        animate.scaleX(2f).scaleY(2f);
        animate.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        animate.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animate.setDuration(DURACATION);
        animate.rotation(360f);
    }

    private void testJava() {
        Animator alpha = loadAnimator(ALPHA, 0f, 1f);
        Animator rotation = loadAnimator(ROTATION, 0f, 360f);
        Animator translationX = loadAnimator(TRANSLATION_X, 0f);
        Animator translationY = loadAnimator(TRANSLATION_Y, 0f);
        Animator scaleX = loadAnimator(SCALE_X, 2f);
        Animator scaleY = loadAnimator(SCALE_Y, 2f);
        Animator colorAnimator = loadColorAnimator(TEXT_COLOR, "#000000", "#FF5932", "#008577");

        playAnimators(rotation, translationX, translationY, scaleX, scaleY, colorAnimator);
    }

    /**
     * animatorSet.playSequentially(animators);{顺序执行}
     *
     * @param animators
     */
    private void playAnimators(Animator... animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);
        animatorSet.setDuration(DURACATION);
        animatorSet.setTarget(targetView);
        animatorSet.start();
    }

    private void testXml() {
        Animator xmlAnimator = loadXmlAnimator(R.animator.alpha);
        xmlAnimator.setTarget(targetView);
        xmlAnimator.start();
    }

    private Animator loadXmlAnimator(@AnimatorRes int id) {
        return AnimatorInflater.loadAnimator(this, id);
    }

    private Animator loadAnimator(@NonNull String propertyName, @NonNull float... values) {
        ObjectAnimator animator = createObjectAnimator(propertyName);
        animator.setFloatValues(values);
        return animator;
    }

    /**
     * ObjectAnimator.ofObject(targetView, ALPHA, new FloatEvaluator(), 0F, 1F)
     *
     * @param propertyName
     * @param values
     * @return
     */
    private ObjectAnimator loadColorAnimator(@NonNull String propertyName, @NonNull Object... values) {
        ObjectAnimator animator = createObjectAnimator(propertyName);
        animator.setObjectValues(values);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(DURACATION);
        animator.setTarget(targetView);
        animator.start();
        return animator;
    }

    private Animator loadPointAnimator() {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setObjectValues(new Point(0, 0), new Point(200, 200));
        animator.setEvaluator(new PointEvaluator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                targetView.setTranslationX(point.getX());
                targetView.setTranslationY(point.getY());
            }
        });
        animator.setDuration(DURACATION);
        animator.start();
        return animator;
    }

    private ObjectAnimator createObjectAnimator(String propertyName) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(DURACATION);
        animator.setPropertyName(propertyName);
        animator.setRepeatCount(-1);
        return animator;
    }
}

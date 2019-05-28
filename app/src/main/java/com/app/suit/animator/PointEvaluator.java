package com.app.suit.animator;

import android.animation.TypeEvaluator;

/**
 * @author: 李刘欢
 * @date：2019/5/27 15:36
 * @version:1.0.0
 * @description: PointEvaluator 估值器 ： https://cloud.tencent.com/developer/article/1334680
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
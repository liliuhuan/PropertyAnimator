package com.app.suit.animator;

/**
 * @author: 李刘欢
 * @date：2019/5/27 15:37
 * @version:1.0.0
 * @description: Point
 */
public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "point:" + "x==" + x + ", y===" + y;
    }
}

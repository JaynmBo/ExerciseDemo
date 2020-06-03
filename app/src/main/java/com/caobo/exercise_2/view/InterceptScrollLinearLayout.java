package com.caobo.exercise_2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 自定义一个布局
 * 用于拦截onTouch事件
 */

public class InterceptScrollLinearLayout extends LinearLayout {

    public InterceptScrollLinearLayout(Context context) {
        super(context);
    }

    public InterceptScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InterceptScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("caobocaobo","InterceptScrollLinearLayout onTouchEvent");
        return super.onTouchEvent(event);
    }
}

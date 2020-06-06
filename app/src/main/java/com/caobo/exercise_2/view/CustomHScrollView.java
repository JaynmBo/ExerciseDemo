package com.caobo.exercise_2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义横向滚动控件
 * 重载了 onScrollChanged方法，监听每次的变化
 */

public class CustomHScrollView extends HorizontalScrollView {

    ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

    public CustomHScrollView(Context context) {
        super(context);
    }

    public CustomHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //滚动时通知
        if (mScrollViewObserver != null) {
            mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        Log.e("caobocaobo","CustomHScrollView onInterceptTouchEvent");
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("caobocaobo","CustomHScrollView onTouchEvent");
        return super.onTouchEvent(event);
    }

    /*
     * 当发生了滚动事件时接口，供外部访问
     */
    public static interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    /*
     * 添加滚动事件监听
     * */
    public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.AddOnScrollChangedListener(listener);
    }

    /*
     * 移除滚动事件监听
     * */
    public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
        mScrollViewObserver.RemoveOnScrollChangedListener(listener);
    }
    /*
     * 滚动观察者
     */
    public static class ScrollViewObserver {
        List<OnScrollChangedListener> mChangedListeners;

        public ScrollViewObserver() {
            super();
            mChangedListeners = new ArrayList<OnScrollChangedListener>();
        }
        //添加滚动事件监听
        public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
            mChangedListeners.add(listener);
        }
        //移除滚动事件监听
        public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
            mChangedListeners.remove(listener);
        }
        //回调
        public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (mChangedListeners == null || mChangedListeners.size() == 0) {
                return;
            }
            for (int i = 0; i < mChangedListeners.size(); i++) {
                if (mChangedListeners.get(i) != null) {
                    mChangedListeners.get(i).onScrollChanged(l, t, oldl, oldt);
                }
            }
        }
    }


}

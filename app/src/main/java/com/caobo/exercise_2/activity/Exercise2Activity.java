package com.caobo.exercise_2.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.caobo.MainActivity;
import com.caobo.exercise_1.R;
import com.caobo.exercise_2.adapter.ListViewAdapter;
import com.caobo.exercise_2.model.TestData;
import com.caobo.exercise_2.view.CustomHScrollView;
import com.caobo.exercise_2.view.InterceptScrollLinearLayout;
import com.caobo.exercise_2.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Exercise2Activity extends AppCompatActivity {

    private MyRecyclerView recycler;
    //标题头
    private InterceptScrollLinearLayout mHead;
    private List<TestData> mDataList;
    private ListViewAdapter mAdapter;
    //用于记录CustomHScrollView的初始位置
    private CustomHScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);
        setTitle(getIntent().getCharSequenceExtra(MainActivity.INTENT_TITLE));
        initView();
        initData();
    }

    private void initView(){
        recycler = findViewById(R.id.recycler);
        mScrollView = findViewById(R.id.h_scrollView);
        mHead = findViewById(R.id.head_layout);
        mHead.setBackgroundResource(R.color.gray);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setOnTouchListener(new MyTouchLinstener());
        mHead.setOnTouchListener(new MyTouchLinstener());
    }

    /**
     * 加载数据
     */
    private void initData(){
        mDataList = new ArrayList<>();
        TestData data = null;
        Random random = new Random(1);
        for (int i = 1; i <= 30; i++) {
            data = new TestData();
            data.setText1(String.valueOf(1));
            data.setText2(String.valueOf(2));
            data.setText3(String.valueOf(3));
            data.setText4(String.valueOf(4));
            data.setText5(String.valueOf(5));
            data.setText6(String.valueOf(6));
            data.setText7(String.valueOf(7));
            data.setText8(String.valueOf(8));
            data.setText9(String.valueOf(9));
            data.setText10(String.valueOf(10));
            mDataList.add(data);
        }
        setData();
    }

    private void setData(){
        mAdapter = new ListViewAdapter(mDataList, mHead);
        recycler.setAdapter(mAdapter);
    }

    class MyTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            //当在表头和listView控件上touch时，将事件分发给 ScrollView
            mScrollView.onTouchEvent(arg1);
            return false;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}

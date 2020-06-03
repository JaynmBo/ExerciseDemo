package com.caobo.exercise_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caobo.exercise_1.R;
import com.caobo.exercise_2.model.TestData;
import com.caobo.exercise_2.view.CustomHScrollView;
import com.caobo.exercise_2.view.InterceptScrollLinearLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>  {
    //记录初始位置
    private int leftPos;
    private int topPos;
    private List<TestData> mList;
    private InterceptScrollLinearLayout mHead;
    //标记变量
    private int n = 1;

    public ListViewAdapter(List<TestData> list, InterceptScrollLinearLayout head) {
        this.mList = list;
        this.mHead = head;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView1.setText(mList.get(position).getText1());
        holder.mTextView2.setText(mList.get(position).getText2());
        holder.mTextView3.setText(mList.get(position).getText3());
        holder.mTextView4.setText(mList.get(position).getText4());
        holder.mTextView5.setText(mList.get(position).getText5());
        holder.mTextView6.setText(mList.get(position).getText6());
        holder.mTextView7.setText(mList.get(position).getText7());
        holder.mTextView8.setText(mList.get(position).getText8());
        holder.mTextView9.setText(mList.get(position).getText9());
        holder.mTextView10.setText(mList.get(position).getText10());
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 记录CustomHScrollView的初始位置
     * @param l
     * @param t
     */
    public void setPosData(int l, int t){
        this.leftPos = l;
        this.topPos = t;
    }


    class OnScrollChangedListenerImp implements CustomHScrollView.OnScrollChangedListener {
        CustomHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(CustomHScrollView scrollViewar) {
            mScrollViewArg = scrollViewar;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l, t);
            if (n == 1) {//记录滚动的起始位置，避免因刷新数据引起错乱
                setPosData(oldl, oldt);
            }
            n++;
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        TextView mTextView5;
        TextView mTextView6;
        TextView mTextView7;
        TextView mTextView8;
        TextView mTextView9;
        TextView mTextView10;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.head);
            mTextView1 = itemView.findViewById(R.id.textView_1);
            mTextView2 = itemView.findViewById(R.id.textView_2);
            mTextView3 = itemView.findViewById(R.id.textView_3);
            mTextView4 = itemView.findViewById(R.id.textView_4);
            mTextView5 = itemView.findViewById(R.id.textView_5);
            mTextView6 = itemView.findViewById(R.id.textView_6);
            mTextView7 = itemView.findViewById(R.id.textView_7);
            mTextView8 = itemView.findViewById(R.id.textView_8);
            mTextView9 = itemView.findViewById(R.id.textView_9);
            mTextView10 =itemView.findViewById(R.id.textView_10);
            // item列表中的ScrollView
            CustomHScrollView scrollView = itemView.findViewById(R.id.h_scrollView);

            // Tab栏的ScrollView
            CustomHScrollView headSrcrollView =  mHead.findViewById(R.id.h_scrollView);
            // 订阅
            headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView));
        }
    }
}

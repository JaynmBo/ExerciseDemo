package com.caobo.exercise_1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.caobo.MainActivity;
import com.caobo.exercise_1.R;
import com.caobo.exercise_1.adapter.DataAdapter;
import com.caobo.exercise_1.circle.CircleLayoutManager;
import com.caobo.exercise_1.circle.ScrollHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Exercise1Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CircleLayoutManager viewPagerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);
        setTitle(getIntent().getCharSequenceExtra(MainActivity.INTENT_TITLE));
        recyclerView = findViewById(R.id.recycler);
        viewPagerLayoutManager = new CircleLayoutManager(this);
        DataAdapter dataAdapter = new DataAdapter();
        dataAdapter.setOnItemClickListener(new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(v.getContext(), "clicked:" + pos, Toast.LENGTH_SHORT).show();
                ScrollHelper.smoothScrollToTargetView(recyclerView, v);
            }
        });
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(viewPagerLayoutManager);

    }

}

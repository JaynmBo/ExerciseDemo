package com.caobo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.caobo.exercise_1.R;
import com.caobo.exercise_1.activity.Exercise1Activity;
import com.caobo.exercise_2.activity.Exercise2Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity  extends AppCompatActivity {

    public final static String INTENT_TITLE = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onExercise1(View view) {
        startActivity(Exercise1Activity.class,view);
    }

    public void onExercise2(View view) {
        startActivity(Exercise2Activity.class,view);
    }

    private void startActivity(Class clz, View view) {
        Intent intent = new Intent(this, clz);
        if (view instanceof AppCompatButton) {
            intent.putExtra(INTENT_TITLE, ((AppCompatButton) view).getText());
        }
        startActivity(intent);
    }
}

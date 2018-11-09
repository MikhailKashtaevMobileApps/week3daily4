package com.example.mike.week3daily4.views.random_objects_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mike.week3daily4.R;

public class RandomObjectsActivity extends AppCompatActivity implements RandomObjectsContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_objects);

    }
}

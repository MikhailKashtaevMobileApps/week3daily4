package com.example.mike.week3daily4.views.main_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mike.week3daily4.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        mainPresenter.attachView( this );
        super.onStart();
    }

    @Override
    protected void onStop() {
        mainPresenter.detachView();
        super.onStop();
    }

    public void toggleMusic(View view) {
        mainPresenter.toggleMusic();
    }
}

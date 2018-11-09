package com.example.mike.week3daily4.views.main_activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mike.week3daily4.base.BaseView;
import com.example.mike.week3daily4.services.MusicService;
import com.example.mike.week3daily4.utils.ServiceUtils;

public class MainPresenter implements MainContract.Presenter{

    public static final String TAG = "__TAG__";
    private BaseView activity;
    private Context context;

    public MainPresenter(Context ctx){
        context = ctx;
    }

    @Override
    public void attachView(BaseView v) {
        activity = v;
    }

    @Override
    public void detachView() {
        activity = null;
    }


    @Override
    public void toggleMusic() {
        Log.d(TAG, "toggleMusic: ");
        // if running then stop
        if (isMusicRunning()){
            Intent intent = new Intent(context, MusicService.class );
            intent.setAction(MusicService.ACTION_STOP);
            context.stopService( intent );
        }else{
            // Create service
            Intent intent = new Intent(context, MusicService.class );
            intent.setAction(MusicService.ACTION_PLAY);
            context.startService( intent );
        }
    }

    public boolean isMusicRunning(){
        return ServiceUtils.isMyServiceRunning(context, MusicService.class);
    }
}

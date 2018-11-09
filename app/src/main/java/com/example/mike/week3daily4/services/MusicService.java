package com.example.mike.week3daily4.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mike.week3daily4.R;

import java.io.IOException;

public class MusicService extends Service {

    public static final String ACTION_STOP = "week3daily3.music_service.action.STOP";
    public static final String ACTION_PAUSE = "week3daily3.music_service.action.PAUSE";
    public static final String ACTION_PLAY = "week3daily3.music_service.action.PLAY";
    public static final String ACTION_DELETE = "week3daily3.music_service.action.DELETE";
    public static final String CHANNEL_ID = "MYCHANNELID";
    public static final String TAG = MusicService.class.getSimpleName()+"__TAG__";
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createNotification();
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource( getAssets().openFd( "don_mclean_american_pie.mp3" ) );
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mediaPlayer.release();
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.cancel(0);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: "+intent.getAction());

        if ( intent.getAction() == null ){
            return super.onStartCommand(intent, flags, startId);
        }

        switch (intent.getAction()){
            case ACTION_PLAY:
                mediaPlayer.start();
                break;
            case ACTION_STOP:
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                break;
            case ACTION_PAUSE:
                mediaPlayer.pause();
                break;
            case ACTION_DELETE:
                stopSelf();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Creating a new notification
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotification(){

        // STop button
        Intent stopIntent = new Intent( this, MusicService.class);
        stopIntent.setAction( ACTION_STOP );
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action stopAction = new NotificationCompat.Action(0,"Stop", stopPendingIntent);

        // Pause button
        Intent pauseIntent = new Intent( this, MusicService.class);
        pauseIntent.setAction( ACTION_PAUSE );
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action pauseAction = new NotificationCompat.Action(0,"Pause", pausePendingIntent);

        // Play button
        Intent playIntent = new Intent( this, MusicService.class);
        playIntent.setAction( ACTION_PLAY );
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action playAction = new NotificationCompat.Action(0,"Play", playPendingIntent);

        // Delete Intent
        Intent deleteIntent = new Intent( this, MusicService.class );
        deleteIntent.setAction( ACTION_DELETE );
        PendingIntent deletePendingIntent = PendingIntent.getService( this, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("MusicService")
                .setContentText("American Pie")
                .addAction(stopAction)
                .addAction(pauseAction)
                .addAction(playAction)
                .setDeleteIntent(deletePendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify( 0, mBuilder.build() );
    }

}

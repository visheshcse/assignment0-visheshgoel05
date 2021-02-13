package com.example.helloworld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.IOException;

public class MusicService extends Service {
    NotificationManagerCompat notificationManager;
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MusicService")
                .setContentTitle("Content Title")
                .setContentText("Context Text")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(this);

        String musicServiceCallingActivity = intent.getStringExtra("MusicServiceData");


        if(musicServiceCallingActivity.equals("DownloadMusicPlayActivity")){
            String musicFileName = intent.getStringExtra("MusicFilename");
            try {
                String filePath = getFilesDir() + File.separator + musicFileName;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.s1);
            }
            mediaPlayer.start();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer media) {
                media.start();
            }
        });


        //notificationManager.notify(1, builder.build());
        startForeground(1, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "V";
            String description = "des";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MusicService", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            //mediaPlayer.release();
            mediaPlayer.reset();
            mediaPlayer = null;
        }
        //notificationManager.cancel(1);
    }
}

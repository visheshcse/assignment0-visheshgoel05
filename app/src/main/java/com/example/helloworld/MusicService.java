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

        String musicServiceCallingActivity = intent.getStringExtra("MusicServiceData");
        String musicFilePlayName = intent.getStringExtra("MusicFilePlayname");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MusicService")
                .setContentTitle("Music File Playing")
                .setContentText(musicFilePlayName)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager = NotificationManagerCompat.from(this);

        if(musicServiceCallingActivity != null && "DownloadMusicPlayActivity".equals(musicServiceCallingActivity)){
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
                if(musicFilePlayName != null && musicFilePlayName.equals("Second Song")){
                    mediaPlayer = MediaPlayer.create(this, R.raw.sound2);
                }
                else if(musicFilePlayName != null && musicFilePlayName.equals("Third Song")){
                    mediaPlayer = MediaPlayer.create(this, R.raw.sound3);
                }
                else if(musicFilePlayName != null && musicFilePlayName.equals("Fourth Song")){
                    mediaPlayer = MediaPlayer.create(this, R.raw.sound4);
                }
                else if(musicFilePlayName != null && musicFilePlayName.equals("Fifth Song")){
                    mediaPlayer = MediaPlayer.create(this, R.raw.sound5);
                }
                else {
                    mediaPlayer = MediaPlayer.create(this, R.raw.sound1);
                }
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
            CharSequence name = "Notification";
            String description = "NotificationDescription";
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

package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentHolder,new MusicPlayerFragment()).addToBackStack(null).commit();

        /*Button startService = findViewById(R.id.buttonStartService);
        Button stopService = findViewById(R.id.buttonStopService);
        Button downloadActivity = findViewById(R.id.buttonDownloadActivity);

        Intent intent = new Intent(this, MusicService.class);
        Intent launchDownloadActivityIntent = new Intent(this, DownloadActivity.class);
        startService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startService(intent);
            }
        });


        stopService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopService(intent);
            }
        });

        downloadActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(launchDownloadActivityIntent, 1);
            }
        });*/
    }
}
package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer = new MediaPlayer();
    TextView textViewDownloadStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentDownloadActivityHolder, new DownloadMusicFragment() ).addToBackStack(null).commit();

        /*Button buttonStartDownload = findViewById(R.id.buttonStartDownload);
        Button buttonPlayDownload = findViewById(R.id.buttonPlayDownload);
        Button buttonStopDownload = findViewById(R.id.buttonStopDownload);
        EditText editTextServerName = findViewById(R.id.editTextServerName);
        textViewDownloadStatus = findViewById(R.id.textViewDownloadStatus);
        String[] files = getApplicationContext().fileList();
        //String filePath = "file://" + getFilesDir()+File.separator+"s1.mp3";
        String filePath = getFilesDir()+File.separator+"s1.mp3";

        buttonStartDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadeMusicTask().execute();
            }
        });

        buttonPlayDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buttonPlayDownload.setEnabled(false);
            }
        });

        buttonStopDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                buttonPlayDownload.setEnabled(true);
            }
        });



        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer media) {
                media.start();
            }
        });*/

    }

   /* private class DownloadeMusicTask extends AsyncTask<Void, Void, Void> {

        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewDownloadStatus.setText("Download Started");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textViewDownloadStatus.setText("Download Complete");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                textViewDownloadStatus.setText("Download In Progress");
                URL url = new URL("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                String filename = "s1.mp3";
                InputStream is = connection.getInputStream();
                FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
                byte[] bufferData = new byte[1024];
                int length = 0;
                while ((length = is.read(bufferData)) != -1) {
                    fos.write(bufferData, 0, length);
                }
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                outputFile = null;
                Log.e("ErrorDownload", "Download Error Exception " + e.getMessage());
            }
            return null;
        }
    }*/
}

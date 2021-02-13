package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadMusicFragment extends Fragment {
    MediaPlayer mediaPlayer = new MediaPlayer();
    TextView textViewDownloadStatus;
    EditText editTextMusicServerPath;
    String downloadFilename = "downloadmusic.mp3";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DownloadMusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadMusicFragment newInstance(String param1, String param2) {
        DownloadMusicFragment fragment = new DownloadMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_music, container, false);
        Button buttonStartDownload = (Button) view.findViewById(R.id.buttonStartDownload);
        Button buttonPlayDownload = (Button) view.findViewById(R.id.buttonPlayDownload);
        Button buttonStopDownload = (Button) view.findViewById(R.id.buttonStopDownload);
        editTextMusicServerPath = (EditText) view.findViewById(R.id.editTextServerName);
        textViewDownloadStatus = (TextView) view.findViewById(R.id.textViewDownloadStatus);
        String[] files = getActivity().fileList();
        //String filePath = "file://" + getFilesDir()+File.separator+"s1.mp3";
        String filePath = getActivity().getFilesDir() + File.separator + downloadFilename;

        buttonStartDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadeMusicTask().execute();
            }
        });

        Intent musicServiceDownloadIntent = new Intent(getActivity(), MusicService.class);
        musicServiceDownloadIntent.putExtra("MusicServiceData", "DownloadMusicPlayActivity");
        musicServiceDownloadIntent.putExtra("MusicFilename", downloadFilename);

        buttonPlayDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().startService(musicServiceDownloadIntent);
                /*try {
                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                buttonPlayDownload.setEnabled(false);
            }
        });

        buttonStopDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().stopService(musicServiceDownloadIntent);
                /*try {
                    if (mediaPlayer != null) {
                        mediaPlayer.reset();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                buttonPlayDownload.setEnabled(true);
            }
        });


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer media) {
                media.start();
            }
        });


        return view;
    }

    private class DownloadeMusicTask extends AsyncTask<Void, Void, Void> {

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
                //URL url = new URL("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");
                URL url = new URL(editTextMusicServerPath.getText().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                FileOutputStream fileOutputStream = getActivity().openFileOutput(downloadFilename, Context.MODE_PRIVATE);
                byte[] bufferData = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(bufferData)) != -1) {
                    fileOutputStream.write(bufferData, 0, length);
                }

                fileOutputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                outputFile = null;
                Log.e("Error in Download Files", "Music Download Exception " + e.getMessage());
            }
            return null;
        }
    }
}
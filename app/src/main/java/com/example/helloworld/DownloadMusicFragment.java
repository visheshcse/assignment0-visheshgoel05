package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    Button buttonPlayDownload;
    Button buttonStopDownload;
    Button buttonStartDownload;
    String downloadFilename = "downloadmusic.mp3";
    boolean downloadStatus = true;
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
        buttonStartDownload = (Button) view.findViewById(R.id.buttonStartDownload);
        buttonPlayDownload = (Button) view.findViewById(R.id.buttonPlayDownload);
        buttonStopDownload = (Button) view.findViewById(R.id.buttonStopDownload);
        editTextMusicServerPath = (EditText) view.findViewById(R.id.editTextServerName);
        textViewDownloadStatus = (TextView) view.findViewById(R.id.textViewDownloadStatus);
        buttonPlayDownload.setEnabled(false);
        buttonStopDownload.setEnabled(false);
        String[] files = getActivity().fileList();
        //String filePath = "file://" + getFilesDir()+File.separator+"s1.mp3";
        String filePath = getActivity().getFilesDir() + File.separator + downloadFilename;

        buttonStartDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editTextMusicServerPath.getText() == null || editTextMusicServerPath.getText().toString().length() == 0
                        || editTextMusicServerPath.getText().toString().isEmpty() ||
                        !editTextMusicServerPath.getText().toString().substring(editTextMusicServerPath.getText().toString().lastIndexOf(".")).
                                equals(".mp3")){
                    textViewDownloadStatus.setText("Please Enter Music File Path");
                }
                else if(!checkNetworkStatus()){
                    textViewDownloadStatus.setText("Not Connected to the Internet, Please connect to Internet");
                }
                else{

                    buttonStartDownload.setEnabled(false);
                    new DownloadeMusicTask().execute();
                }
                if(!downloadStatus){
                    buttonPlayDownload.setEnabled(true);
                    buttonStopDownload.setEnabled(true);
                }
            }
        });

        Intent musicServiceDownloadIntent = new Intent(getActivity(), MusicService.class);
        musicServiceDownloadIntent.putExtra("MusicServiceData", "DownloadMusicPlayActivity");
        musicServiceDownloadIntent.putExtra("MusicFilename", downloadFilename);



        buttonPlayDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                musicServiceDownloadIntent.putExtra("MusicFilePlayname",
                        editTextMusicServerPath.getText().toString().
                                substring(editTextMusicServerPath.getText().toString().lastIndexOf("/")+1));
                getActivity().startService(musicServiceDownloadIntent);
                buttonPlayDownload.setEnabled(false);
            }
        });

        buttonStopDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().stopService(musicServiceDownloadIntent);
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

    private boolean checkNetworkStatus() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class DownloadeMusicTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewDownloadStatus.setText("Download Started");
                }
            });
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!downloadStatus){
                        textViewDownloadStatus.setText("Please Enter Correct Music File Path");
                    }
                    else{
                        buttonPlayDownload.setEnabled(true);
                        buttonStopDownload.setEnabled(true);
                        textViewDownloadStatus.setText("Download Complete");
                    }
                    buttonStartDownload.setEnabled(true);

                }
            });
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewDownloadStatus.setText("Download In Progress");
                    }
                });

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
                downloadStatus = false;
                Log.e("Error in Download Files", "Music Download Exception " + e.getMessage());
            }
            return null;
        }
    }
}
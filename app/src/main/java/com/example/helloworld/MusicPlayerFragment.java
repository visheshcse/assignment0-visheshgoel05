package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicPlayerFragment extends Fragment {

    String songSelected = "First Song";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicPlayerFragment newInstance(String param1, String param2) {
        MusicPlayerFragment fragment = new MusicPlayerFragment();
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
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        Button startService = (Button) view.findViewById(R.id.buttonStartService);
        Button stopService = (Button) view.findViewById(R.id.buttonStopService);
        Button downloadActivity = (Button) view.findViewById(R.id.buttonDownloadActivity);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        Intent musicServiceIntent = new Intent(getActivity(), MusicService.class);
        Intent launchDownloadActivityIntent = new Intent(getActivity(), DownloadActivity.class);

        startService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                musicServiceIntent.putExtra("MusicFilePlayname", songSelected);
                getActivity().startService(musicServiceIntent);
                startService.setEnabled(false);
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().stopService(musicServiceIntent);
                startService.setEnabled(true);
            }
        });

        downloadActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(launchDownloadActivityIntent, 1);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.one:
                        songSelected = "First Song";
                        break;
                    case R.id.two:
                        songSelected = "Second Song";
                        break;
                    case R.id.three:
                        songSelected = "Third Song";
                        break;
                    case R.id.four:
                        songSelected = "Fourth Song";
                        break;
                    case R.id.five:
                        songSelected = "Fifth Song";
                        break;
                }
            }
        });
        return view;
    }

    /*public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.one:
                if (checked)
                    songSelected = "First Song";
                    break;
            case R.id.two:
                if (checked)
                    songSelected = "Second Song";
                    break;
            case R.id.three:
                if (checked)
                    songSelected = "Third Song";
                break;
            case R.id.four:
                if (checked)
                    songSelected = "Fourth Song";
                break;
            case R.id.five:
                if (checked)
                    songSelected = "Fifth Song";
                break;
        }
    }*/
}
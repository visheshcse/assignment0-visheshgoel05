package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ScanResult> results;
    private  Button saveWarDrivingData;
    private EditText roomNumber, getLocationK;
    WifiManager wifiManager;
    private BarChart apchart;
    private Button scanWifi, getLocation, imuSensors;
    TextView setStatus;
    String apNames[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getLocationK = (EditText)findViewById(R.id.getLocationK);
            getLocation = (Button) findViewById(R.id.getLocation);
            setStatus = (TextView) findViewById(R.id.statusTextView);
            //imuSensors = (Button) findViewById(R.id.imuSensors);

//            imuSensors.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, IMUActivity.class);
//                }
//            });


            //WifiManager
            wifiManager = (WifiManager)
                    getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            //WifiBroadcastReceiver
            BroadcastReceiver br = new WifiBroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean success = intent.getBooleanExtra(
                            WifiManager.EXTRA_RESULTS_UPDATED, false);
                    if (success) {
                        results = wifiManager.getScanResults();
                    }

                }
            };

            //Registration of Broadcast Receiver
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            getApplicationContext().registerReceiver(br, filter);

            //Display rssi values
            apchart = findViewById(R.id.apChart);
            scanWifi = (Button) findViewById(R.id.scanWifi);
            scanWifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wifiManager.startScan();
                    results = wifiManager.getScanResults();
                    Log.d("Results", results.toString());

                    //Get AP Data
                    //BarData data
                    ArrayList<BarEntry> apData = new ArrayList<BarEntry>();
                    apNames = new String[100];
                    int i = 0;
                    if (results != null) {
                        for (ScanResult result : results) {
                            if (result.SSID != null && !(result.SSID.isEmpty()) && result.SSID.length() != 0) {
                                apNames[i] = result.SSID;
                                Log.d("Resultsi", Integer.toString(i));
                                Log.d("Resultsa", apNames[i]);
                                apData.add(new BarEntry(i, (result.level)));
                                i++;
                            }

                        }
                    }

                    BarDataSet apDataSet = new BarDataSet(apData, "APData");
                    ArrayList<IBarDataSet> apDataSets = new ArrayList<>();
                    apDataSets.add(apDataSet);
                    BarData apdata = new BarData(apDataSets);

                    //Set Chart Appearance
                    setChartAppearance();

                    //Set BarData to graph
                    apdata.setValueTextSize(12f);
                    apchart.setData(apdata);
                    apchart.invalidate();
                }
            });


            //Save War driving Data
            try {
                saveWarDrivingData = (Button) findViewById(R.id.saveWarDrivingData);
                roomNumber = (EditText) findViewById(R.id.roomNumber);
                saveWarDrivingData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setStatus.setText("");
                        if (roomNumber != null && roomNumber.getText() != null && roomNumber.getText().toString() != null && !(roomNumber.getText().toString().isEmpty())) {
                            boolean success = wifiManager.startScan();
                            Log.d("Results", Boolean.toString(success));
                            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                    AppDatabase.class, "Wardriving-Application").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                            WifiApInfoDao wifiApInfoDao = db.wifiApInfoDao();
                            results = wifiManager.getScanResults();
                            Log.d("Results", results.toString());
                            if (results != null) {
                                for (ScanResult result : results) {
                                    if (result.SSID != null && !(result.SSID.isEmpty()) && result.SSID.length() != 0) {
                                        String level = Integer.toString(result.level);
                                        String apName = result.SSID;
                                        WifiAPInfoEntity wifiApInfoEntity = new WifiAPInfoEntity(roomNumber.getText().toString(), apName, level);
                                        wifiApInfoDao.insertAll(wifiApInfoEntity);
                                    }
                                }
                                setStatus.setText("War Driving Done for Room " + roomNumber.getText().toString());
                            }
                        }
                        else{
                            setStatus.setText("Please enter room number");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            getLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getLocationK != null && getLocationK.getText() != null && getLocationK.getText().toString() != null && !(getLocationK.getText().toString().isEmpty())) {
                        boolean success = wifiManager.startScan();
                        Log.d("Results", Boolean.toString(success));
                        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                                AppDatabase.class, "Wardriving-Application").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                        WifiApInfoDao wifiApInfoDao = db.wifiApInfoDao();
                        results = wifiManager.getScanResults();
                        Log.d("Results", results.toString());
                        ArrayList<Integer> location_list = new ArrayList<>();
                        if (results != null) {
                            for (ScanResult result : results) {
                                if (result.SSID != null && !(result.SSID.isEmpty()) && result.SSID.length() != 0) {
                                    String level = Integer.toString(result.level);
                                    String apName = result.SSID;
                                    ArrayList<WifiAPInfoEntity> apData = (ArrayList<WifiAPInfoEntity>) wifiApInfoDao.findApData(apName);

                                    if (apData != null && apData.size() != 0) {
                                        for (int i = 0; i < apData.size(); i++) {
                                            WifiAPInfoEntity wifiInfo = apData.get(i);
                                            int levelInfoDiff = Math.abs(Math.abs(Integer.parseInt(wifiInfo.getRssiValue())) - Math.abs(result.level));
                                            WifiAPInfoEntity subtractedData = new WifiAPInfoEntity(wifiInfo.getRoomNumber(), wifiInfo.getApName(), Integer.toString(levelInfoDiff));
                                            apData.set(i, subtractedData);
                                        }
                                        Collections.sort(apData);
                                        //three nearest neighbours
                                        ArrayList<Integer> list_of_rooms = new ArrayList<>();
                                        int neighbours = Integer.parseInt(getLocationK.getText().toString());
                                        for (int i = 0; i < apData.size() && i < neighbours; i++) {
                                            list_of_rooms.add(Integer.parseInt(apData.get(i).getRoomNumber()));
                                        }
//                                list_of_rooms.add(Integer.parseInt(apData.get(0).getRoomNumber()));
//                                list_of_rooms.add(Integer.parseInt(apData.get(1).getRoomNumber()));
//                                list_of_rooms.add(Integer.parseInt(apData.get(2).getRoomNumber()));
                                        Log.d("Resultsinteri", Integer.toString(find_mode(list_of_rooms)));
                                        location_list.add(find_mode(list_of_rooms));
                                    }

                                }
                            }
                            setStatus.setText("Room Location is: " + Integer.toString(find_mode(location_list)));
                            Log.d("ResultsF", Integer.toString(find_mode(location_list)));
                            Log.d("ResultsFSize", Integer.toString((location_list).size()));
                            Log.d("ResultsFinal", location_list.toString());

                        }
                    }
                    else{
                        setStatus.setText("Please Enter value of k");
                    }
                }
            });


//        //Start Scanning
//        boolean success = wifiManager.startScan();
//        results = wifiManager.getScanResults();
//        Log.d("Results", Boolean.toString(success));
//        if(success){
//            for (ScanResult result : results){
//                int level = result.level;
//            }
//            Log.d("Results", results.toString());
//        }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private int find_mode(ArrayList<Integer> list){
        if(list == null || list.size() ==0 ){
            return 1;
        }
        Collections.sort(list);
        int prev = list.get(0);
        int count = 1;
        int max_count = 0;
        int max_count_el = list.get(0);
        for(int i = 1; i < list.size(); i++){
            if(list.get(i) == prev){
                count ++;
            }
            else{
                if(count > max_count){
                    max_count = count;
                    max_count_el = prev;
                }
                prev = list.get(i);
                count = 1;
            }
        }
        if(count > max_count){
            max_count = count;
            max_count_el = prev;
        }
        return max_count_el;
    }

    private void setChartAppearance(){
        apchart.getDescription().setEnabled(false);
        YAxis yL = apchart.getAxisLeft();
        YAxis yR = apchart.getAxisRight();
        yL.setGranularity(10f);
        yL.setAxisMinimum(-100);
        XAxis x = apchart.getXAxis();
        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return apNames[(int) value];
            }
        });
        yR.setGranularity(10f);
        yR.setAxisMinimum(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 0:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }
}
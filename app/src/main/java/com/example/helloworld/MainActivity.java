package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private Sensor accSensor, linearAccSensor, magneticSensor, proximitySensor, lightSensor;;
    private Button showAvgAccelerometer, showAvgLight;
    private TextView showAvg, detectStationary;
    private Switch saveAccData, saveLinAccData, saveLightData, saveGPSData, saveProximityData, saveMagneticData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAvgAccelerometer = (Button) findViewById(R.id.showAvgAccelerometer);
        showAvgLight = (Button) findViewById(R.id.showAvgLight);
        showAvg = (TextView) findViewById(R.id.showAvg);
        detectStationary = (TextView)findViewById(R.id.detectStationary);
        saveAccData = (Switch) findViewById(R.id.recordAccelerometer);
        saveLinAccData = (Switch) findViewById(R.id.recordLinAcc);
        saveLightData = (Switch) findViewById(R.id.recordLight);
        saveGPSData = (Switch) findViewById(R.id.recordGps);
        saveProximityData = (Switch) findViewById(R.id.recordProximity);
        saveMagneticData = (Switch) findViewById(R.id.recordMagnetic);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        AccelerometerDao accelerometerDao = db.accelerometerDao();
        accelerometerDao.deleteAll();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("Debug", deviceSensors.toString());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAccSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        saveAccData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!saveAccData.isChecked()){
                    detectStationary.setText("");
                }
            }
        });
        showAvgAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sumX=0, sumY=0, sumZ = 0;
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                AccelerometerDao accelerometerDao = db.accelerometerDao();
                long time2 = System.currentTimeMillis();
                long time1 = time2 - 3600000;
                ArrayList<Accelerometer> accList = (ArrayList<Accelerometer>) accelerometerDao.findPast1hr(time1, time2);
                Log.d("Debug", accList.toString());
                Log.d("Debug", time1 + " " + time2);
                for(int i = 0; i < accList.size(); i++){
                    sumX = sumX + accList.get(i).getX();
                    sumY = sumY + accList.get(i).getY();
                    sumZ = sumZ + accList.get(i).getZ();
                }
                sumX = sumX/accList.size();
                sumY = sumY/accList.size();
                sumZ = sumZ/accList.size();
                showAvg.setText("Average Accelerometer Value: \n x = " + Float.toString(sumX) + " , y = " + Float.toString(sumY) + " , z = " + Float.toString(sumZ));
            }
        });
        
        showAvgLight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                float sumX=0;
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                LightDao lightDao = db.lightDao();
                long time2 = System.currentTimeMillis();
                long time1 = time2 - 3600000;
                ArrayList<Light> lightList = (ArrayList<Light>) lightDao.findPast1hr(time1, time2);
                Log.d("Debug", lightList.toString());
                Log.d("Debug", time1 + " " + time2);
                for(int i = 0; i < lightList.size(); i++){
                    sumX = sumX + lightList.get(i).getLightValue();
                }
                sumX = sumX/lightList.size();
                showAvg.setText("Average Light Value: " + Float.toString(sumX));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAccSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if(saveAccData.isChecked()){
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long time = System.currentTimeMillis();
                Accelerometer accelerometer = new Accelerometer(x, y, z, time);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                AccelerometerDao accelerometerDao = db.accelerometerDao();
                accelerometerDao.insertAll(accelerometer);
                double value = Math.sqrt(x*x + y*y + z*z);
                value = (double) Math.round(value * 10) / 10;
                //Log.d("DebugValue", value + "");
                if(value >= 9.5 && value <= 10.5){
                    Log.d("DebugValueStationary", value + "");
                    detectStationary.setText("Device is Stationary");
                }else{
                    Log.d("DebugValueMoving", value + "");
                    detectStationary.setText("Device is Moving");
                }
                //Toast.makeText(this, "x=" + x + ", y=" + y + ", z=" + z, Toast.LENGTH_LONG).show();
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            if(saveLinAccData.isChecked()) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long time = System.currentTimeMillis();
                LinearAcceleration linearAcceleration = new LinearAcceleration(x, y, z, time);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                LinearAccelerationDao linearAccelerationDao = db.linearAccelerationDao();
                linearAccelerationDao.insertAll(linearAcceleration);
                //Toast.makeText(this, "x=" + x + ", y=" + y + ", z=" + z, Toast.LENGTH_LONG).show();
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            if(saveMagneticData.isChecked()) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long time = System.currentTimeMillis();
                Magnetometer magnetometer = new Magnetometer(x, y, z, time);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                MagnetometerDao magnetometerDao = db.magnetometerDao();
                magnetometerDao.insertAll(magnetometer);
                //Toast.makeText(this, "x=" + x + ", y=" + y + ", z=" + z, Toast.LENGTH_LONG).show();
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(saveLightData.isChecked()) {
                float x = event.values[0];
                long time = System.currentTimeMillis();
                Light light = new Light(x, time);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                LightDao lightDao = db.lightDao();
                lightDao.insertAll(light);
                //Toast.makeText(this, "x=" + x, Toast.LENGTH_LONG).show();
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if(saveProximityData.isChecked()) {
                float x = event.values[0];
                long time = System.currentTimeMillis();
                Proximity proximity = new Proximity(x, time);
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                ProximityDao proximityDao = db.proximityDao();
                proximityDao.insertAll(proximity);
                //Toast.makeText(this, "x=" + x, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(saveGPSData.isChecked()) {
            double x = location.getLatitude();
            double y = location.getLongitude();
            long time = System.currentTimeMillis();
            GPS gps = new GPS(x, y, time);
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            GPSDao gpsDAO = db.gpsDao();
            gpsDAO.insertAll(gps);
            //Toast.makeText(this, "x=" + x + ", y=" + y, Toast.LENGTH_LONG).show();
        }
    }
}
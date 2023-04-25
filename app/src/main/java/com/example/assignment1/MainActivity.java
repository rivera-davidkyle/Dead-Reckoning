package com.example.assignment1;

import static java.lang.Math.pow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener, LocationListener {
    double x, y, z;
    float ax = 0.0f, ay = 0.0f, az = 0.0f;
    double lng = 0, lat = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private LocationManager locationManager;
    private Fragment mapsFragment;
    private float init_time = 0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapsFragment = new MapsFragment();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout, mapsFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float dt = (event.timestamp - init_time) / 1e9f;
            //  Calculate alpha for the complimentary filter
            float alpha = 0.75f/(0.75f+dt);
            if (init_time == 0.0f)
            {
                dt = 0.0f;
            }
            ax = alpha * ax + (1 - alpha) * event.values[0];
            ay = alpha * ay + (1 - alpha) * event.values[1];

            x += 0.5 * ax * pow(dt, 2);
            y += 0.5 * ay * pow(dt, 2);
        }
        init_time = event.timestamp;
        // Log.d("DISP", "X: " + x + " Y:" + y);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lng = location.getLongitude();
        lat = location.getLatitude();

        lng += x;
        lat += y;

        Log.d("LOCATION", "LNG: " + lng + " LAT:" + lat);

        x = 0;
        y = 0;
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_FASTEST);
    }
}
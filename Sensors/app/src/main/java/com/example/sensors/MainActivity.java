package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
SensorManager sensorManager;
Sensor asen,gsen,psen;
SensorEventListener asenlist,gsenlist,psenlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        asen=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gsen=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        asenlist=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
           if(sensorEvent.values[2]>0.5f){
               getWindow().getDecorView().setBackgroundColor(Color.RED);
           }
           else if(sensorEvent.values[2] <-0.5f){
               getWindow().getDecorView().setBackgroundColor(Color.GREEN);
           }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        gsenlist=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2]>0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.MAGENTA);
                }
                else if(sensorEvent.values[2] <-0.5f){
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(asenlist,asen,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gsenlist,gsen,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(asenlist,asen);
        sensorManager.unregisterListener(gsenlist,gsen);
    }
}
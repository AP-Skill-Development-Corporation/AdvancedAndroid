package com.example.sensorsapi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Camera;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nisrulz.sensey.FlipDetector;
import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;

public class MainActivity extends AppCompatActivity {

    Switch s1,s2,s3,s4;
    TextView tv;
    public static Camera cam = null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.shake);
        s2 = findViewById(R.id.touch);
        s3 = findViewById(R.id.light);
        s4 = findViewById(R.id.flip);

        tv =findViewById(R.id.sensor);
        Sensey.getInstance().init(MainActivity.this);

        final FlipDetector.FlipListener flipListener=new FlipDetector.FlipListener() {
            @Override
            public void onFaceDown() {
                tv.setText("Face Down");
            }

            @Override
            public void onFaceUp() {
                tv.setText("Face Up");
            }
        };

        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Sensey.getInstance().startFlipDetection(flipListener);
                }else {
                    Sensey.getInstance().stopFlipDetection(flipListener);
                }
            }
        });




        final LightDetector.LightListener lightListener=new LightDetector.LightListener() {
            @Override
            public void onDark() {
                tv.setText("It Is Dark");
            }
            @Override
            public void onLight() {
                tv.setText("It Is Light");
            }
        };

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Sensey.getInstance().startLightDetection(lightListener);
                }else {
                    Sensey.getInstance().stopLightDetection(lightListener);
                }
            }
        });


        final ShakeDetector.ShakeListener shakeListener = new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {
                tv.setText("Shake Sensor Detected");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null;
                    try {
                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, true);   //Turn ON
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onShakeStopped() {
                tv.setText("Shake Sensor Stopped");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null;
                    try {
                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, false);   //Turn OFF
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        };



        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Sensey.getInstance().startShakeDetection(shakeListener);
                }else {
                    Sensey.getInstance().stopShakeDetection(shakeListener);
                }
            }
        });

        // Touch Type Sensor Implementation

        final TouchTypeDetector.TouchTypListener touchTypListener =new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onDoubleTap() {
                tv.setText("Double Tap Detected");
            }

            @Override
            public void onLongPress() {
                tv.setText("Long Press Detected");
            }

            @Override
            public void onScroll(int i) {
                tv.setText("Scroll Detected");
            }

            @Override
            public void onSingleTap() {
                tv.setText("Single Tap Detected");
            }

            @Override
            public void onSwipe(int i) {
                tv.setText("Swipe Detected");
            }

            @Override
            public void onThreeFingerSingleTap() {
                tv.setText("Three Fingers Tap Detected");
            }

            @Override
            public void onTwoFingerSingleTap() {
                tv.setText("Two Fingers Tap Detected");
            }
        };



        // Touch Sensor Click Event

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Sensey.getInstance().startTouchTypeDetection(MainActivity.this,touchTypListener);
                }else {
                    Sensey.getInstance().stopTouchTypeDetection();
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sensey.getInstance().stop();
    }
}

package edu.lewisu.cs.howardcy.shaker;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class Shaker extends ActionBarActivity {
    private final int ACCEL_THRESHOLD = 15000;

    private SensorManager sensorManager;

    private float deltaAccel;
    private float currentAccel;
    private float lastAccel;
    private MySensorListener sensorListener;
    private Sensor accelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaker);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorListener = new MySensorListener();
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //initialize acceleration variables
        deltaAccel = 0.0f;
        currentAccel = SensorManager.GRAVITY_EARTH;
        lastAccel = SensorManager.GRAVITY_EARTH;

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener,accelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorListener);
        super.onResume();
    }

    private class MySensorListener implements SensorEventListener{
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
            //empty implementation
        }


        @Override
        public void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();

            if(type==Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                lastAccel = currentAccel;
                currentAccel = x*x+ y*y + z*z;
                deltaAccel = currentAccel*(currentAccel-lastAccel);


                //if there has been significant change in acceleration
               if(deltaAccel > ACCEL_THRESHOLD){
                    Toast toast = Toast.makeText(Shaker.this, "Device was shuffled ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

}

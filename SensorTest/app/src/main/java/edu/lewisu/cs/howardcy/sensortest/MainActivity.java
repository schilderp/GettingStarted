package edu.lewisu.cs.howardcy.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    //constants used in calculating and displaying acceleration
    private final int ACCEL_THRESHOLD = 15000;
    private final float alpha = 0.8f;

    //references to text views
    private TextView xViewA;
    private TextView yViewA;
    private TextView zViewA;
    private TextView xViewO;
    private TextView yViewO;
    private TextView zViewO;
    private TextView lightView;

    //sensor objects
    private SensorManager sm;
    private Sensor accelSensor;
    private Sensor lightSensor;
    private Sensor magSensor;

    //used in calculating acceleration
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private float deltaAccel;
    private float currentAccel;
    private float lastAccel;

    //used in calculating rotation
    private float mAzimuth;
    private float mPitch;
    private float mRoll;
    private float[] mGravs = new float[3];
    private float[] mGeoMags = new float[3];
    private float[] mOrientation = new float[3];
    private float[] mRotationM = new float[9];
    private float[] mRemapedRotationM = new float[9];

    //used in calculating lightChange;
    private float prevLight;

    SensorListener sensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xViewA = (TextView)findViewById(R.id.accelX);
        yViewA = (TextView)findViewById(R.id.accelY);
        zViewA = (TextView)findViewById(R.id.accelZ);
        xViewO = (TextView)findViewById(R.id.orientX);
        yViewO = (TextView)findViewById(R.id.orientY);
        zViewO = (TextView)findViewById(R.id.orientZ);
        lightView = (TextView)findViewById(R.id.lightValue);

        //retrieve sensors
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        magSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //initialize acceleration variables
        deltaAccel = 0.0f;
        currentAccel = SensorManager.GRAVITY_EARTH;
        lastAccel = SensorManager.GRAVITY_EARTH;

        //instantiate sensor listener
        sensorListener = new SensorListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sensorListener, accelSensor, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(sensorListener, magSensor, SensorManager.SENSOR_DELAY_UI);


        sm.registerListener(sensorListener, lightSensor, SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    protected void onPause() {
        sm.unregisterListener(sensorListener);
        super.onPause();
    }


    private class SensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                int type = event.sensor.getType();

                if (type == Sensor.TYPE_ACCELEROMETER) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    lastAccel = currentAccel;
                    currentAccel = x * x + y * y + z * z;
                    deltaAccel = currentAccel * (currentAccel - lastAccel);

                    //for computing orientation
                    for (int i = 0; i < 3; i++)
                        mGravs[i] = event.values[i];

                    //if there has been significant change in acceleration
                    if (deltaAccel > ACCEL_THRESHOLD) {

                        //removing the effect of gravity
                        // In this example, alpha is calculated as t / (t + dT),
                        // where t is the low-pass filter's time-constant and
                        // dT is the event delivery rate.

                        gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
                        gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
                        gravity[2] = alpha * gravity[2] + (1 - alpha) * z;

                        linear_acceleration[0] = x - gravity[0];
                        linear_acceleration[1] = y - gravity[1];
                        linear_acceleration[2] = z - gravity[2];

                        xViewA.setText("Accel X: " + linear_acceleration[0]);
                        yViewA.setText("Accel Y: " + linear_acceleration[1]);
                        zViewA.setText("Accel Z: " + linear_acceleration[2]);


                    }
                }

                if (type == Sensor.TYPE_MAGNETIC_FIELD) {
                    for (int i = 0; i < 3; i++)
                        mGeoMags[i] = event.values[i];
                }

                boolean success = false;
                if (SensorManager.getRotationMatrix(mRotationM, null, mGravs, mGeoMags)) {
                    SensorManager.remapCoordinateSystem(mRotationM,
                            SensorManager.AXIS_X,
                            SensorManager.AXIS_Z,
                            mRemapedRotationM);
                    SensorManager.getOrientation(mRemapedRotationM, mOrientation);
                    success = true;
                }

                //if successfully retrieved rotation matrix
                if (success) {

                    // Convert to degrees in 0.5 degree resolution.
                    mAzimuth = (float) Math.round((Math.toDegrees(mOrientation[0])) * 2 / 2);
                    mPitch = (float) Math.round((Math.toDegrees(mOrientation[1])) * 2 / 2);
                    mRoll = (float) Math.round((Math.toDegrees(mOrientation[2])) * 2 / 2);

                    // adjust the range: 0 < range <= 360 (from: -180 < range <= 180).
                    mAzimuth = (mAzimuth + 360) % 360;

                    xViewO.setText("Azimuth:  " + mAzimuth);
                    yViewO.setText("Pitch: " + mPitch);
                    zViewO.setText("Roll: " + mRoll);
                } else {
                    xViewO.setText("Unable to retrieve rotation data");
                    yViewO.setText("");
                    zViewO.setText("");

                }

                if (type == Sensor.TYPE_LIGHT) {
                    float lux = event.values[0];
                    float change = Math.abs(prevLight - lux);
                    if(change > 20) {
                        String lightStr = lux + " lux";
                        lightStr += "\nCloudy =" + SensorManager.LIGHT_CLOUDY;
                        lightStr += "\nFull Moon =" + SensorManager.LIGHT_FULLMOON;
                        lightView.setText(lightStr);
                    }
                    prevLight = lux;
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}

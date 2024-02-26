package edu.otc.compass_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // public static final String STRING_TYPE_MAGNETIC_FIELD = null;
    private ImageView compassImg;
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private boolean lastAccelerometerSet = false;
    private boolean lastMagnetometerSet = false;
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];
    private float currentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compassImg = findViewById(R.id.compass); // grab the compass image by ID
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    //@Override
    /*protected void onResume() { // register listener to an "active" status when the app is in use
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME); // NOT WORKING, CAN'T CAST TYPE THIS TO SENSOREVENTLISTENER
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }*/

    @Override
    protected void onPause() { // pause listeners when the app is not in use
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this, magnetometer);
        sensorManager.unregisterListener((SensorEventListener) this, accelerometer);
    }

//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor == magnetometer) {
//            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
//            lastMagnetometerSet = true;
//        } else if (event.sensor == accelerometer) {
//            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
//            lastAccelerometerSet = true;
//        }
//
//        if (lastAccelerometerSet && lastMagnetometerSet) {
//            SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer);
//            SensorManager.getOrientation(rotationMatrix, orientation);
//
//            float azimuthInRadians = orientation[0];
//            float azimuthInDegrees = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;
//
//            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass);
//            Matrix matrix = new Matrix();
//            matrix.postRotate(-azimuthInDegrees);
//            Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
//
//            compassImg.setImageBitmap(rotatedBitmap);
//        }
//    }

    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        compassImg.startAnimation(ra);
        currentDegree = -degree;

    }
}


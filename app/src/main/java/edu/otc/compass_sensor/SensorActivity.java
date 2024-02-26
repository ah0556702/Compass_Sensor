package edu.otc.compass_sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

private final Sensor mAccelerometer;
private final Sensor mMagnetometer;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorMan = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // access Sensor hardware through Android OS
    mAccelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
}


// Display acceleration along x, y and z-axis.

package com.example.snippet.accelerometersensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private CustomView view;
    private SensorManager sensormanager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the display to custom view
        view = new CustomView(getApplicationContext());
        setContentView(view);

        // access the device's sensors
        sensormanager = (SensorManager)getSystemService(SENSOR_SERVICE);

        // check if sensor is available
        if(sensormanager == null) {
            Toast.makeText(this, "WALANG SENSOR SERVICE", Toast.LENGTH_SHORT).show();
            return;
        }

        // list all sensors on device
        List<Sensor> sensors = sensormanager.getSensorList(Sensor.TYPE_ALL);
        for(int i=0; i<sensors.size(); i++){
            Log.d("output", String.format("%d %s %s %d", i+1, sensors.get(i).getName() , sensors.get(i).getVendor(), sensors.get(i).getVersion()));
        }

        // get gravity sensor
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // check if sensor is available
        if(sensor == null)
            Toast.makeText(this, "WALANG LINEAR ACCELERATION SENSOR", Toast.LENGTH_SHORT).show();
    }

    // enable sensor
    @Override
    protected void onResume() {
        super.onResume();

        sensormanager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    // disable sensor
    @Override
    protected void onPause() {
        super.onPause();

        sensormanager.unregisterListener(this);
    }

    //
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // get reading
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x, y, z;

        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            view.setValue(x, y, z);
        }
    }
}

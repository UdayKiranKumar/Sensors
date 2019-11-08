package com.example.sensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tvsensor;
    SensorManager sensorManager;

    Sensor lightSensor,proxySensor,tempSensor,accSensor,magSensor;
    float [] accarr = new float[3];
    float [] magarr = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvsensor = findViewById(R.id.tvsensor);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proxySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null)
        {
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proxySensor != null)
        {
            sensorManager.registerListener(this,proxySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (tempSensor != null)
        {
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accSensor != null)
        {
            sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (magSensor != null)
        {
            sensorManager.registerListener(this,magSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType)
        {
            case Sensor.TYPE_LIGHT:
            break;
            case Sensor.TYPE_PROXIMITY:
                float[] value = event.values;
                Toast.makeText(this,"Distance is"+ value[0],Toast.LENGTH_SHORT).show();
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accarr = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magarr = event.values.clone();
                break;
        }

        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,null,accarr,magarr);
        if (rotationOK)
        {
            float orientation[] = new float[3];
            SensorManager.getOrientation(rotationMatrix,orientation);
            float azimut = orientation[0];
            float pitch = orientation[1];
            float roll = orientation[2];
           tvsensor.setText("azimut is :" + azimut + "\n pitch is :" +pitch+ "\n roll is :"+roll);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void dothis(View view) {
        List<Sensor>sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor s : sensorList)
        {
            String s1 = s.getName()+"Vendor: "+ s.getVendor()+ "Version" + s.getVersion();
            stringBuilder.append(s1+"\n");
        }
        tvsensor.setText(stringBuilder);
    }
}

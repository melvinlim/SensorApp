package com.melvinlim.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

public class MySensorEventListener implements SensorEventListener {
    private TextView sensorView = null;
    private List<Sensor> deviceSensors;
    public boolean listening;

    private SensorManager sensorManager;
    private static Context context = null;

    public CharSequence sensorList = "";

    private void initSensors(){
        if(context == null) return;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager==null)
            return;
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        CharSequence str = "";
        for(int i=0;i<deviceSensors.size();i++){
            Sensor sensor = deviceSensors.get(i);
            sensorList += sensor.getName();
            sensorList += "\n";
        }
    }

    public MySensorEventListener(Context context,TextView sensorView){
        this.context = context;
        this.sensorView = sensorView;
        initSensors();
    }

    private void registerSensorListeners(){
        if(deviceSensors!=null){
            for(int i=0;i<deviceSensors.size();i++) {
                Sensor sensor = deviceSensors.get(i);
                //sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(listening){
            if(sensorView != null){

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

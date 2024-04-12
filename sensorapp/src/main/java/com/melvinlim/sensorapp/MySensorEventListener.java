package com.melvinlim.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.List;

public class MySensorEventListener implements SensorEventListener {
    private List<Sensor> deviceSensors;
    public boolean listening;
    public String sensorValues;

    private SensorManager sensorManager;
    private Context context = null;

    public CharSequence sensorList = "";

    private void initSensors(){
        if(context == null) return;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager==null)
            return;
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0;i<deviceSensors.size();i++){
            Sensor sensor = deviceSensors.get(i);
            sensorList += sensor.getName();
            sensorList += "\n";
        }
    }

    public MySensorEventListener(Context context){
        this.context = context;
        initSensors();
        registerSensorListeners();
    }

    public void registerSensorListeners(){
        if(deviceSensors!=null){
            deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
            Sensor sensor = deviceSensors.get(0);
            if(sensorManager!=null){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            /*
            for(int i=0;i<deviceSensors.size();i++) {
                Sensor sensor = deviceSensors.get(i);
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
             */
        }
    }

    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(listening){
            sensorValues="";
            for(int i=0;i<event.values.length;i++){
                sensorValues+=(Float.toString(event.values[i]));
                sensorValues+=" ";
            }
            sensorValues+="\n";
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

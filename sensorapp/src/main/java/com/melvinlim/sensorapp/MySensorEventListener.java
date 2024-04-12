package com.melvinlim.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySensorEventListener implements SensorEventListener {
    private List<Sensor> deviceSensors;
    public String sensorValues;

    private SensorManager sensorManager;
    private final Context context;

    public List<Integer> sensorTypeList = new ArrayList<>();
    private final String[] sensors = new String[256];

    public String getData(){
        StringBuilder sb = new StringBuilder(sensors.length);
        for (int x : sensorTypeList){
            sb.append(sensors[x]);
        }
        return sb.toString();
    }

    private void initSensors(){
        if(context == null) return;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager==null)
            return;
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0;i<deviceSensors.size();i++){
            Sensor sensor = deviceSensors.get(i);
            sensorTypeList.add(sensor.getType());
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
            for(int i=0;i<deviceSensors.size();i++) {
                Sensor sensor = deviceSensors.get(i);
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    public void unregisterSensorListeners(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorValues=event.sensor.getName()+":\n";

        StringBuilder sb = new StringBuilder(2 * event.values.length);
        for(int i=0;i<event.values.length;i++){
            sb.append(String.format(Locale.CANADA, "%.4f", event.values[i]));
            sb.append(" ");
        }
        sensorValues += sb +"\n";
        sensors[event.sensor.getType()]=sensorValues;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

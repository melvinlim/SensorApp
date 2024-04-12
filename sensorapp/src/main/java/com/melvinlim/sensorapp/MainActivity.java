package com.melvinlim.sensorapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

//import java.time.ZonedDateTime;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private MySensorEventListener mySensorEventListener;
    private TextView sensorView;
    private TextView timerView;
    private Timer myTimer;

    void sensorTest(){
        runOnUiThread(new Runnable() {
                          public void run() {
                              CharSequence text = mySensorEventListener.sensorList;
                              text += "\n";
                              //text += mySensorEventListener.sensorValues;

                              text += mySensorEventListener.getData();

                              sensorView.setText(text);
                          }
                      });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorEventListener.registerSensorListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorEventListener.unregisterSensorListeners();
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            /*
            long currentTime = System.currentTimeMillis();
            String timeStr = Long.toString(currentTime);
            timeStr += "\n";

            timeStr += ZonedDateTime.now();
            timerView.setText(timeStr);
             */
            sensorTest();

            runOnUiThread(new Runnable() {
                public void run() {
                    long currentTime = System.currentTimeMillis();
                    String timeStr = Long.toString(currentTime);
                    timeStr += "\n";

                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CANADA);
                    Date today = Calendar.getInstance().getTime();
                    timeStr += df.format(today);
                    timerView.setText(timeStr);
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("info", "in onCreate");
        setContentView(R.layout.activity_main);

        timerView = (TextView) findViewById(R.id.myTextView1);
        sensorView = (TextView) findViewById(R.id.myTextView2);
        mySensorEventListener = new MySensorEventListener(getApplicationContext());

        myTimer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        myTimer.schedule(myTimerTask, 0, 100);

    }
}
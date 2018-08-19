package opr.capacidad.model;

import android.app.Service;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import opr.capacidad.Vistas.ResolverTareaActivity;


public class Chronometer extends Service {

    private Timer timer = new Timer();
    private static final long UPDATE_INTERVAL = 1000; // ms
    public static ResolverTareaActivity ACTIVITY;
    private double chronometer = 0;
    private Handler handler;

    public static void setUpdateListener(ResolverTareaActivity a) {
        Log.i("CRONOMETRO", "-- asignando servicio --");
        ACTIVITY = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        Log.i("CRONOMETRO", "-- creando servicio --");

        handler = new Handler() {

            public void handleMessage(Message msg) {
                ACTIVITY.updateChronometer(chronometer);
            }
        };
    }

    @Override
    public void onDestroy() {
        Log.i("CRONOMETRO", String.format("%.2f",chronometer) + "s");
        stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void initialize() {
        Log.i("CRONOMETRO", "-- initialize --");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                chronometer += 1;
                handler.sendEmptyMessage(0);
            }
        },0,UPDATE_INTERVAL);
    }

    private void stop() {
        if (timer != null)
            timer.cancel();
    }
}

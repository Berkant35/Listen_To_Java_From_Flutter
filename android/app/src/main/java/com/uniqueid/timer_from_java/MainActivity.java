package com.uniqueid.timer_from_java;

import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    Timer timer1 = new Timer("MyTimer-1");
    private Map<Object, Runnable> listeners = new HashMap<>();
    String timerResult;
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        SimpleTimerTask simpleTimerTask = new SimpleTimerTask("Task1",timerResult);

        new EventChannel(flutterEngine.getDartExecutor(), "streamData").setStreamHandler(
                new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object listener, EventChannel.EventSink eventSink) {
                        startListening(listener, eventSink);
                    }
                    @Override
                    public void onCancel(Object listener) {
                        cancelListening(listener);
                    }

                }
        );
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "mainChannel").setMethodCallHandler(
                (call, result) -> {

                    result.success(simpleTimerTask.timerResultOfTask);

                }
        );
    }
    public static class SimpleTimerTask extends TimerTask {
        public String name;
        public String timerResultOfTask;

        public SimpleTimerTask(String name,String timerResult) {
            this.name = name;
            this.timerResultOfTask = timerResult;
        }

        public void setTimerResult(String dateTime){
            timerResultOfTask = dateTime;
        }

        @Override
        public void run() {
            String dateStr = String.valueOf(new Date().getTime());
            String currentThreadStr = Thread.currentThread().getName();

            setTimerResult(dateStr);
            System.out.printf("SimpleTimerTask {name: %s, date: %s, thread: %s}\n", name, dateStr, currentThreadStr);
        }
    }
    void startListening(Object listener, EventChannel.EventSink emitter) {
        // Prepare a timer like self calling task
        final Handler handler = new Handler();
        listeners.put(listener, new Runnable() {
            @Override
            public void run() {
                if (listeners.containsKey(listener)) {
                    // Send some value to callback
                    emitter.success("Hello listener! " + (System.currentTimeMillis() / 1000));
                    handler.postDelayed(this, 1000);
                }
            }
        });
        // Run task
        handler.postDelayed(listeners.get(listener), 1000);
    }
    void cancelListening(Object listener) {
        // Remove callback
        listeners.remove(listener);
    }
}



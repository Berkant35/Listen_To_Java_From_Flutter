package com.uniqueid.timer_from_java;

import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.ArrayList;
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
    private Map<String, Runnable> listeners = new HashMap<>();
    ArrayList<String> allEpc = new ArrayList<String>();
    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);

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
    }

    void startListening(Object listener, EventChannel.EventSink emitter) {
        // Prepare a timer like self calling task
        final Handler handler = new Handler();
        listeners.put(listener.toString(), new Runnable() {
            @Override
            public void run() {
                if (listeners.containsKey(listener)) {
                    allEpc.add("Yeni Data");
                    emitter.success(allEpc);
                    handler.postDelayed(this, 1);

                }
            }
        });
        // Run task
        handler.postDelayed(listeners.get(listener), 1);
    }
    void cancelListening(Object listener) {
        // Remove callback
        listeners.remove(listener);
    }
}



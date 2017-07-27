package com.androiddev.zf.androidadvanced.chapter3.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androiddev.zf.androidadvanced.R;

public class ThreadTestActivity extends AppCompatActivity {

    private static final String TAG = "ThreadTestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        final MyArrayBlockingQueue<Integer> queue = new MyArrayBlockingQueue<>();
        queue.put(10);
        queue.put(12);
        queue.put(14);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Log.d(TAG, "run: lolo");
                    queue.put(40);
                    queue.put(43);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        for (int i = 0; i < 5; i++) {
            Log.d(TAG, "onCreate: " + i);
            Log.d(TAG, String.valueOf(queue.take()));
        }
        Log.d(TAG, "onCreate:  我还是走到了这里");
    }

    public void test() {
        
    }
}

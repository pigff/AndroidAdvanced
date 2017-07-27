package com.androiddev.zf.androidadvanced.chapter3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.androiddev.zf.androidadvanced.R;

public class SimpleAsyncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_async);
        final TextView textView = (TextView) findViewById(R.id.simple_async_tv_chapter);
        new SimpleAsyncTask<String>() {

            @Override
            protected void onPreExecute() {
                Log.d("SimpleAsyncActivity", "正在准备");
            }

            @Override
            protected String doInBackground() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "嘻嘻";
            }

            @Override
            protected void onPostExecute(String s) {
                textView.setText(s);
            }
        }.execute();
    }
}

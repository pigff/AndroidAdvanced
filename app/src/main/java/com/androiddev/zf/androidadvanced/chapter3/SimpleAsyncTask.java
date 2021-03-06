package com.androiddev.zf.androidadvanced.chapter3;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

/**
 * Created by greedy on 2017/7/27.
 */

public abstract class SimpleAsyncTask<Result> {

    //HandlerThread内部封装了自己的Handler和Thread，有单独的Looper和消息队列
    private static final HandlerThread HT = new HandlerThread("SimpleAsyncTask",
            Process.THREAD_PRIORITY_BACKGROUND);

    static {
        HT.start();
    }

    //与异步线程队列关联的Handler
    final Handler mAsyncHandler = new Handler(HT.getLooper());
    // 获取调用execute的线程的Looper
    final Handler mUIHandler = new Handler(Looper.getMainLooper());

    /**
     * 任务执行前的初始化操作
     */
    protected void onPreExecute() {

    }

    /**
     * 后台执行任务
     * @return 返回执行结果
     * 必须重写
     */
    protected abstract Result doInBackground();

    /**
     * doInBackground返回结果传递给执行在UI线程的onPostExecute
     */

    protected void onPostExecute(Result result) {

    }

    /**
     * execute方法，执行任务，调用doInBackground,并且将结果投递给UI线程，使用户可以在onPostExecute处理结果
     */
    public final SimpleAsyncTask<Result> execute() {
        onPreExecute();
        mAsyncHandler.post(new Runnable() {
            @Override
            public void run() {
                //后台执行任务，完成之后向UI线程post数据，用以更新UI等操作
                postResult(doInBackground());
            }
        });
        return this;
    }

    private void postResult(final Result result) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

}

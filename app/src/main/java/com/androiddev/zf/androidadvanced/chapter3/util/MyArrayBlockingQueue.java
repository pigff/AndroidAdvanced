package com.androiddev.zf.androidadvanced.chapter3.util;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by greedy on 2017/7/10.
 */

public class MyArrayBlockingQueue<T> {

    private static final String TAG = "MyArrayBlockingQueue";

    //数据数组
    private final T[] items;
    //锁
    private final Lock mLock = new ReentrantLock();
    //队满的条件
    private Condition notFull = mLock.newCondition();
    //队空的条件
    private Condition notEmpty = mLock.newCondition();
    //头部的索引
    private int head;
    //尾部的索引
    private int tail;
    //数据的个数
    private int count;

    public MyArrayBlockingQueue(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public MyArrayBlockingQueue() {
        this(10);
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        mLock.lock();
        try {
            return count;
        } finally {
            mLock.unlock();
        }
    }

    public void put(T t) {
        mLock.lock();
        try {
            while (count == getCapacity()) {
                Log.d(TAG, "put: 数据已满，等待");
                notFull.await();
            }
            items[tail] = t;
            if (++tail == getCapacity()) {
                tail = 0;
            }
            ++count;
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
    }

    public T take() {
        mLock.lock();
        try {
            while (count == 0) {
                Log.d(TAG, "take: 还没有数据，等待");
                notEmpty.await();
            }
            T ret = items[head];
            items[head] = null;
            if (++head == getCapacity()) {
                head = 0;
            }
            --count;
            notFull.signalAll();
            return ret;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mLock.unlock();
        }
        return null;
    }
}

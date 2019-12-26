package com.muhammedokumus;

import java.util.StringJoiner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Strawberry yogurt redesigned for people who like to share... But not too much
 */
public class ThreadSafeBestDSEver implements Runnable {
    private final BestDSEver m = new BestDSEver();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Object get(int index) {
        r.lock();
        try {
            Object o = m.get(index);
            System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: get(" + index + ")" + "->" + o.toString());
            return o;
        }
        finally { r.unlock(); }
    }

    public void insert(int index, Object o) {
        System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: insert(" + index + ", "+ o.toString() +")");
        w.lock();
        try {  m.insert(index, o); }
        finally { w.unlock(); }
    }

    public void remove(int index) {
        System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: remove(" + index + ")");
        w.lock();
        try { m.remove(index); }
        finally { w.unlock(); }
    }

    public int size() {
        System.out.println("Current Thread Name- " + Thread.currentThread().getName() + " | Operation: size()");
        w.lock();
        try { return m.size(); }
        finally { w.unlock(); }
    }

    @Override
    public void run() {
        // Getting thread's name
        System.out.println("Current Thread Name- " + Thread.currentThread().getName());
        // Getting thread's ID
        System.out.println("Current Thread ID- " + Thread.currentThread().getId() + " For Thread- " + Thread.currentThread().getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThreadSafeBestDSEver.class.getSimpleName() + "[", "]")
                .add("m=" + m)
                .toString();
    }
}

package com.muhammedokumus;

import java.util.Random;

/**
 * Consumer class insert operations on ThreadSafeBestDSEver
 */
public class Producer implements Runnable{
    private final ThreadSafeBestDSEver sharedCollection;
    boolean cancel = false;
    private int maxSize;

    public Producer(ThreadSafeBestDSEver sc, int maxSize){
        this.sharedCollection = sc;
        this.maxSize = maxSize;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(!cancel) {
            synchronized (sharedCollection) {
                while(sharedCollection.size() >= maxSize){
                    try {
                        System.out.println("Collection is full");
                        sharedCollection.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                Random random = new Random();
                int number = random.nextInt(100);
                sharedCollection.insert(0,number);
                System.out.println("Producer edit: " + sharedCollection);
                sharedCollection.notify();
            }
        }
    }
}

package com.muhammedokumus;

/**
 * Consumer class performs get & remove operations on ThreadSafeBestDSEver
 */
public class Consumer implements Runnable{
    private final ThreadSafeBestDSEver sharedCollection;
    boolean cancel = false;
    boolean done = false;
    public Consumer(ThreadSafeBestDSEver sc){
        this.sharedCollection = sc;
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
            done = true;
            synchronized (sharedCollection) {
                while(sharedCollection.size() == 0) {
                    try {
                        System.out.println("Collection is empty");
                        sharedCollection.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sharedCollection.get(0);
                sharedCollection.remove(0);
                System.out.println("Consumer edit: " + sharedCollection);
                sharedCollection.notify();
            }
        }
    }
}

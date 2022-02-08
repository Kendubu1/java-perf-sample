package com.performance.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class threadB implements Runnable {
    private Logger logger = LoggerFactory.getLogger(threadB.class);

    private Object lockA;
    private Object lockB;

    public threadB(Object lockA,Object lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockB){
            try {
                logger.info("lockB !",Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(" !",Thread.currentThread().getName(),e);
            }
            synchronized (lockA){
                logger.info("lockA !",Thread.currentThread().getName());
            }
        }
    }
}

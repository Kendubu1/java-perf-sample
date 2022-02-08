package com.performance.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class threadA implements Runnable  {
    private Logger logger = LoggerFactory.getLogger(threadA.class);

    private Object lockA;
    private Object lockB;

    public threadA(Object lockA,Object lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA){
            try {
                logger.info("lockA !",Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                logger.error(" !",Thread.currentThread().getName(),e);
            }
            synchronized (lockB){
                logger.info("lockB !",Thread.currentThread().getName());
            }
        }
    }
}

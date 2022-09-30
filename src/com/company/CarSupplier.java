package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarSupplier implements Runnable {
    private final CarWarehouse carWarehouse;
    private final ReentrantLock threadLocker;
    private final Condition condition;
    private final int carBuildTime = 2500;

    public CarSupplier(CarWarehouse carWarehouse, ReentrantLock threadLocker, Condition condition) {
        this.carWarehouse = carWarehouse;
        this.threadLocker = threadLocker;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (carWarehouse.isPossibleSell()) {
            threadLocker.lock();
            try {
                if (!carWarehouse.isFull()) {
                    Thread.sleep(carBuildTime);
                    carWarehouse.add(new Car("Toyota"));
                    System.out.println(Thread.currentThread().getName() + ": выпустил 1 авто");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                threadLocker.unlock();
            }
        }
    }
}

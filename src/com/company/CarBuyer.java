package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarBuyer implements Runnable {
    private final CarWarehouse carWarehouse;
    private final ReentrantLock threadLocker;
    private final Condition condition;
    private final int thinkBuyerTime = 2500;

    public CarBuyer(CarWarehouse carWarehouse, ReentrantLock threadLocker, Condition condition) {
        this.carWarehouse = carWarehouse;
        this.threadLocker = threadLocker;
        this.condition = condition;

    }

    @Override
    public void run() {

        threadLocker.lock();
        try {
            while (carWarehouse.isPossibleSell()) {
                System.out.println("Покупатель " + Thread.currentThread().getName() + ": зашел в автосалон");
                if (carWarehouse.isEmpty()) {
                    System.out.println("Машин нет!");
                    carWarehouse.addInQueue(Thread.currentThread());
                    condition.await();
                }
                Thread.sleep(thinkBuyerTime);
                carWarehouse.sell(Thread.currentThread());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadLocker.unlock();

        }
    }
}


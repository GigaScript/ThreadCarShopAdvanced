package com.company;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarWarehouse {
    final static int maximumCarsInStock = 2;
    final static ArrayList<Car> carInStock = new ArrayList<>();
    private static final ReentrantLock threadLocker = new ReentrantLock();
    private static final Condition condition = threadLocker.newCondition();
    private static final List<Thread> stack = new ArrayList<>();
    private static final List<String> stack2 = new ArrayList<>();
    private int totalCarSells = 0;
    private final int maximumCarSell;

    public CarWarehouse(int maximumCarSell) {
        this.maximumCarSell = maximumCarSell;
    }

    public Car getCar() {
        return carInStock.get(0);
    }

    public boolean inStock() {
        return carInStock.size() > 0;
    }

    public boolean isEmpty() {
        return carInStock.size() == 0;
    }

    public boolean isFull() {
        return carInStock.size() == maximumCarsInStock;
    }

    public boolean isPossibleSell() {
        return totalCarSells() < maximumCarSell;
    }


    public void sell(Thread thread) {
        if (isPossibleSell()) {
            if (inStock()) {
                totalCarSells++;
                if (stack.size() > 0) {
                    System.out.println("Покупатель " + stack.get(0).getName() + ": уехал на новеньком авто: " + carInStock.get(0).getManufacturer());
                    stack.remove(0);
                }
                carInStock.remove(0);
            }
        } else {
            condition.signalAll();
        }
    }

    public int totalCarSells() {
        return totalCarSells;
    }

    //для вывода последовательности добавления потоков в стэк, для проверки последовательности уехавших машин
    public List<String> getStack2() {
        return stack2;
    }

    public void addInQueue(Thread carBuyer) throws InterruptedException {
        stack.add(carBuyer);
        stack2.add(carBuyer.getName());
    }


    public ReentrantLock getThreadLocker() {
        return threadLocker;
    }

    public Condition getCondition() {
        return condition;
    }

    public void add(Car car) {
        carInStock.add(car);
        condition.signalAll();
    }
}

package com.company;

public class CarShop {
    private static final int totalConsumerThreads = 3;
    private static final int maximumCarSell = 5;
    private static final CarWarehouse carWarehouse = new CarWarehouse(maximumCarSell);


    public static void main(String[] args)  {
        for (int i = 0; i < totalConsumerThreads; i++) {
            new Thread(
                    null,
                    new CarBuyer(
                            carWarehouse,
                            carWarehouse.getThreadLocker(),
                            carWarehouse.getCondition()),
                    i + "")
                    .start();
        }
        new Thread(
                null,
                new CarSupplier(
                        carWarehouse,
                        carWarehouse.getThreadLocker(),
                        carWarehouse.getCondition()),
                "Toyota Motor SPB")
                .start();
    }
}
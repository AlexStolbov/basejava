package com.amstolbov;

public class DeadLock {

    private String name;

    public DeadLock(String name) {
        this.name = name;
    }

    public synchronized void doOne(DeadLock monitor) {
        System.out.println(Thread.currentThread().getName() + " " + getName() + ".doOne()");
        doAnyThing();
        System.out.println(Thread.currentThread().getName() + " invoke " + monitor.getName() + ".doTwo()");
        monitor.doTwo();
    }

    public synchronized void doTwo() {
        System.out.println(Thread.currentThread().getName() + " " + getName() + ".doTwo()");
    }

    public String getName() {
        return this.name;
    }

    private void doAnyThing() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e){};
    }

    public static void main(String[] args) {
        DeadLock monitorOne = new DeadLock("MonitorOne");
        DeadLock monitorTwo = new DeadLock("MonitorTwo");
        new Thread(() -> {
            monitorOne.doOne(monitorTwo);
            System.out.println("one finish");
        }).start();

        new Thread(() -> {
            monitorTwo.doOne(monitorOne);
            System.out.println("two finish");
        }).start();
    }
}

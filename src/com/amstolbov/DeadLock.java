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
            Thread.sleep(0);
        } catch (InterruptedException e){};
    }

    public static void main(String[] args) {
        DeadLock monitorOne = new DeadLock("MonitorOne");
        DeadLock monitorTwo = new DeadLock("MonitorTwo");
        createThread(monitorOne, monitorTwo, "one thread finish");
        createThread(monitorTwo, monitorOne, "two thread finish");
    }

    private static void createThread(DeadLock monitorOne, DeadLock monitorTwo, String message) {
        new Thread(() -> {
            monitorOne.doOne(monitorTwo);
            System.out.println(message);
        }).start();
    }
}

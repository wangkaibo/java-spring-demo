package com.example.demo.study;

public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 end");
        });
        t1.start();
        t1.join(1000);
        System.out.println("main end");
    }
}

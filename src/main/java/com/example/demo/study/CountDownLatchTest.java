package com.example.demo.study;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch c = new CountDownLatch(3);
        System.out.println("start");

        new Thread(c::countDown).start();
        new Thread(c::countDown).start();

        c.countDown();
        System.out.println("await");
//        c.await();
        boolean awaitResult = c.await(5, TimeUnit.SECONDS);
        System.out.println("awaitResult:");
        System.out.println(awaitResult);
        System.out.println("end");
    }
}

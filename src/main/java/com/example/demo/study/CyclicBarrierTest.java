package com.example.demo.study;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException,BrokenBarrierException {
        CyclicBarrier c = new CyclicBarrier(2, () -> {
            System.out.println("屏蔽前立即执行");
        });

        new Thread(() -> {
            System.out.println("2");
            try {
                c.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println("3");
            try {
                c.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("4");
    }
}

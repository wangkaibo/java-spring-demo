package com.example.demo.study;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author BridgeLi
 * @date 2021/2/6 16:14
 */
public class Thread_Communication_Condition {
  
    public static void main(String[] args) {
  
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        final List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        long start = System.currentTimeMillis();
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
  
        new Thread(() -> {
            lock.lock();
            try {
                integers.forEach(item -> {
                    System.out.print(item);
                    condition2.signal();
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
  
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }, "t1").start();
  
        new Thread(() -> {
            lock.lock();
            try {
                strings.forEach(item -> {
                    System.out.print(item);
                    condition1.signal();
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                condition1.signal();
            } finally {
                lock.unlock();
            }
        }, "t2").start();

        System.out.println(System.currentTimeMillis() - start);
    }
  
}
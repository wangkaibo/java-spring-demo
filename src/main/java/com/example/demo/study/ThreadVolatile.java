package com.example.demo.study;

import java.util.Arrays;
import java.util.List;

public class ThreadVolatile {

    private static volatile Integer state = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        final List<String> strings = Arrays.asList("A", "B", "C", "D", "E", "F", "G");
        Thread t1 = new Thread(() -> {
            int i = 0;
            while (i < integers.size()) {
                if (state == 0) {
                    System.out.print(integers.get(i));
                    i++;
                    state = 1;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i < strings.size()) {
                if (state == 1) {
                    System.out.print(strings.get(i));
                    i++;
                    state = 0;
                }
            }
        });
        t1.start();
        t2.start();
        System.out.println(System.currentTimeMillis() - start);
    }
}

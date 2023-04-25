package com.example.demo.temp;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Temp {

    private static String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static int SCALE = 62;

    public static String convert62(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(CHARS.charAt((int) (number % SCALE)));
            number = number / SCALE;
        }
        return sb.reverse().toString();
    }

    public static String shortId() {
        int randNum = RandomUtils.nextInt(1000, 9999);
        return shortId(Integer.toString(randNum, 36));
    }

    public static String shortId(String prefix) {
        long timeMs = System.currentTimeMillis();
        int ms = (int)(timeMs % 1000);
        int time = (int)(timeMs / 1000);
        return prefix + Integer.toString(time, 36) + Integer.toString(ms, 36);
    }
    public static void main(String[] args) throws Exception {
        LocalDateTime round1Time = LocalDateTime.of(2023, 1, 27, 0, 0, 0);
        LocalDateTime t = round1Time.plusDays(158);
        LocalDateTime t2 = t.plusDays(15);
        System.out.println(round1Time);
        System.out.println(t);
        System.out.println(t2);
    }
}

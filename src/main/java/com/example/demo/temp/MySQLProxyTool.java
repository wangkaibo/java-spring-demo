package com.example.demo.temp;

public class MySQLProxyTool {


    public static void main(String[] args) {
        int dbNum = 1;
        int tableNum = 128;
        int momoId = 269114576;
        System.out.println(momoId % dbNum);
        System.out.println((momoId / (dbNum * tableNum)) % tableNum);
    }
}

package com.example.demo.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestObj implements IProxyClass {

    public void doSomething(String str) {
      log.info("TestObj test run " + str);
    }

    public static void main(String[] args) {
        IProxyClass testObj = (IProxyClass) DynamicProxyHandler.newProxyObj(new TestObj());
        testObj.doSomething("test");
    }
}

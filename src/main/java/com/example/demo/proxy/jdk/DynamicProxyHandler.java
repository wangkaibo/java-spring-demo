package com.example.demo.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class DynamicProxyHandler implements InvocationHandler {

    private DynamicProxyHandler(Object object) {
        this.object = object;
    }

    private final Object object;

    public static Object newProxyObj(Object object) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), object.getClass().getInterfaces(), new DynamicProxyHandler(object));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("LogProxy info proxy {}", proxy);
        log.info("LogProxy before method {} args {} run", method, args);
        Object rtn = method.invoke(this.object, args);
        log.info("LogProxy after rtn {}", rtn);
        return rtn;
    }
}

package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> resp = new HashMap<>();

        return resp;
    }
}

package com.example.demo.controller;

import com.example.demo.dao.ActiveDao;
import com.example.demo.entity.Active;
import com.example.demo.pulsar.ProducerDemo;
import com.example.demo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class TestController {

    @Resource
    private ActiveDao activeDao;

    @Resource
    private FileService fileService;
    @Value("application.properties.test")
    private String value;
    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("application.properties.test", value);
        resp.put("test", System.getProperty("test"));
        log.info("test log");
        return resp;
    }

    @RequestMapping("/activeList")
    public Object activeList() {
        Map<String, Object> resp = new HashMap<>();
        List<Active> activeList = activeDao.getActiveList();
        resp.put("data", activeList);

        return resp;
    }

    @RequestMapping("/fileExport")
    public Object fileExport() {
        fileService.dataExport();

        return true;
    }

    @RequestMapping("/sendmsg")
    public Object sendMsg() {
        return ProducerDemo.sendMessage(UUID.randomUUID().toString());
    }
}

package com.example.demo;

import com.example.demo.dao.ActiveDao;
import com.example.demo.entity.Active;
import com.example.demo.service.FileService;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Resource
    private ActiveDao activeDao;

    @Resource
    private FileService fileService;

    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> resp = new HashMap<>();

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
}

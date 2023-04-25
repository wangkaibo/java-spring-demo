package com.example.demo.service;

import com.example.demo.dao.ActiveDao;
import com.example.demo.entity.Active;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class FileService {

    @Resource
    private ActiveDao activeDao;

    public void dataExport() {
        (new Thread(() -> {
            try {
                String path = "export.csv";
                File file = ResourceUtils.getFile(path);
                if (file.exists()) {
                    file.delete();
                } else {
                    file.createNewFile();
                }
                List<Active> activeList = activeDao.getAllActiveList();
                log.info("activeList count:" + activeList.size());
                try (Writer writer = new FileWriter(path, true)) {
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    for (Active active: activeList) {
                        bufferedWriter.write(active.toString());
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
                System.out.println(file);
            } catch (Exception e) {
                log.error("Exception", e);
            }
        })).start();
    }
}

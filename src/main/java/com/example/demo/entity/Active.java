package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Active {
    @TableId
    private Long id;
    private String uid;
    private LocalDateTime activeTime;
    private String activeDate;

    public String toString() {
        return this.id + "," + this.uid + "," + this.activeTime + "," + this.activeDate;
    }
}

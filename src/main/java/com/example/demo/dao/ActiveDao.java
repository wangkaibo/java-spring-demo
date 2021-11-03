package com.example.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.entity.Active;
import com.example.demo.mapper.ActiveMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ActiveDao {

    @Resource
    private ActiveMapper activeMapper;

    public List<Active> getActiveList() {
        LambdaQueryWrapper<Active> queryWrapper = Wrappers.lambdaQuery(Active.class)
                .orderByDesc(Active::getId)
                .last("limit 100");
        return activeMapper.selectList(queryWrapper);
    }

    public List<Active> getAllActiveList() {
        LambdaQueryWrapper<Active> queryWrapper = Wrappers.lambdaQuery(Active.class)
                .orderByDesc(Active::getId);
        return activeMapper.selectList(queryWrapper);
    }
}

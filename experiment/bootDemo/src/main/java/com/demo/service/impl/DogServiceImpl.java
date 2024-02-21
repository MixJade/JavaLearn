package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.DogMapper;
import com.demo.model.entity.Dog;
import com.demo.service.IDogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 狗 服务实现类
 *
 * @author MixJade
 * @since 2024-02-21
 */
@Service
public class DogServiceImpl extends ServiceImpl<DogMapper, Dog> implements IDogService {

    @Override
    public List<Dog> queryAllDog() {
        return baseMapper.queryAllDog();
    }
}

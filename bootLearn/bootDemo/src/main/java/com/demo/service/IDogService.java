package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.Dog;

import java.util.List;

/**
 * 狗 服务类
 *
 * @author MixJade
 * @since 2024-02-21
 */
public interface IDogService extends IService<Dog> {

    List<Dog> queryAllDog();
}

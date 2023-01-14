package com.forge.service;

import com.forge.dto.AdoptDto;
import com.forge.dto.Page;
import com.forge.entity.Adopt;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 领养宠物订单 服务类
 * </p>
 *
 * @author MixJade
 * @since 2023-01-12
 */
public interface IAdoptService extends IService<Adopt> {
    /**
     * 单个逻辑删除
     *
     * @param adoptId 寄养单id
     * @return 是否删除成功
     */
    boolean deleteById(long adoptId);

    /**
     * 批量逻辑删除
     *
     * @param idGroup 一组id
     * @return 是否批量删除成功
     */
    boolean deleteByIds(long[] idGroup);

    /**
     * 分页查询
     *
     * @param adoptCode 订单编码
     * @param numPage    当前页码
     * @param pageSize   当前一页的最大条数
     * @return 数据的总长度，与分页查询结果
     */
    Page<List<AdoptDto>> selectByPage(String adoptCode, int numPage, int pageSize);
}

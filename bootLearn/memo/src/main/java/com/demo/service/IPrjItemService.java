package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.PrjPageDto;
import com.demo.model.entity.PrjItem;
import com.demo.model.vo.PrjItemVo;

/**
 * <p>
 * 项目分类表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
public interface IPrjItemService extends IService<PrjItem> {
    IPage<PrjItemVo> getByPage(int pageNum, int pageSize, PrjPageDto prjPageDto);
}

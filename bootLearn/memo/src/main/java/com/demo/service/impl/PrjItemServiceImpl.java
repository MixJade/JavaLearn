package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PrjItemMapper;
import com.demo.model.dto.PrjPageDto;
import com.demo.model.entity.PrjItem;
import com.demo.model.vo.PrjItemVo;
import com.demo.service.IPrjItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目分类表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@Service
public class PrjItemServiceImpl extends ServiceImpl<PrjItemMapper, PrjItem> implements IPrjItemService {
    @Override
    public IPage<PrjItemVo> getByPage(int pageNum, int pageSize, PrjPageDto prjPageDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), prjPageDto);
    }
}

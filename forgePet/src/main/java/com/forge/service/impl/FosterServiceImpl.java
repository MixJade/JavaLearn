package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.FosterDto;
import com.forge.dto.Page;
import com.forge.entity.Foster;
import com.forge.mapper.FosterMapper;
import com.forge.service.IFosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2023-01-12
 */
@Service
public class FosterServiceImpl extends ServiceImpl<FosterMapper, Foster> implements IFosterService {
    private FosterMapper fosterMapper;

    @Autowired
    public void setFosterMapper(FosterMapper fosterMapper) {
        this.fosterMapper = fosterMapper;
    }

    @Override
    public boolean deleteById(long fosterId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return fosterMapper.deleteId(delDate, fosterId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return fosterMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<FosterDto>> selectByPage(String fosterCode, int numPage, int pageSize) {
        int maxCount = fosterMapper.selectFosterCount(fosterCode);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<FosterDto> fosterList = fosterMapper.selectFosterPage(fosterCode, needBegin, pageSize);
        return Page.page(fosterList, maxCount);
    }

    @Override
    public boolean save(Foster entity) {
        String fosterCode = "1000" + (fosterMapper.getMaxId() + 1);//这里要自动生成订单号
        entity.setFosterCode(fosterCode);
        return super.save(entity);
    }
}

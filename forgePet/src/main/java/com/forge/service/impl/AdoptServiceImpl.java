package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.AdoptDto;
import com.forge.dto.Page;
import com.forge.entity.Adopt;
import com.forge.mapper.AdoptMapper;
import com.forge.mapper.PetMapper;
import com.forge.service.IAdoptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 领养宠物订单 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2023-01-12
 */
@Service
public class AdoptServiceImpl extends ServiceImpl<AdoptMapper, Adopt> implements IAdoptService {
    private AdoptMapper adoptMapper;
    private PetMapper petMapper;

    @Autowired
    public void setAdoptMapper(AdoptMapper adoptMapper) {
        this.adoptMapper = adoptMapper;
    }

    @Autowired
    public void setPetMapper(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Override
    public boolean deleteById(long adoptId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return adoptMapper.deleteId(delDate, adoptId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return adoptMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<AdoptDto>> selectByPage(String adoptCode, int numPage, int pageSize) {
        int maxCount = adoptMapper.selectAdoptCount(adoptCode);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<AdoptDto> adoptList = adoptMapper.selectAdoptPage(adoptCode, needBegin, pageSize);
        return Page.page(adoptList, maxCount);
    }

    @Override
    public boolean save(Adopt entity) {
        String adoptCode = "2000" + (adoptMapper.getMaxId() + 1);//这里要自动生成订单号
        petMapper.setPetClient(entity.getClientId(), entity.getPetId());
        entity.setAdoptCode(adoptCode);
        return super.save(entity);
    }

    @Override
    public boolean updateById(Adopt entity) {
        petMapper.setPetClient(entity.getClientId(), entity.getPetId());
        return super.updateById(entity);
    }
}

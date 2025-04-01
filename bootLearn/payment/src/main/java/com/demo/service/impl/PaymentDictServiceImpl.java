package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentDictMapper;
import com.demo.model.dto.PayDictPageDto;
import com.demo.model.entity.BigType;
import com.demo.model.entity.PaymentDict;
import com.demo.model.vo.PaymentDictVo;
import com.demo.model.vo.TypeSelectTwoVo;
import com.demo.model.vo.TypeSelectVo;
import com.demo.service.IPaymentDictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收支类型表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-20
 */
@Service
public class PaymentDictServiceImpl extends ServiceImpl<PaymentDictMapper, PaymentDict> implements IPaymentDictService {

    @Override
    public List<TypeSelectVo> getOption(Boolean isIncome) {
        List<TypeSelectTwoVo> typeSelectTwoVos = baseMapper.getSelectTwoVos(isIncome);
        // 查询大类
        List<BigType> bigTypes = baseMapper.getBigTypes();
        Map<Integer, String> bigTypeMap = new HashMap<>();
        for (BigType bigType : bigTypes) {
            bigTypeMap.put(bigType.typeKey(), bigType.typeName());
        }
        Map<Integer, List<TypeSelectTwoVo>> twoKeyMap = new HashMap<>();

        // 直接在循环中进行分组和大类-小类的组装
        List<TypeSelectVo> typeSelectVos = new ArrayList<>();
        for (TypeSelectTwoVo selectTwoVo : typeSelectTwoVos) {
            int oneKey = selectTwoVo.oneKey();
            // 如果不存在这个key，则添加到Map，然后无论前面key是否存在，为对应value加值
            twoKeyMap.computeIfAbsent(oneKey, k -> new ArrayList<>()).add(selectTwoVo);
            // 只在第一次遇到大类时创建 TypeSelectVo 然后放入列表的引用
            if (twoKeyMap.get(oneKey).size() == 1) {
                typeSelectVos.add(new TypeSelectVo(oneKey, bigTypeMap.get(oneKey), twoKeyMap.get(oneKey)));
            }
        }
        return typeSelectVos;
    }

    @Override
    public IPage<PaymentDictVo> getByPage(int pageNum, int pageSize, PayDictPageDto payDictPageDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), payDictPageDto);
    }

    @Override
    public List<BigType> getBigTypes() {
        return baseMapper.getBigTypes();
    }
}

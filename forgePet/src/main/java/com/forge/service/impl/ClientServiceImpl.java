package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Client;
import com.forge.mapper.ClientMapper;
import com.forge.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {
    private ClientMapper clientMapper;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public boolean deleteById(long clientId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return clientMapper.deleteId(delDate, clientId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return clientMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<Client>> selectByPage(String clientName, int numPage, int pageSize) {
        int maxCount = clientMapper.selectClientCount(clientName);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<Client> clientList = clientMapper.selectClientPage(clientName, needBegin, pageSize);
        return Page.page(clientList, maxCount);
    }

    @Override
    public List<NameDto> selectName() {
        return clientMapper.selectName();
    }
}

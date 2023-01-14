package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Client;
import com.forge.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    private IClientService clientService;

    @Autowired
    public void setClientService(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<NameDto> getAll() {
        return clientService.selectName();
    }

    @GetMapping("/page")
    public Page<List<Client>> getPage(int numPage, int pageSize, String clientName) {
        return clientService.selectByPage(clientName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Client client) {
        if (client.getClientPassword() == null || client.getClientPassword().equals("")) {
            client.setClientPassword("6b6864bf70c40ccbc2752cd9ef11e77b");
        }
        return Result.choice("添加", clientService.save(client));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", clientService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", clientService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Client client) {
        return Result.choice("修改", clientService.updateById(client));
    }

}

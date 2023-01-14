package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.NoticeDto;
import com.forge.dto.Page;
import com.forge.entity.Notice;
import com.forge.service.INoticeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公告表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2023-01-02
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private INoticeService noticeService;

    @Autowired
    public void setNoticeService(INoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public List<Notice> getAll() {
        return noticeService.list();
    }

    @GetMapping("/page")
    public Page<List<NoticeDto>> getPage(int numPage, int pageSize, String noticeName) {
        return noticeService.selectByPage(noticeName, numPage, pageSize);
    }

    @GetMapping("/four")
    public List<NoticeDto> getFour() {
        return noticeService.selectFour();
    }

    @PostMapping
    public Result save(@RequestBody NoticeDto noticeDto) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        noticeDto.setCreateName(username);
        return Result.choice("添加", noticeService.addNotice(noticeDto));
    }

    @DeleteMapping("/disable/{id}")
    public Result disableSet(@PathVariable Long id, boolean isDis) {
        if (isDis) {
            return Result.choice("启用", noticeService.disableNotice(id, true));
        } else {
            return Result.choice("禁用", noticeService.disableNotice(id, false));
        }
    }

    @DeleteMapping("/delImg")
    public Result delName() {
        return Result.choice("删除冗余资源", noticeService.delImg());
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", noticeService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", noticeService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody NoticeDto noticeDto) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        noticeDto.setUpdateName(username);
        return Result.choice("修改", noticeService.updateNotice(noticeDto));
    }

}

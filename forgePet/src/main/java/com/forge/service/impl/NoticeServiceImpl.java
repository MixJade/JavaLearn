package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.NoticeDto;
import com.forge.dto.Page;
import com.forge.entity.Notice;
import com.forge.mapper.NoticeMapper;
import com.forge.service.INoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2023-01-02
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    private static final Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);
    private NoticeMapper noticeMapper;

    @Autowired
    public void setNoticeMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Value("${pet-forge.images-path}")
    private String basePath;
    @Value("${pet-forge.notice-path}")
    private String noticePath;

    @Override
    public boolean delImg() {
        // 数据库中存储的照片名字
        List<String> clientImg = noticeMapper.selectImgClient();
        List<String> doctorImg = noticeMapper.selectImgDoctor();
        List<String> petImg = noticeMapper.selectImgPet();
        List<String> employeeImg = noticeMapper.selectImgEmployee();
        clientImg.addAll(doctorImg);
        clientImg.addAll(petImg);
        clientImg.addAll(employeeImg);
        // 去重
        Set<String> set = new HashSet<>(clientImg);
        // 图片文件夹下的文件名
        String[] list01 = new File(basePath).list();
        assert list01 != null;
        List<String> moreImg = new ArrayList<>(Arrays.asList(list01));
        // 图片文件夹去掉数据库中的，就是冗余的图片
        moreImg.removeAll(set);
        // 开删！！！！！！！
        boolean delete = false;
        for (String fileName : moreImg) {
            File delImg = new File(basePath + fileName);
            delete = delImg.delete();
            log.info("删除冗余图片");
        }
        // 数据库中公告的名字
        List<String> noticeFile = noticeMapper.selectNoticeFile();
        // 公告文件夹下的文件名
        String[] list02 = new File(noticePath).list();
        List<String> moreNotice = new ArrayList<>(Arrays.asList(Objects.requireNonNull(list02)));
        moreNotice.removeAll(noticeFile);
        // 开删！！！！！！！
        for (String fileName : moreNotice) {
            delete = (new File(noticePath + fileName)).delete();
            log.info("删除冗余公告");
        }
        return delete;
    }

    @Override
    public boolean deleteById(long noticeId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return noticeMapper.deleteId(delDate, noticeId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return noticeMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<NoticeDto>> selectByPage(String noticeName, int numPage, int pageSize) {
        int maxCount = noticeMapper.selectNoticeCount(noticeName);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<NoticeDto> noticeList = noticeMapper.selectNoticePage(noticeName, needBegin, pageSize);
        for (NoticeDto noticeDto : noticeList) {
            String text = getText(noticeDto.getNoticeFile());
            noticeDto.setTextNotice(text);
        }
        return Page.page(noticeList, maxCount);
    }

    @Override
    public List<NoticeDto> selectFour() {
        List<NoticeDto> noticeList = noticeMapper.selectFour();
        for (NoticeDto noticeDto : noticeList) {
            String text = getText(noticeDto.getNoticeFile());
            noticeDto.setTextNotice(text);
        }
        return noticeList;
    }

    @Override
    public boolean addNotice(NoticeDto noticeDto) {
        String text = noticeDto.getTextNotice();
        String path = UUID.randomUUID() + ".txt";
        writeText(text, path);
        String username = noticeDto.getCreateName();
        String noticeTitle = noticeDto.getNoticeTitle();
        return noticeMapper.addNotice(username, noticeTitle, path);
    }

    @Override
    public boolean updateNotice(NoticeDto noticeDto) {
        String text = noticeDto.getTextNotice();
        String path = noticeDto.getNoticeFile();
        writeText(text, path);
        String noticeTitle = noticeDto.getNoticeTitle();
        String username = noticeDto.getUpdateName();
        long noticeId = noticeDto.getNoticeId();
        return noticeMapper.updateNotice(username, noticeTitle, noticeId);
    }

    @Override
    public boolean disableNotice(long noticeId, boolean isDis) {
        return noticeMapper.disableNotice(noticeId, !isDis);
    }

    /**
     * 获取公告文本
     *
     * @param noticeFile 公告文件名
     * @return 公告文本
     */
    private String getText(String noticeFile) {
        try {
            return Files.readString(Path.of(noticePath + noticeFile));
        } catch (IOException e) {
            log.warn("公告文本获取失败" + e);
        }
        return null;
    }

    /**
     * 将公告内容写入文本
     *
     * @param text       公告内容
     * @param noticeFile 公告文件名
     */
    private void writeText(String text, String noticeFile) {
        FileWriter writer;
        try {
            writer = new FileWriter(noticePath + noticeFile);
            writer.write("");//清空原文件内容
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.warn("公告文本写入失败" + e);
        }
    }
}

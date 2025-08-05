package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentCacheMapper;
import com.demo.model.dto.PayCachePageDto;
import com.demo.model.entity.PaymentCache;
import com.demo.service.IPaymentCacheService;
import com.demo.utils.CsvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 收支缓存表(微信导入) 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-03-31
 */
@Service
public class PaymentCacheServiceImpl extends ServiceImpl<PaymentCacheMapper, PaymentCache> implements IPaymentCacheService {
    private static final Logger log = LoggerFactory.getLogger(PaymentCacheServiceImpl.class);

    @Override
    public IPage<PaymentCache> getByPage(int pageNum, int pageSize, PayCachePageDto payCachePageDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), payCachePageDto);
    }

    @Override
    public boolean delAll() {
        baseMapper.truncateCache();
        return true;
    }

    @Override
    @Transactional
    public boolean saveCsv(MultipartFile file) {
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");
        // 定义时间格式(用于入库)
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        List<String> lines = CsvUtil.readCsvFile(file);
        // 读取表头
        log.info("表头:{}", Arrays.toString(lines.get(0).split(",")));
        lines.remove(0); // 去除头
        List<PaymentCache> paymentCacheList = new ArrayList<>();
        for (String line : lines) {
            PaymentCache payCache = new PaymentCache();
            List<String> csvLine = CsvUtil.parseCsvLine(line);
            // 0-付费时间
            // 将字符串解析为 LocalDateTime 对象
            LocalDateTime dateTime = LocalDateTime.parse(csvLine.get(0), formatter);
            // 从 LocalDateTime 对象中提取 LocalDate 对象
            LocalDate localDate = dateTime.toLocalDate();
            payCache.setPayDate(localDate);
            // 提取小时分钟
            String hourTime = dateTime.format(formatter2);
            payCache.setPayTime(hourTime);
            // 1-交易类型
            payCache.setPayType(csvLine.get(1));
            // 2-交易对方
            payCache.setPayMan(csvLine.get(2));
            // 3-商品
            payCache.setWareName(csvLine.get(3));
            // 4-收/支
            payCache.setIsIncome("收入".equals(csvLine.get(4)));
            // 5-金额(去除符号)
            String pureNumberStr = csvLine.get(5).replaceAll("[^0-9.]", "");
            // 将处理后的字符串转换为 BigDecimal
            BigDecimal amount = new BigDecimal(pureNumberStr);
            payCache.setMoney(amount);
            // 6-支付方式
            payCache.setPayWay(csvLine.get(6));
            // 7-当前状态
            payCache.setPayState(csvLine.get(7));
            payCache.setIsDel(false);
            paymentCacheList.add(payCache);
        }
        return saveBatch(paymentCacheList);
    }
}

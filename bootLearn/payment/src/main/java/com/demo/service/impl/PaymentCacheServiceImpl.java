package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentCacheMapper;
import com.demo.model.entity.PaymentCache;
import com.demo.service.IPaymentCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public IPage<PaymentCache> getByPage(int pageNum, int pageSize) {
        return lambdaQuery()
                .orderByAsc(PaymentCache::getPayDate)
                .page(new Page<>(pageNum, pageSize));
    }

    @Override
    @Transactional
    public boolean saveCsv(MultipartFile file) {
        String csvSplitBy = ",";
        String[] headers;
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // 读取表头
            headers = br.readLine().split(csvSplitBy);
            log.info("表头:{}", Arrays.toString(headers));
            List<PaymentCache> paymentCacheList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                PaymentCache payCache = new PaymentCache();
                String[] data = line.split(csvSplitBy);
                // 0-付费时间
                // 将字符串解析为 LocalDateTime 对象
                LocalDateTime dateTime = LocalDateTime.parse(data[0], formatter);
                // 从 LocalDateTime 对象中提取 LocalDate 对象
                LocalDate localDate = dateTime.toLocalDate();
                payCache.setPayDate(localDate);
                // 1-交易类型
                payCache.setPayType(data[1]);
                // 2-交易对方
                payCache.setPayMan(data[2]);
                // 3-商品
                payCache.setWareName(data[3]);
                // 4-收/支
                payCache.setIsIncome("收入".equals(data[4]));
                // 5-金额(去除符号)
                String pureNumberStr = data[5].replaceAll("[^0-9.]", "");
                // 将处理后的字符串转换为 BigDecimal
                BigDecimal amount = new BigDecimal(pureNumberStr);
                payCache.setMoney(amount);
                // 6-支付方式
                payCache.setPayWay(data[6]);
                // 7-当前状态
                payCache.setPayState(data[7]);
                payCache.setIsDel(false);
                paymentCacheList.add(payCache);
            }
            if (paymentCacheList.size() == 0) {
                log.warn("无数据");
                return false;
            }
            // 批量保存
            return saveBatch(paymentCacheList);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

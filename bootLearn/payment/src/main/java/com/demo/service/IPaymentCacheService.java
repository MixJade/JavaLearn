package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.PaymentCache;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 收支缓存表(微信导入) 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-03-31
 */
public interface IPaymentCacheService extends IService<PaymentCache> {
    /**
     * 分页查询缓存数据
     */
    IPage<PaymentCache> getByPage(int pageNum, int pageSize);

    /**
     * 保存csv中的消费数据
     *
     * @param file 上传csv的二进制流
     * @return 保存成功
     */
    boolean saveCsv(MultipartFile file);
}

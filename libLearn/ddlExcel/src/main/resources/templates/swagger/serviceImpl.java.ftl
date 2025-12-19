<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="serviceName" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${pack}.mapper.${tab.lJNm()}Mapper;
import ${pack}.model.entity.${tab.lJNm()};
import ${pack}.service.${serviceName};
import org.springframework.stereotype.Service;

/**
 * ${tab.tb().comments()} 服务类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${tab.lJNm()}ServiceImpl extends ServiceImpl${r"<"}${tab.lJNm()}Mapper, ${tab.lJNm()}>implements ${serviceName} {
    /**
     * 模糊分页查询
     *
     * @author: ${author}
     * @date: ${date}
     */
    @Override
    public IPage<${tab.lJNm()}> queryByLike(int pageNum, int pageSize, ${tab.lJNm()} ${tab.sJNm()}) {
        return baseMapper.queryByLike(new Page<>(pageNum, pageSize), ${tab.sJNm()});
    }

    /**
     * 按照编号查询
     *
     * @author: ${author}
     * @date: ${date}
     */
    @Override
    public IPage<${tab.lJNm()}> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}

<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="serviceName" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import ${pack}.model.entity.${tab.lJNm()};

/**
 * ${tab.tb().comments()} 服务类
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${serviceName} extends IService<${tab.lJNm()}> {
    /**
     * 模糊分页查询
     *
     * @author: ${author}
     * @date: ${date}
     */
    IPage<${tab.lJNm()}> queryByLike(int pageNum, int pageSize, ${tab.lJNm()} ${tab.sJNm()});

    /**
     * 按照编号查询
     *
     * @author: ${author}
     * @date: ${date}
     */
    ${tab.lJNm()} detailById(String id);
}

<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="serviceName" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ${pack}.model.entity.${tab.lJNm()};
import org.apache.ibatis.annotations.Mapper;

/**
 * ${tab.tb().comments()} Mapper接口
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${tab.lJNm()}Mapper extends BaseMapper<${tab.lJNm()}> {
    // 模糊分页查询
    IPage<${tab.lJNm()}> queryByLike(IPage<${tab.lJNm()}> page, ${tab.lJNm()} ${tab.sJNm()});
}

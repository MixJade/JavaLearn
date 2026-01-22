<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
<#list tab.pkgList() as pkg>
import ${pkg};
</#list>

/**
 * ${tab.tb().comments()}
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@TableName("${tab.tb().tableName()}")
@Schema(description = "${tab.tb().comments()}")
public class ${tab.lJNm()} implements Serializable {
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list tab.codeCols() as field>

    <#if field.comment()!?length gt 0>
    @Schema(description = "${field.comment()}")
    </#if>
    <#if field.keyFlag()>
    <#-- 主键 -->
    @TableId("${field.colNm()}")
    <#else>
    <#-- 普通字段 -->
    @TableField("${field.colNm()}")
    </#if>
    private ${field.type()} ${field.jNm()};
</#list>
<#------------  END 字段循环遍历  ---------->
}

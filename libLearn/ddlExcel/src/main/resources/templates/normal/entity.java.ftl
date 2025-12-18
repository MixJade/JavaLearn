<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.model.entity;

<#list tab.pkgList() as pkg>
import ${pkg};
</#list>
import java.io.Serializable;
import java.io.Serial;

/**
 * ${tab.tb().comments()}
 *
 * @author ${author}
 * @since ${date}
 */
@SuppressWarnings("unused")
public class ${tab.lJNm()} implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list tab.codeCols() as field>

    <#if field.comment()!?length gt 0>
    /**
     * ${field.comment()}
     */
    </#if>
    <#-- 主键 -->
    <#if field.keyFlag()>
    @TableId(value = "${field.colNm()}", type = IdType.AUTO)
    </#if>
    private ${field.type()} ${field.jNm()};
</#list>
<#------------  END 字段循环遍历  ---------->

<#-- ----------  BEGIN 字段方法循环遍历  ---------->
<#list tab.codeCols() as field>
    <#if field.type() == "boolean">
        <#assign getprefix="is"/>
    <#else>
        <#assign getprefix="get"/>
    </#if>
    <#-- 定义变量：字段的首字母大写 -->
    <#assign capitalName=field.jNm()?cap_first/>

    public ${field.type()} ${getprefix}${capitalName}() {
        return ${field.jNm()};
    }

    public void set${capitalName}(${field.type()} ${field.jNm()}) {
        this.${field.jNm()} = ${field.jNm()};
    }
</#list>
}

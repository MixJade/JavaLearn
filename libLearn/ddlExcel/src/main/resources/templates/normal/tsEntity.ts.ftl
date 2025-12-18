<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- 定义number类型的枚举 -->
<#assign enumSet = ["Integer", "Long", "Boolean", "BigDecimal"]>
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
/**
 * ${tab.tb().comments()}
 */
export interface ${tab.lJNm()} {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list tab.codeCols() as field>
    ${field.jNm()}: <#if field.type()=="Boolean">boolean<#elseif enumSet?seq_contains(field.type())>number<#else>string</#if>;<#if field.comment()!?length gt 0> // ${field.comment()}</#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}

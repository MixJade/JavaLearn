<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
<#-- 生成TS类定义 -->
/**
 * ${tab.tb().comments()}
 */
export class ${tab.lJNm()} {
<#-- 遍历字段：生成带注释的属性定义 -->
<#list tab.codeCols() as field>
    ${field.jNm()}: string;<#if field.comment()!?length gt 0> // ${field.comment()}</#if>
</#list>
<#-- 构造函数-->
    constructor(
        options: {
<#list tab.codeCols() as field>
            ${field.jNm()}?: string;
</#list>
        } = {}
    ) {
<#list tab.codeCols() as field>
        this.${field.jNm()} = options.${field.jNm()} || "";
</#list>
    }
}
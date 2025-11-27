<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="className" type="java.lang.String" -->
<#-- @ftlvariable name="columns" type="java.util.List<entiy.ColumnDo>" -->
<#-- 生成TS类定义 -->
export class ${className} {
<#-- 遍历字段：生成带注释的属性定义 -->
<#list columns as column>
    ${column.columnName}: string; // ${column.comment}
</#list>
<#-- 构造函数-->
    constructor(
        options: {
<#list columns as column>
            ${column.columnName}?: string;
</#list>
        } = {}
    ) {
<#list columns as column>
        this.${column.columnName} = options.${column.columnName} || "";
</#list>
    }
}
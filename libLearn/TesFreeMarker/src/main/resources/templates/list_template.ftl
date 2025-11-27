<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="userList" type="java.util.List<entiy.User>" -->
使用变量的index属性获取循环次数(从0开始)
以及使用include引入其它模板
<#list userList as user>
<#-- 注意这里的user变量名与引入模板的相同,所以下面的assign可以不加-->
    <#assign user = user/>
    <#include "common_template.ftl" />
</#list>

使用变量的counter属性获取循环次数(从1开始)
<#list userList as user>
    ${user?counter}. 姓名:${user.name}，年龄:${user.age}
</#list>
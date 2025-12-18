<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="user" type="entiy.User" -->
姓名【${user.name()}】，今年${user.age()}岁，<#if (user.age() > 35)>无业游民<#else>灵活就业</#if>

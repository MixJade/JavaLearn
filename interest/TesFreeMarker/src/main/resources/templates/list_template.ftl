使用变量的index属性获取循环次数(从0开始)
<#list userList as user>
    ${user?index}. 姓名:${user.name}，年龄:${user.age}
</#list>
使用变量的counter属性获取循环次数(从1开始)
<#list userList as user>
    ${user?counter}. 姓名:${user.name}，年龄:${user.age}
</#list>
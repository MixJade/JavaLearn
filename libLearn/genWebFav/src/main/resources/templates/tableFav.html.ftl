<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="lv1DirList" type="java.util.List<entiy.Lv1Dir>" -->
<#-- @ftlvariable name="htmlTit" type="java.lang.String" -->
<#-- @ftlvariable name="mainIco" type="enums.IcoEnum" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${htmlTit}</title>
<#--    <link href="tableFav.min.css" rel="stylesheet">-->
    <style><#include "tableFav.min.css.ftl" /></style>
    <link href='icoLogo/${mainIco.getIcoName()}' rel="icon" type="image/png"/>
</head>
<body>
<nav id="headNav">
    <#list lv1DirList as lv1Dir>
    <button type="button" onclick="cikNav(${lv1Dir?counter-1})" <#if lv1Dir?is_first>class="selectNav"</#if>>${lv1Dir.title()}</button>
    </#list>
</nav>
<div id="navHr"></div>
<#list lv1DirList as lv1Dir>
<div class="tableDiv" style="display: <#if lv1Dir?is_first>block<#else>none</#if>">
<#list lv1Dir.lv2Dirs() as lv2Dir>
<#-- 遍历datum.group数组，对应JS中的datum.group.forEach -->
    <table>
        <caption>${lv2Dir.title()}</caption>
        <tbody>
        <tr>
            <#-- 遍历i.ul数组，同时获取索引（从0开始） -->
            <#list lv2Dir.favUrls() as j>
            <td>
                <div title="${j.remark()}">
                    <img src="icoLogo/${j.icoEnum().getIcoName()}" alt="ico">
                    <a href="${j.href()}">${j.name()}</a>
                    <#if j.remark()!=''>...</#if><#if j.pwd()!=''>
                    <button type="button" onclick="geCopy('${j.pwd()}')">复密</button><span class="btnDot"></span></#if>
                </div>
            </td>
            <#-- 每4个元素换行 -->
            <#if (j_index + 1) % 4 == 0 && j_has_next>
        </tr><tr>
            </#if>
            </#list>
            <#-- 补充空td补全最后一行，对应JS中的ulNum % 4 !== 0的逻辑 -->
            <#assign remain = lv2Dir.favUrls()?size % 4>
            <#if remain != 0>
                <#assign emptyNum = 4 - remain>
                <#list 1..emptyNum as k>
            <td class="emptyTd"><div></div></td>
                </#list>
            </#if>
        </tr>
        </tbody>
    </table>
</#list>
    <footer>${lv1Dir.updateTime()}</footer>
</div>
</#list>
</body>
</html>
<#--<script src="../../showTs/tableFav2.js"></script>-->
<script><#include "tableFav.js.ftl" /></script>

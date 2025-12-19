<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="serviceName" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${pack}.mapper.${tab.lJNm()}Mapper">
    <!-- 分页查询-->
    <#--noinspection HtmlFormInputWithoutLabel-->
    <select id="getByPage" resultType="${pack}.model.entity.${tab.lJNm()}">
        SELECT <#list tab.codeCols() as field>at.${field.colNm()}<#if !field?is_last>,</#if></#list>
        FROM ${tab.tb().tableName()} at
    </select>
</mapper>

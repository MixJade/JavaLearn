<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- 定义变量：Service名称 -->
<#assign serviceName = "I${tab.lJNm()}Service">
<#assign serviceNameLower = "${tab.sJNm()}Service">
<#-- 定义变量：Controller名称 -->
<#assign controllerName = tab.lJNm()+"Controller">
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.controller;

import ${pack}.${tab.lJNm()};
import ${pack}.service.${serviceName};
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${tab.tb().comments()} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("/api/${tab.sJNm()}")
@Tag(name = "${tab.tb().comments()}操作接口")
public class ${tab.lJNm()}Controller {
    private final ${serviceName} ${serviceNameLower};

    @Autowired
    public ${controllerName}(${serviceName} ${serviceNameLower}) {
        this.${serviceNameLower} = ${serviceNameLower};
    }

    /**
     * 按照编号查询
     *
     * @author: ${author}
     * @date: ${date}
     */
    @Operation(summary = "按照编号查询")
    @Parameter(name = "id", description = "ID", required = true, in = ParameterIn.QUERY)
    @RequestMapping(value = "/detail-by-id", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ${tab.lJNm()} detailById(@RequestParam String id){
        return ${serviceNameLower}.detailById(id);
    }
}

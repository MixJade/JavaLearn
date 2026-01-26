<#-- 声明变量，消除IDEA警告 -->
<#-- @ftlvariable name="date" type="java.lang.String" -->
<#-- @ftlvariable name="author" type="java.lang.String" -->
<#-- @ftlvariable name="pack" type="java.lang.String" -->
<#-- @ftlvariable name="tab" type="work.model.dto.CodeTab" -->
<#-- 定义变量：Service名称 -->
<#assign serviceName = "${tab.lJNm()}Service">
<#assign serviceNameLower = "${tab.sJNm()}Service">
<#-- 定义变量：Controller名称 -->
<#assign controllerName = tab.lJNm()+"Controller">
<#-- IDEA 识别：从此处开始，禁用格式化（包括缩进） -->
<#-- @formatter:off -->
package ${pack}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${pack}.entity.${tab.lJNm()};
import ${pack}.service.${serviceName};
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * ${tab.tb().comments()} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping(value = "/api/v1" + ${tab.lJNm()}Controller.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "${tab.tb().comments()}操作接口")
public class ${tab.lJNm()}Controller {
    static final String API_PREFIX = "/${tab.sJNm()}";

    @Autowired
    ${serviceName} ${serviceNameLower};

    /**
     * 分页条件查询信息
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @param ${tab.sJNm()} ${tab.tb().comments()}查询模型
     * @author ${author}
     * @date ${date}
     */
    @Operation(summary = "分页条件查询信息")
    @Parameters({@Parameter(name = "pageNum", description = "页码", required = true, in = ParameterIn.QUERY), @Parameter(name = "pageSize", description = "每页显示数量", required = true, in = ParameterIn.QUERY)})
    @RequestMapping(value = "/query-by-like", method = RequestMethod.POST)
    public IPage<${tab.lJNm()}> queryByLike(@RequestParam @Min(1) Integer pageNum, @RequestParam @Min(1) Integer pageSize, @RequestBody ${tab.lJNm()} ${tab.sJNm()}) {
        return ${serviceNameLower}.queryByLike(pageNum, pageSize, ${tab.sJNm()});
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

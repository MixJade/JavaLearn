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

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${pack}.common.Result;
import ${pack}.${tab.lJNm()};
import ${pack}.service.${serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* ${tab.tb().comments()} 前端控制器
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("/api/${tab.sJNm()}")
public class ${tab.lJNm()}Controller {
    private final ${serviceName} ${serviceNameLower};

    @Autowired
    public ${controllerName}(${serviceName} ${serviceNameLower}) {
        this.${serviceNameLower} = ${serviceNameLower};
    }

    @PostMapping
    public Result add(@RequestBody ${tab.lJNm()} ${tab.sJNm()}) {
        boolean addRes = ${serviceNameLower}.save(${tab.sJNm()});
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = ${serviceNameLower}.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ${tab.lJNm()} ${tab.sJNm()}) {
        boolean updateRes = ${serviceNameLower}.updateById(${tab.sJNm()});
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<${tab.lJNm()}> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return ${serviceNameLower}.getByPage(pageNum, pageSize);
    }
}

package ${package.Controller};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Parent}.common.Result;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

<#-- 定义变量：去除 I 并首字母小写 -->
<#assign serviceNameLower = table.serviceName?substring(1)?uncap_first>
<#-- 定义变量：实体类的首字母小写 -->
<#assign entityLower = entity?uncap_first>
/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@RestController
@RequestMapping("/api/${table.entityPath}")
public class ${table.controllerName} {
    private final ${table.serviceName} ${serviceNameLower};

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${serviceNameLower}) {
        this.${serviceNameLower} = ${serviceNameLower};
    }

    @PostMapping
    public Result add(@RequestBody ${entity} ${entityLower}) {
        boolean addRes = ${serviceNameLower}.save(${entityLower});
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = ${serviceNameLower}.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody ${entity} ${entityLower}) {
        boolean updateRes = ${serviceNameLower}.updateById(${entityLower});
        return Result.choice("修改", updateRes);
    }

    @GetMapping("/page")
    public IPage<${entity}> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return ${serviceNameLower}.getByPage(pageNum, pageSize);
    }
}

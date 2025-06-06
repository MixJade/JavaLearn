package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
<#if table.serviceInterface>
import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}><#if table.serviceInterface> implements ${table.serviceName}</#if> {
    @Override
    public IPage<${entity}> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}

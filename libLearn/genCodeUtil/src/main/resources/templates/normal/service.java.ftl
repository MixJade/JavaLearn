package ${package.Service};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${superServiceClassPackage};
import ${package.Entity}.${entity};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    IPage<${entity}> getByPage(int pageNum, int pageSize);
}
